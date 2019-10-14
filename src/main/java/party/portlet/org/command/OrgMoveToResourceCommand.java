package party.portlet.org.command;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.partyMembers.Member;
import hg.party.server.partyBranch.PartyBranchService;
import hg.util.TransactionUtil;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.Org,
				"mvc.command.name=/hg/org/move/org"
	    },
	    service = MVCResourceCommand.class
)
/**
 * 组织关系移动
 * @author gongmingbo
 *
 */
public class OrgMoveToResourceCommand implements MVCResourceCommand {
	Logger log = Logger.getLogger(OrgMoveToResourceCommand.class);
	@Reference
	private MemberDao memberDao;
	@Reference
	private UserDao UserDao;
	@Reference
	TransactionUtil transactionUtil;
	@Reference
	private OrgDao orgDao;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		Object userId_ = SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");
		String moveToOrgId = ParamUtil.getString(resourceRequest, "moveToOrgId");
		String userId = ParamUtil.getString(resourceRequest, "userId");
		List<Member> list = memberDao.findMemberByUserId(userId);
		Map<String, Object> map = new HashMap<>();
		int i = 0, j = 0, k = 0;
		try {
			transactionUtil.startTransaction();
			Member m = new Member();
			if (list != null && list.size() > 0) {
				m = list.get(0);
				String insertSql = "INSERT INTO hg_party_member (\"member_name\", \"member_sex\", \"member_ethnicity\", \"member_age\""
						+ ", \"member_birthday\", \"member_identity\", \"member_degree\", \"member_job\", \"member_join_date\""
						+ ", \"member_fomal_date\", \"member_org\", \"member_type\", \"member_address\", \"member_phone_number\""
						+ ", \"member_landline_number\", \"member_is_outofcontact\", \"member_outofcontact_date\", \"member_is_flow\""
						+ ", \"member_flow_to\", \"member_membership_state\", \"member_mailbox\", \"historic\", \"member_party_position\""
						+ ", \"member_major_title\", \"member_new_class\", \"member_front_line\", \"member_party_committee\", \"member_birth_place\", \"member_marriage\")"
						+ "VALUES ('" + m.getMember_name() + "', '" + m.getMember_sex() + "', '"
						+ m.getMember_ethnicity() + "', NULL, '" + m.getMember_birthday() + "', '"
						+ m.getMember_identity() + "', '" + m.getMember_degree() + "', '" + m.getMember_job() + "', '"
						+ m.getMember_join_date() + "', '" + m.getMember_fomal_date() + "', '" + moveToOrgId + "', '"
						+ m.getMember_type() + "', '" + m.getMember_address() + "', '" + m.getMember_phone_number()
						+ "', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '" + false + "', NULL, NULL, NULL, NULL, '"
						+ m.getMember_party_committee() + "', '" + m.getMember_birth_place() + "', '" + m.getMember_marriage() + "');";
				insertSql = insertSql.replaceAll("'null'", "NULL");
				String updateSql = "UPDATE hg_party_member set historic=" + true + " WHERE member_identity='" + userId+ "'";
				String deleteSsql = "delete from hg_party_group_member_info where participant_id='"+userId+"'";
				String deleteAssigneSql = "DELETE from hg_party_assigne  where assigne_user_id='"+userId+"'";
				int n = memberDao.insertOrUpate(deleteSsql);
				i = memberDao.insertOrUpate(updateSql);
				j = memberDao.insertOrUpate(insertSql);
				k = UserDao.update(userId, moveToOrgId);
				orgDao.deleteAdmin(userId);
				memberDao.insertOrUpate(deleteAssigneSql);
				if (j > 0 && i > 0 && k > 0) {
					map.put("state", true);
					log.info("移动组织 :[" + new Date() + "] [by " + userId_ + "]  移动人员userId :[" + userId + "]");
					transactionUtil.commit();
				} else {
					transactionUtil.rollback();
				}
			}
			PrintWriter printWriter = resourceResponse.getWriter();
			printWriter.write(JSON.toJSONString(map));
		} catch (Exception e) {
			log.info("移动组织 异常:[" + new Date() + "] [by " + userId_ + "]  移动人员userId :[" + userId + "]");
			transactionUtil.rollback();
		}
		return false;
	}

}
