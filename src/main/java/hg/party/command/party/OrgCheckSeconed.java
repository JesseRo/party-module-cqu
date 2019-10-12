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
				"javax.portlet.name=" + PartyPortletKeys.PartySecondary,
				"javax.portlet.name=" + PartyPortletKeys.OrganizationReviewContent,
				"javax.portlet.name=" + PartyPortletKeys.SecondPartyReviewContent,
				"mvc.command.name=/OrgCheckSeconed"
		},
		service = MVCActionCommand.class
	)

public class OrgCheckSeconed implements MVCActionCommand{

	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) 
			throws PortletException {
		
		return false;
	}

}
