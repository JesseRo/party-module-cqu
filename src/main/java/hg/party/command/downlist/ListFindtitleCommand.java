package hg.party.command.downlist;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.DownList,
				"mvc.command.name=/listfindtitlecommand"
		},
		service = MVCActionCommand.class
		)
public class ListFindtitleCommand implements MVCActionCommand{
	
	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		return false;
	}
}
