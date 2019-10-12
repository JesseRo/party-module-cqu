package hg.party.command.userMeetingCount;



import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import party.constants.PartyPortletKeys;
/**
 * 查询command
 * @author Administrator
 *
 */

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.UserMeetingCount,
				"mvc.command.name=/meeting/FindRenderd"
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
