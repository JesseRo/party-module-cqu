package party.portlet.login;

import party.constants.PartyPortletKeys;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import dt.session.SessionManager;
import hg.util.PropertiesUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author anran
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=登录-z",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.portlet-mode=text/html;view,edit",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.init-param.view-template=/jsp/login/login.jsp",
		"javax.portlet.init-param.edit-template=/jsp/login/edit.jsp",
		"javax.portlet.name=" + PartyPortletKeys.Hgg_login,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class Hgg_loginPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String sessionId=renderRequest.getRequestedSessionId();
		HttpServletRequest request=PortalUtil.getHttpServletRequest(renderRequest);
		/**评论页面的路径*/
		String url=PortalUtil.getOriginalServletRequest(request).getParameter("url");
		/**资源id及resource_id*/
		String ids=PortalUtil.getOriginalServletRequest(request).getParameter("ids");
	    /**转跳到评论登录页*/
		String urlAction=url+"?"+"id="+ids;
		if (ids!=null) {
			SessionManager.setAttribute("urlAction", "urlAction", urlAction);
		}
		renderRequest.setAttribute("urlAction",SessionManager.getAttribute("urlAction", "urlAction"));
		renderRequest.setAttribute("userName",SessionManager.getAttribute(sessionId, "userName"));
		renderRequest.setAttribute("user_name", SessionManager.getAttribute(sessionId, "user_name"));
		if (ids==null) {
			SessionManager.removeSession("urlAction");
		}
		
		 ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);	 
		 PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
			//System.out.println("portletDisplay---->"+portletDisplay.getInstanceId());
			//获取portletid
			renderRequest.setAttribute("portletID", "p_p_id_"+portletDisplay.getId()+"_");
			//System.out.println("portletDisplay getId---->"+portletDisplay.getId());
			//System.out.println("portletDisplay getPortletName---->"+portletDisplay.getPortletName());
		
		PropertiesUtil propertiesUtil = new PropertiesUtil("/cas.properties");
		Properties properties = propertiesUtil.getResourceProperties();
		String urlAddress = properties.getProperty("casServer");
		urlAddress += URLEncoder.encode(
				PortalUtil.getOriginalServletRequest(request).getRequestURL().toString(), "utf-8");
		renderRequest.setAttribute("urlAddress", urlAddress);	
		// cas统一身份认证地址
//		PortletPreferences preferences = renderRequest.getPreferences();
//		String urlAddress = preferences.getValue("urlAddress", "");
//		renderRequest.setAttribute("urlAddress", urlAddress);
		super.doView(renderRequest, renderResponse);
	}
	@Override
	protected void sendRedirect(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException {
		String sessionId=actionRequest.getRequestedSessionId();
		actionRequest.setAttribute("userName",SessionManager.getAttribute(sessionId, "userName"));
		actionRequest.setAttribute("user_name", SessionManager.getAttribute(sessionId, "user_name"));
		super.sendRedirect(actionRequest, actionResponse);
	}
}