package party.portlet.loginDisplay;

import java.io.IOException;
import java.util.Properties;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.osgi.service.component.annotations.Component;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.entity.unity.CasProperties;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月10日下午3:05:19<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=false",//设为false，主题直接引入
			"javax.portlet.display-name=登陆、退出显示",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/loginDisplay/view.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.LoginDisplay,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class LoginDisplayPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String sessionId = renderRequest.getRequestedSessionId();
		//获取配置文件中cas登录地址
		CasProperties resourceProperties = new CasProperties();
		Properties properties = resourceProperties.getResourceProperties();//获取配置文件
		String casServer = properties.getProperty("casServer");//cas登录地址
		//用户显示名字
		String name = (String) SessionManager.getAttribute(sessionId, "user_name");
		String role = (String) SessionManager.getAttribute(sessionId, "role");
		
		renderRequest.setAttribute("name", name);
		renderRequest.setAttribute("role", role);
		renderRequest.setAttribute("casServer", casServer);
		super.doView(renderRequest, renderResponse);
	}
}
