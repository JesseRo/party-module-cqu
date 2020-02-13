package party.portlet.org.command;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import hg.util.ConstantsKey;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.tool.Extension.Param;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;
import hg.util.MD5;
import hg.util.TransactionUtil;
import party.constants.PartyPortletKeys;

@Component(immediate = true, property = {
		"javax.portlet.name=" + PartyPortletKeys.PersonAddPortlet,
		"javax.portlet.name=" + PartyPortletKeys.PersonalInfoPortlet,
		"mvc.command.name=/org/add/user"
}, service = MVCActionCommand.class)
public class AddPersonActionCommand implements MVCActionCommand {
	Logger log = Logger.getLogger(AddPersonActionCommand.class);

	@Reference
	private MemberDao memberDao;
	@Reference
	private UserDao UserDao;
	@Reference
	TransactionUtil transactionUtil;
	@Reference
	private OrgDao orgDao;

	@Override
	@Transactional
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		Object userId = SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "userName");
		String role = (String)SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "role");

		Member m = new Member();
		User u = new User();
		String userName = ParamUtil.getString(actionRequest, "userName");
		String sex = ParamUtil.getString(actionRequest, "sex");
		String ethnicity = ParamUtil.getString(actionRequest, "ethnicity");
		String birth_place = ParamUtil.getString(actionRequest, "birth_place");
		String province = ParamUtil.getString(actionRequest, "province");
		String city = ParamUtil.getString(actionRequest, "city");
		String birthday = ParamUtil.getString(actionRequest, "birthday");
		String join_party_time = ParamUtil.getString(actionRequest, "join_party_time");
		String turn_Time = ParamUtil.getString(actionRequest, "turn_Time");
		String telephone = ParamUtil.getString(actionRequest, "telephone");
		String ID_card = ParamUtil.getString(actionRequest, "ID_card");
		String educational_level = ParamUtil.getString(actionRequest, "educational_level");
		String party_type = ParamUtil.getString(actionRequest, "party_type");
		String home_addrss = ParamUtil.getString(actionRequest, "home_addrss");
		String orgId = ParamUtil.getString(actionRequest, "orgId");
		String seconedName = ParamUtil.getString(actionRequest, "seconedName");
		String email = ParamUtil.getString(actionRequest, "email");
		String job = ParamUtil.getString(actionRequest, "job");
		String positior = ParamUtil.getString(actionRequest, "positior");
		String marriage = ParamUtil.getString(actionRequest, "marriage");
		String id = ParamUtil.getString(actionRequest, "id");
		String prevID_card = ParamUtil.getString(actionRequest, "prevID_card");
		String title = ParamUtil.getString(actionRequest, "major_title");
		String unit = ParamUtil.getString(actionRequest, "unit");
		String isLeader = ParamUtil.getString(actionRequest, "isLeader");
		String classnew1 = ParamUtil.getString(actionRequest, "new_class1");
		String classnew2 = ParamUtil.getString(actionRequest, "new_class2");
		String classnew3 = ParamUtil.getString(actionRequest, "new_class3");
		String classnew = classnew1 + "园区" + classnew2 + "栋" + classnew3 + "室";
		ID_card = ID_card.toUpperCase();

		String addPersonFormId = ParamUtil.getString(actionRequest, "addPersonFormId");

		try {
			// if (seconedName.contains("Ö")) {
			List<Map<String, Object>> list = orgDao.findSecondOrgName(orgId);
			seconedName = list.get(0).get("org_name").toString();
			// }
			String sql = "INSERT INTO hg_party_member (\"member_name\", \"member_sex\", \"member_ethnicity\", \"member_age\""
					+ ", \"member_birthday\", \"member_identity\", \"member_degree\", \"member_job\", \"member_join_date\""
					+ ", \"member_fomal_date\", \"member_org\", \"member_type\", \"member_address\", \"member_phone_number\""
					+ ", \"member_landline_number\", \"member_is_outofcontact\", \"member_outofcontact_date\", \"member_is_flow\""
					+ ", \"member_flow_to\", \"member_membership_state\", \"member_mailbox\", \"historic\", \"member_party_position\", \"member_marriage\", \"member_province\", \"member_city\""
					+ ", \"member_major_title\", \"member_new_class\", \"member_front_line\", \"member_party_committee\", \"member_birth_place\", \"member_is_leader\", \"member_unit\")"
					+ "VALUES ('" + userName + "', '" + sex + "', '" + ethnicity + "', NULL, '" + birthday + "', '"
					+ ID_card + "', '" + educational_level + "', '" + job + "', '" + join_party_time + "', '"
					+ turn_Time + "', '" + orgId + "', '" + party_type + "', '" + home_addrss + "', '" + telephone
					+ "', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '" + false + "', '" + positior + "','" + marriage + "','"  + province + "','"  + city + "','" + title
					+ "','" + classnew + "', NULL, '" + seconedName + "', '" + birth_place + "', '" + isLeader + "', '" + unit + "');";
			String Updatesql = "UPDATE hg_party_member set \"member_name\"='" + userName + "', \"member_sex\"='" + sex
					+ "', \"member_ethnicity\"='" + ethnicity + "', \"member_birthday\"='" + birthday
					+ "', \"member_identity\"='" + ID_card + "', \"member_degree\"='" + educational_level
					+ "', \"member_job\"='" + job + "', \"member_join_date\"='" + join_party_time
					+ "', \"member_fomal_date\"='" + turn_Time + "', \"member_org\"='" + orgId + "', \"member_type\"='"
					+ party_type + "', \"member_address\"='" + home_addrss + "', \"member_phone_number\"='" + telephone
					+ "',\"historic\"=" + false + ", \"member_birth_place\"='" + birth_place
					+ "',\"member_party_position\"= '" + positior
					+ "',\"member_province\"= '" + province
					+ "',\"member_city\"= '" + city
					+ "',\"member_marriage\"= '" + marriage
					+ "', \"member_major_title\"='" + title
					+ "', \"member_is_leader\"='" + isLeader
					+ "', \"member_unit\"='" + unit
					+ "', \"member_new_class\"='" + classnew + "'  WHERE id='" + id + "'";
			if (!StringUtils.isEmpty(id)) {
				u = UserDao.findUserByEthnicity(prevID_card);
			}
			u.setUser_id(ID_card);
			u.setUser_name(userName);

			u.setUser_sex(sex);
			u.setUser_telephone(telephone);
			u.setUser_department_id(orgId);
			u.setState("1");
			u.setUser_mailbox(email);
			u.setUserrole("普通党员");
			// log.info("编辑人员:["+new Date()+"] [by "+userId+"] ID_card
			// :["+ID_card+"]");
			synchronized (PortalUtil.getHttpServletRequest(actionRequest).getSession()) {
				String originalFormId = (String) SessionManager.getAttribute(actionRequest.getRequestedSessionId(),
						"addperson-formId");
				if (addPersonFormId.equals(originalFormId)) {

					transactionUtil.startTransaction();
					if (!StringUtils.isEmpty(id)) {
//						u.setUser_password(MD5.getMD5(ID_card.substring(12)));
						memberDao.insertOrUpate(Updatesql);
						UserDao.update(u);
						if (!prevID_card.equals(ID_card)) {
							String sqls = getSql(prevID_card, ID_card);
							// memberDao.insertOrUpate(sqls);
							memberDao.getJdbcTemplate().execute(sqls);
						}
						log.info("编辑人员:[" + new Date() + "] [by " + userId + "]  ID_card :[" + ID_card + "]");
					} else {
						u.setUser_password(MD5.getMD5(ID_card.substring(12)));
						memberDao.insertOrUpate(sql);
						UserDao.save(u);
						log.info("添加人员:[" + new Date() + "] [by " + userId + "]  ID_card :[" + ID_card + "]");
					}
					transactionUtil.commit();
					SessionManager.setAttribute(actionRequest.getRequestedSessionId(), "addperson-formId", "NULL");
					if (role.equalsIgnoreCase(ConstantsKey.COMMON_PARTY)){
						actionResponse.sendRedirect("/personal_info");
					}else {
						actionResponse.sendRedirect("/partyusermanager?org=" + orgId);
					}
				}
			}

		} catch (Exception e) {
			transactionUtil.rollback();
			log.info("异常信息:[" + new Date() + "] [by " + userId + "]  ID_card :[" + ID_card + "]");
			actionRequest.setAttribute("error", "操作异常！");
			return false;
		}

		return false;
	}

	public static String getSql(String oldIds, String nowIds) {
		// String password = MD5.getMD5(nowIds.substring(12));
		String nowId = nowIds.toUpperCase();
		String oldId = oldIds.toUpperCase();
		String sql = // "UPDATE hg_users_info set user_id =
						// '"+nowId+"',user_password='"+password+"' where
						// user_id = '"+oldId+"';"+
				"UPDATE hg_party_org_admin set admin_id = '" + nowId + "' where admin_id = '" + oldId + "';"
						+ "UPDATE hg_party_group_member_info set participant_id = '" + nowId
						+ "' where participant_id = '" + oldId + "';"
						+ "UPDATE hg_party_meeting_member_info set participant_id = '" + nowId
						+ "' where participant_id = '" + oldId + "';"
						+ "UPDATE hg_party_comments_info set participant_id = '" + nowId + "' where participant_id = '"
						+ oldId + "';" + "UPDATE hg_party_learning_experience set participant_id = '" + nowId
						+ "' where participant_id = '" + oldId + "';" +
						// "UPDATE hg_party_member set member_identity =
						// '"+nowId+"' where member_identity = '"+oldId+"';"+
						"UPDATE hg_party_org_inform_info set publisher = '" + nowId + "' where publisher = '" + oldId
						+ "';" + "UPDATE hg_party_assigne set assigne_user_id = '" + nowId
						+ "' where assigne_user_id = '" + oldId + "';"
						+ "UPDATE hg_party_meeting_plan_info set check_person = '" + nowId + "' where check_person = '"
						+ oldId + "';" + "UPDATE hg_party_meeting_plan_info set check_person_org = '" + nowId
						+ "' where check_person_org = '" + oldId + "';"
						+ "UPDATE hg_value_attribute_info set user_id = '" + nowId + "' where user_id = '" + oldId
						+ "';" + "UPDATE hg_party_visit_count set user_id = '" + nowId + "' where user_id = '" + oldId
						+ "';" + "UPDATE hg_login set user_id = '" + nowId + "' where user_id = '" + oldId + "';";
		return sql;
	}
}
