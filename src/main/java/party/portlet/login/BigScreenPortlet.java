package party.portlet.login;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import dt.session.SessionManager;
import hg.util.ConstantsKey;
import hg.util.PropertiesUtil;
import org.osgi.service.component.annotations.Component;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=大屏幕",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/login/screen.jsp",
//                "javax.portlet.init-param.edit-template=/jsp/login/edit.jsp",
                "javax.portlet.name=" + PartyPortletKeys.BigScreenPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class BigScreenPortlet extends MVCPortlet {
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String sessionId=renderRequest.getRequestedSessionId();
        String role = (String)SessionManager.getAttribute(sessionId, "role");
        String url;
        if (ConstantsKey.SECOND_PARTY.equals(role)) {
            url = "/backlogtwo";
        } else if (ConstantsKey.ORG_PARTY.equals(role)) {
            url = "/statisticalreport";
        } else if (ConstantsKey.BRANCH_PARTY.equals(role)) {
            url = "/task";
        } else {
            url = "/home";
        }
        renderRequest.setAttribute("turnTo", url);
        super.doView(renderRequest, renderResponse);
    }
}
