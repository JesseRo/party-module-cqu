package hg.party.command.party;



import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.MeetingRecord,
				"javax.portlet.name=" + PartyPortletKeys.OrgCheckCountPortlet,
				"javax.portlet.name=" + PartyPortletKeys.SecondMeetingRecordPortlet,
				"javax.portlet.name=" + PartyPortletKeys.BranchMeetingRecordPortlet,
				"mvc.command.name=/meeting/FindRender"
		},
		service = MVCActionCommand.class
	)

public class FindMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(ActionRequest actionRequest,
			             ActionResponse actionResponse) throws PortletException {
		
		return false;
	}
}
