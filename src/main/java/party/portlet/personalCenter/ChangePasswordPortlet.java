package party.portlet.personalCenter;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.server.personalCenter.PersonalCenterService;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Map;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=修改密码",
		"javax.portlet.init-param.template-path=/",
		
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.portlet-mode=text/html;view,edit",
		"javax.portlet.init-param.view-template=/jsp/personalCenter/resetPassword.jsp",
		"javax.portlet.name=" + PartyPortletKeys.ChangePasswordPortlet,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ChangePasswordPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		super.doView(renderRequest, renderResponse);
	}
}
