package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"mvc.command.name=/org/edit"
	    },
	    service = MVCResourceCommand.class
)
public class OrgEditResourceCommand implements MVCResourceCommand {
	@Reference
	private OrgDao orgDao;
	@Reference
	private TransactionUtil transactionUtil;
	@Reference
	private MemberDao memberDao;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		String orgName = ParamUtil.getString(resourceRequest, "orgName");
		String address = ParamUtil.getString(resourceRequest, "address");
		String contactNumber = ParamUtil.getString(resourceRequest, "contactNumber");
		String fax = ParamUtil.getString(resourceRequest, "fax");
		String secretary = ParamUtil.getString(resourceRequest, "secretary");
		String email = ParamUtil.getString(resourceRequest, "email");
		String contactor = ParamUtil.getString(resourceRequest, "contactor");
		String contactorNumber = ParamUtil.getString(resourceRequest, "contactorNumber");
		try {
			Organization organization = orgDao.findByOrgId(orgId);
			if (organization != null){
				organization.setOrg_name(orgName);
				organization.setOrg_address(address);
				organization.setOrg_phone_number(contactNumber);
				organization.setOrg_fax(fax);
				organization.setOrg_secretary(secretary);
				organization.setOrg_email(email);
				organization.setOrg_contactor(contactor);
				organization.setOrg_contactor_phone(contactorNumber);
				orgDao.updateDetail(organization);
				res.getWriter().write(JSON.toJSONString(JsonResponse.Success(organization)));
			} else {
				res.getWriter().write(JSON.toJSONString(JsonResponse.Failure("")));

			}
		} catch (Exception e) {
			try {
				res.getWriter().write(JSON.toJSONString(JsonResponse.Failure("")));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
}
