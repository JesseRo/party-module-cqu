package party.log;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.server.dwonlistserver.DownListServer;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static hg.util.ConstantsKey.DROPDOWN_TYPES_MAPPING;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.cqu",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=系统日志",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/log/view.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/downlist/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.Log,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class LogPortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(LogPortlet.class);
	@Reference
	private DownListServer listServer;

	public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
		super.doView(req, res);
	}
}


