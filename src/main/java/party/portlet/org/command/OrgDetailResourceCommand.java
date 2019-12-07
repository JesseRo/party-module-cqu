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
import java.util.HashMap;
import java.util.Map;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"mvc.command.name=/org/detail"
	    },
	    service = MVCResourceCommand.class
)
public class OrgDetailResourceCommand implements MVCResourceCommand {
	@Reference
	private OrgDao orgDao;
	@Reference
	private TransactionUtil transactionUtil;
	@Reference
	private MemberDao memberDao;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String orgId = ParamUtil.getString(resourceRequest, "id");
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		try {
			Organization organization = orgDao.findByOrgId(orgId);
			if (organization != null){
				res.getWriter().write(JSON.toJSONString(JsonResponse.Success(organization)));
			}else {
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
