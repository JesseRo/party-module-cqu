package hg.party.command.PartyBranch;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
				"javax.portlet.name=" + PartyPortletKeys.NewPlan,
				"mvc.command.name=/hg/getGroup"
	    },
	    service = MVCResourceCommand.class
)
public class Groups implements MVCResourceCommand{
	@Reference
	PartyBranchService service;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		if (StringUtils.isEmpty(orgId)) {
		   orgId = (String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		}
		resourceResponse.setContentType("application/json");
		try {
			PrintWriter printWriter = resourceResponse.getWriter();
//			List<Map<String, Object>> list = service.getGroupsMap(orgId);
			printWriter.write(service.getGroups(orgId));
		} catch (Exception e) {
			
		}
		return false;
	}

}
