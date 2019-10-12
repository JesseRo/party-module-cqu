package hg.party.command.organization;

import java.io.PrintWriter;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import hg.party.server.organization.PublicInformationService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
			"javax.portlet.name="+ PartyPortletKeys.TaskPortlet,
			"javax.portlet.name="+ PartyPortletKeys.AssignedPersonPorlet,
			"javax.portlet.name="+ PartyPortletKeys.MeetingRecord,
			"javax.portlet.name="+ PartyPortletKeys.PartySecondary,
			"javax.portlet.name="+ PartyPortletKeys.PartyMeetingPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
			"javax.portlet.name=" + PartyPortletKeys.OrgCheckCountPortlet,
			"javax.portlet.name=" + PartyPortletKeys.SecondMeetingRecordPortlet,
			"javax.portlet.name=" + PartyPortletKeys.BranchMeetingRecordPortlet,
			"mvc.command.name=/hg/getMeetingTypeAndTheme"
	    },
	    service = MVCResourceCommand.class
)
public class MeetingTypeAndTheme implements MVCResourceCommand{
   @Reference 
   private  PublicInformationService service;

@Override
public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {
	         try {
				  PrintWriter printWriter=resourceResponse.getWriter();
				  printWriter.write(JSON.toJSONString(service.findMeetingTypeAndTheme()));
			} catch (Exception e) {
				  e.printStackTrace();
			}
	return false;
}
	
}
