package party.portlet.navigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import dt.session.SessionManager;
import hg.party.entity.unity.CasProperties;
import hg.party.server.navigation.NavigationPermissionsServer;
import hg.party.server.organization.UserRoleService;
import hg.util.ConstantsKey;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月10日上午9:29:00<br>
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
			"javax.portlet.display-name=顶部导航权限",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/navigation/topView.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.TopNavigation,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class TopNavigationPortlet extends MVCPortlet {

	private static final String NAME="网站首页";
	private static final String LOCATION="top";
	@Reference
	private NavigationPermissionsServer navigationPermissionsServer;
	 @Reference
	 private UserRoleService userRoleService;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		//网站首页导航
		List<Map<String,Object>> groups=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map=navigationPermissionsServer.findHomePage(NAME);
//		groups.add(map);

		//保存数据

		
		String sessionId=renderRequest.getRequestedSessionId();
		String orgId=(String) SessionManager.getAttribute(sessionId, "department");
		String role=(String) SessionManager.getAttribute(sessionId, "role");
		List<Map<String,Object>> lists = null;
		if(("".equals(orgId)|| orgId==null) && ("".equals(role) || role==null)){
			
		}else if("普通党员".equals(role) || "其他".equals(role)){
			lists=navigationPermissionsServer.findNavigationByRoleAndDepartment("null", "普通党员",LOCATION);
		}else{
//			String department=(String) navigationPermissionsServer.findOrgType(orgId).get("org_type");
//			if("secondary".equals(department)){
//				department=ConstantsKey.SECOND_PARTY;
//			}else if("branch".equals(department)){
//				department="党支部";
//			}else if("organization".equals(department)){
//				department="组织部";
//			}else{
//				
//			}
			String department = role;
			lists=navigationPermissionsServer.findNavigationByRoleAndDepartment(department, role,LOCATION);
		}
		if("".equals(lists)|| lists==null){
			//匿名登陆，没有部门和角色，只显示网站首页导航
		}else{
			for(Map<String,Object> map1:lists){
				groups.add(map1);
			}
		}
		
		//String sessionId = renderRequest.getRequestedSessionId();
		//获取配置文件中cas登录地址
		CasProperties resourceProperties = new CasProperties();
		Properties properties = resourceProperties.getResourceProperties();//获取配置文件
		String casServer = properties.getProperty("casServer");//cas登录地址
		//用户显示名字
		String name = (String) SessionManager.getAttribute(sessionId, "user_name");
		Object userId =SessionManager.getAttribute(sessionId, "userName");
		//String role = (String) SessionManager.getAttribute(sessionId, "role");
		if (!StringUtils.isEmpty(userId)) {
			List<String> roles = userRoleService.getRoles((String)userId);
			renderRequest.setAttribute("roles",  roles);
		}
		renderRequest.setAttribute("name", name);
		renderRequest.setAttribute("role", role);
		renderRequest.setAttribute("casServer", casServer);
		renderRequest.setAttribute("lists", groups);
		super.doView(renderRequest, renderResponse);
	}
}
