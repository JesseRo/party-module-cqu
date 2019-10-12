package party.portlet.organization;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import hg.party.server.organization.AssignedPersonService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=name",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/organization/sconedPartySchedule.jsp",
			"javax.portlet.name=" + PartyPortletKeys.SconedPartySchedule,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class SconedPartySchedule extends MVCPortlet{
	AssignedPersonService service=new AssignedPersonService();
     @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
    		throws IOException, PortletException {
    	
    	super.doView(renderRequest, renderResponse);
    }
}
