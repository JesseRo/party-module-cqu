package hg.party.command.pagenation;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.NewsCenter,
				"javax.portlet.name=" + PartyPortletKeys.PartyMeetingPlan,
				"javax.portlet.name=" + PartyPortletKeys.PartyApprovalPlan,
				"javax.portlet.name=" + PartyPortletKeys.PartyApprovalBranch,
				"javax.portlet.name=" + PartyPortletKeys.PartySecondary,
				"javax.portlet.name=" + PartyPortletKeys.ToDoList,
				"javax.portlet.name=" + PartyPortletKeys.NavigationManagement,
				"javax.portlet.name=" + PartyPortletKeys.SeocndCommitteeToDoList,
				"javax.portlet.name=" + PartyPortletKeys.SeocndCommitteeArrangeVenue,
				"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
				"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
				"javax.portlet.name=" + PartyPortletKeys.SecondPartyReviewContent,
				"javax.portlet.name=" + PartyPortletKeys.OrganizationReviewContent,
				"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
				"mvc.command.name=/PageNoMVCActionCommand"
		},
		service = MVCActionCommand.class
	)

public class PageNoMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		return false;
	}

	

}
