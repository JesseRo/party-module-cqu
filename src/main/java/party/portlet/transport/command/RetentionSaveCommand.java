package party.portlet.transport.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.entity.Retention;
import party.portlet.transport.entity.Transport;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.RelationRetentionPortlet,
				"mvc.command.name=/retention/save"
	    },
	    service = MVCResourceCommand.class
)
public class RetentionSaveCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;
	@Reference
	private OrgDao orgDao;

	@Reference
	private RetentionDao retentionDao;

	@Autowired
	private TransactionUtil transactionUtil;


	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String sessionId = resourceRequest.getRequestedSessionId();
		String orgId = (String)SessionManager.getAttribute(sessionId, "orgId");
		String userId = (String) SessionManager.getAttribute(sessionId, "userName");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Retention retention = new Retention();
		Member member = memberDao.findByUserId(userId);
		Organization organization = orgDao.findByOrgId(orgId);

		// 表单字段
		//["name", "sex", "ethnicity", "memberType", "joinDate", "org", "to_org_name",
		//				"to_org_contact", "birthday", "birthplace", "contact", "qq", "wechat", "email",
		//				"foreignLimit", "aboardDate", "returnDate", "toCountry", "studyDegree",
		//				"currentDegree", "studyType", "domestic", "domesticContactPerson",
		//				"domesticContactPhone", "extra"]

		String name = ParamUtil.getString(resourceRequest, "name");
		String sex = ParamUtil.getString(resourceRequest, "sex");
		String ethnicity = ParamUtil.getString(resourceRequest, "ethnicity");
		String memberType = ParamUtil.getString(resourceRequest, "memberType");
		String joinDate = ParamUtil.getString(resourceRequest, "joinDate");
		String org = ParamUtil.getString(resourceRequest, "org");
		String to_org_name = ParamUtil.getString(resourceRequest, "to_org_name");
		String to_org_contact = ParamUtil.getString(resourceRequest, "to_org_contact");
		String birthday = ParamUtil.getString(resourceRequest, "birthday");
		String birthplace = ParamUtil.getString(resourceRequest, "birthplace");
		String contact = ParamUtil.getString(resourceRequest, "contact");
		String qq = ParamUtil.getString(resourceRequest, "qq");
		String wechat = ParamUtil.getString(resourceRequest, "wechat");
		String email = ParamUtil.getString(resourceRequest, "email");
		String foreignLimit = ParamUtil.getString(resourceRequest, "foreignLimit");
		String aboardDate = ParamUtil.getString(resourceRequest, "aboardDate");
		String returnDate = ParamUtil.getString(resourceRequest, "returnDate");
		String toCountry = ParamUtil.getString(resourceRequest, "toCountry");
		String studyDegree = ParamUtil.getString(resourceRequest, "studyDegree");
		String currentDegree = ParamUtil.getString(resourceRequest, "currentDegree");
		String studyType = ParamUtil.getString(resourceRequest, "studyType");
		String domestic = ParamUtil.getString(resourceRequest, "domestic");
		String domesticContactPerson = ParamUtil.getString(resourceRequest, "domesticContactPerson");
		String domesticContactPhone = ParamUtil.getString(resourceRequest, "domesticContactPhone");
		String extra = ParamUtil.getString(resourceRequest, "extra");
		String isResubmit = ParamUtil.getString(resourceRequest, "isResubmit");



		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		Gson gson = new Gson();
		try {
			transactionUtil.startTransaction();
			if (isResubmit.equalsIgnoreCase("1")){
				Retention oriRetention = retentionDao.findByUser(userId);
				oriRetention.setStatus(6);
				retentionDao.saveOrUpdate(oriRetention);
			}
			retention.setUser_name(name);
			retention.setUser_id(userId);
			retention.setSex(sex);
			retention.setEthnicity(ethnicity);
			retention.setMember_type(memberType);
			retention.setJoin_date(joinDate);
			retention.setOrg_id(orgId);
			retention.setTo_org_id(to_org_name);
			retention.setTo_org_contact(to_org_contact);
			retention.setBirthday(birthday);
			retention.setBirth_place(birthplace);
			retention.setContact(contact);
			retention.setQq(qq);
			retention.setWechat(wechat);
			retention.setEmail(email);
			retention.setForeign_limit(foreignLimit);
			retention.setAboard_date(aboardDate);
			retention.setReturn_date(returnDate);
			retention.setTarget_country(toCountry);
			retention.setStudy_degree(studyDegree);
			retention.setCurrent_degree(currentDegree);
			retention.setStudy_type(studyType);
			retention.setDomestic_address(domestic);
			retention.setDomestic_contact(domesticContactPerson);
			retention.setDomestic_contact_number(domesticContactPhone);
			retention.setRetention_id(UUID.randomUUID().toString());
			retention.setTime(new Timestamp(System.currentTimeMillis()));
			retention.setStatus(ConstantsKey.INITIAL);
			retention.setExtra(extra);
			retentionDao.save(retention);

			transactionUtil.commit();
			res.getWriter().write(gson.toJson(JsonResponse.Success()));
		} catch (Exception e) {
			transactionUtil.rollback();
			try {
				e.printStackTrace();
				res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

}
