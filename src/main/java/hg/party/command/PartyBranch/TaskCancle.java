package hg.party.command.PartyBranch;

import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name="+ PartyPortletKeys.TaskPortlet,
				"javax.portlet.name="+ PartyPortletKeys.SeocndCommitteeToDoList,			
				"mvc.command.name=/hg/taskCancle"
	    },
	    service = MVCResourceCommand.class
)
public class TaskCancle implements MVCResourceCommand{
	PartyBranchService service=new PartyBranchService();
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String infromId=ParamUtil.getString(resourceRequest, "infromId");
		String orgId=ParamUtil.getString(resourceRequest, "orgId");
		String cancleReson=ParamUtil.getString(resourceRequest, "cancleReson");
		infromId = HtmlUtil.escape(infromId);
		orgId = HtmlUtil.escape(orgId);
		cancleReson = HtmlUtil.escape(cancleReson);
		
		int n =service.cancleMeetingPlan(cancleReson, infromId, orgId);
		try {
			    PrintWriter printWriter=resourceResponse.getWriter();
			if (n==1) {
				printWriter.write("SUCCEE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
