package hg.party.command.PartyBranch;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
			"javax.portlet.name="+ PartyPortletKeys.PartySecondaryBranch,
			"javax.portlet.name="+ PartyPortletKeys.PartyMeetingPlan,
			"mvc.command.name=/hg/taskCheckReplyState"
	    },
	    service = MVCResourceCommand.class
)
public class CheckReplyState implements MVCResourceCommand {
    PartyBranchService service=new PartyBranchService();
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String path = ParamUtil.getString(resourceRequest, "path");
		String infromId = ParamUtil.getString(resourceRequest, "infromId");
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		String status = ParamUtil.getString(resourceRequest, "state");
		path = HtmlUtil.escape(path);
		infromId = HtmlUtil.escape(infromId);
		orgId = HtmlUtil.escape(orgId);
		status = HtmlUtil.escape(status);
		
		PrintWriter printWriter = null;
		
		String list = null;
		try {
			printWriter = resourceResponse.getWriter();
			if ("allState".equals(path)) {
//				  list = service.findReplyState(infromId, orgId);
				  list = service.findReplysState(infromId, orgId,"");
//				  printWriter.write(list);
			}else{
				if ("allState".equals(status)) {
					 list = service.findReplysState(infromId, orgId,"");
//					 printWriter.write(list);
				}else if ("read".equals(status)) {
					 list=service.findReplysState(infromId, orgId,"已查看");
//					 printWriter.write(list);
				}else if ("noRead".equals(status)) {
					 list=service.findReplysState(infromId, orgId,"未读");
//					 printWriter.write(list);
				}
			}
			 printWriter.write(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
