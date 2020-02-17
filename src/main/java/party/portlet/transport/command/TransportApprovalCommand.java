package party.portlet.transport.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.beans.BeanUtils;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.dao.TransportDao;
import party.portlet.transport.entity.Retention;
import party.portlet.transport.entity.Transport;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.TransportApproval,
				"mvc.command.name=/transport/approval"
	    },
	    service = MVCResourceCommand.class
)
public class TransportApprovalCommand implements MVCResourceCommand {
	@Reference
	private TransportDao transportDao;
	@Reference
	private UserDao userDao;
	@Reference
	private MemberDao memberDao;
	@Reference
	private TransactionUtil transactionUtil;
	@Reference
	private RetentionDao retentionDao;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		Gson gson = new Gson();
		String userId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");

		String transportId = ParamUtil.getString(resourceRequest, "id");
		String type = ParamUtil.getString(resourceRequest, "type");
		int status = ParamUtil.getInteger(resourceRequest, "status");
		transactionUtil.startTransaction();
		try {
			if (type.equalsIgnoreCase("transport")){
				Transport transport = transportDao.findById(transportId);
				if(status == ConstantsKey.APPROVED) {
					if ((transport.getType().equalsIgnoreCase("0")
							|| transport.getType().equalsIgnoreCase("1"))){
						// 院内
						User user = userDao.findUserByEthnicity(transport.getUser_id());
						Member member = memberDao.findByUserId(transport.getUser_id());

						Member newMember = new Member();
						BeanUtils.copyProperties(member, newMember, "id");
						member.setHistoric(true);
						newMember.setMember_org(transport.getOrg_id());
						user.setUser_department_id(transport.getOrg_id());
						userDao.saveOrUpdate(user);
						memberDao.save(newMember);
						memberDao.saveOrUpdate(member);
					}
					transportDao.update(transport);
				}else if(status == ConstantsKey.CONFIRM) {
					if (transport.getType().equalsIgnoreCase("2")
						|| transport.getType().equalsIgnoreCase("3")){
						User user = userDao.findUserByEthnicity(transport.getUser_id());
						Member member = memberDao.findByUserId(transport.getUser_id());
						member.setHistoric(true);
						userDao.delete(user);
						memberDao.saveOrUpdate(member);
					}
				}
				transport.setStatus(status);
				transport.setOperator(userId);
				transportDao.saveOrUpdate(transport);
			}else {
				Retention retention = retentionDao.findById(transportId);
				retention.setStatus(status);
				retention.setOperator(userId);
				retentionDao.update(retention);
			}
			transactionUtil.commit();
			res.getWriter().write(gson.toJson(JsonResponse.Success()));
		}catch (Exception e){
			transactionUtil.rollback();
			try {
				e.printStackTrace();
				res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}

}
