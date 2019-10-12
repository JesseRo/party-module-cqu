package hg.party.command.organization;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import hg.party.server.organization.GraftService;
import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.PublicInformation,
			"mvc.command.name=/hg/getGrafts"
	    },
	    service = MVCResourceCommand.class
)
public class GraftCommand implements MVCResourceCommand{
   GraftService graftService=new GraftService();
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		return false;
	}

}
