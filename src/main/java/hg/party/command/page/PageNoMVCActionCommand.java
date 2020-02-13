package hg.party.command.page;



import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.Graft,
				"javax.portlet.name=" + PartyPortletKeys.AlreadyPublic,
				"javax.portlet.name=" + PartyPortletKeys.MeetingRecord,
				"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
				"javax.portlet.name=" + PartyPortletKeys.TaskPortlet,
				"javax.portlet.name=" + PartyPortletKeys.PartyEntry,
				"javax.portlet.name=" + PartyPortletKeys.PartyEntryOrg,
				"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
				"javax.portlet.name=" + PartyPortletKeys.PartySecondary,
				"javax.portlet.name=" + PartyPortletKeys.PartyMeetingPlan,
				"javax.portlet.name=" + PartyPortletKeys.PartyApprovalPlan,
				"javax.portlet.name=" + PartyPortletKeys.PartyApprovalBranch,
				"javax.portlet.name=" + PartyPortletKeys.DownList,
				"javax.portlet.name=" + PartyPortletKeys.OrgCheckCountPortlet,
				"javax.portlet.name=" + PartyPortletKeys.OrgCheckPortlet,
				"javax.portlet.name=" + PartyPortletKeys.SecondMeetingRecordPortlet,
				"javax.portlet.name=" + PartyPortletKeys.BranchMeetingRecordPortlet,
				"javax.portlet.name=" + PartyPortletKeys.UserMeetingCount,
				"javax.portlet.name=" + PartyPortletKeys.SecondaryTaskReportPortlet,
				"javax.portlet.name=" + PartyPortletKeys.BrunchReportPortlet,
				"javax.portlet.name=" + PartyPortletKeys.SecondaryTaskDraftPortlet,
				"javax.portlet.name=" + PartyPortletKeys.SecondaryTaskPortlet,
				"javax.portlet.name=" + PartyPortletKeys.LeaderMeetingStatistics,
				"mvc.command.name=/PageNoMVCActionCommand"
		},
		service = MVCActionCommand.class
	)

public class PageNoMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(ActionRequest actionRequest,
			             ActionResponse actionResponse) throws PortletException {
		
		return false;
	}
}
