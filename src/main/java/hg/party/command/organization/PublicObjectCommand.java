package hg.party.command.organization;


import java.io.PrintWriter;

import java.util.List;
import java.util.Map;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.osgi.service.component.annotations.Component;
import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.organization.AssignedPersonService;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;
@Component(
		immediate = true,
		property = {
				"javax.portlet.name="+ PartyPortletKeys.Form,
				"javax.portlet.name=" + PartyPortletKeys.NewPlan,
				"javax.portlet.name="+ PartyPortletKeys.SecondaryNewTaskPortlet,
				"javax.portlet.name="+ PartyPortletKeys.SecondaryTaskDetailPortlet,
			"mvc.command.name=/hg/getPublicObject"
	    },
	    service = MVCResourceCommand.class
)
public class PublicObjectCommand implements MVCResourceCommand{
	AssignedPersonService a = new AssignedPersonService();
	PartyBranchService service = new PartyBranchService();

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		orgId = orgId == null ? "050026623508" : orgId;

		String orgType = service.findSconedAndBranch(orgId);
		List<Map<String, Object>> listSconedParty = null;
		PrintWriter printWriter = null;
		try {
			if ("organization".equals(orgType)) {
				listSconedParty = a.findSconedParty();
			} else if ("secondary".equals(orgType)) {
				listSconedParty = a.findPartyBranch(orgId);
			}else if("propaganda".equals(orgType)){
				listSconedParty = a.findSconedParty();
			}
			printWriter = resourceResponse.getWriter();
			printWriter.write(JSON.toJSONString(listSconedParty));
		} catch (Exception e) {

		}
		return false;
	}

}
