package hg.party.command.contentRelease;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
			"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
			"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
			"mvc.command.name=/currentpage/contentreleaseactioncommand"
	    },
	    service = MVCActionCommand.class
)
public class ContentReleaseActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
	}

}
