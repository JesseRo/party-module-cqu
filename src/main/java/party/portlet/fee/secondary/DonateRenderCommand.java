package party.portlet.fee.secondary;

import com.google.gson.internal.$Gson$Preconditions;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.fee.dao.FeeDao;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;
import java.util.Map;

@Component(
		immediate = true,
		property = {
		"javax.portlet.name=" + PartyPortletKeys.SecondaryDonateStatisticsPortlet,
		"mvc.command.name=/donate/render"
		},
		service = MVCRenderCommand.class
	)
public class DonateRenderCommand implements MVCRenderCommand {
	@Reference
	private FeeDao feeDao;

	@Reference
	private OrgDao orgDao;
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		List<Map<String, Object>> donates = feeDao.allDonate();
		renderRequest.setAttribute("tasks", donates);
		String view;

		String orgId = ParamUtil.getString(renderRequest, "orgId");
		if (StringUtils.isEmpty(orgId)) {
			orgId = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
		}
		List<Organization> orgs = orgDao.findChildren(orgId);

		if (orgs == null || orgs.size() == 0) {
			view = "/jsp/fee/branch/donate_statistics.jsp";
		} else {
			view = "/jsp/fee/secondary/donate_statistics.jsp";
		}
		renderRequest.setAttribute("orgId", orgId);
		return view;
	}
	
}