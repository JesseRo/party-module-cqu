package party.portlet.cqu;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import org.osgi.service.component.annotations.Component;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.cqu",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=督察人员管理",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/check/view.jsp",
			"javax.portlet.name=" + PartyPortletKeys.CheckPerson,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class CheckPersonPortlet extends MVCPortlet {

	public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
		super.doView(req, res);
	}
}


