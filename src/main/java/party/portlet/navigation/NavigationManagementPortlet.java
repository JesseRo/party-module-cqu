package party.portlet.navigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.navigation.NavigationPermissionsServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月11日下午3:42:25<br>
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
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=导航后台管理",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/navigation/managementView.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.NavigationManagement,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class NavigationManagementPortlet extends MVCPortlet {
	@Reference
	private NavigationPermissionsServer navigationPermissionsServer;
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		//获取查询输入框内的title
		String role=ParamUtil.getString(renderRequest, "title");
		role = HtmlUtil.escape(role);
//		String sql=generalSql(role);
		Map<String, Object> postgre;//查询结果集
//		postgre=pagenation(renderRequest,sql);
		postgre = pagenation(renderRequest,role);
		List<Map<String,Object>> lists = (List<Map<String, Object>>) postgre.get("list");
		
		//存处理后的数据，传到jsp页面
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:lists){
			Map<String,Object> map1=new HashMap<String,Object>();
			//节点id
			map1.put("navigationId", map.get("navigation_id"));
			//节点名称
			map1.put("name", map.get("navigation_name"));
			//节点等级
			if(map.get("parent_id")==null || "".equals(map.get("parent_id"))){
				map1.put("level", "一级菜单");
			}else{
				map1.put("level", "二级菜单");
			}
			//显示位置
			map1.put("location", map.get("show_location"));
			//所属角色
			map1.put("role", map.get("navigation_to_role"));
			list.add(map1);
			
		}
		int pageNo=(int) postgre.get("pageNow");//当前页
		int pages=(int) postgre.get("totalPage");//总页数
		renderRequest.setAttribute("title", role);
		renderRequest.setAttribute("pageNo", pageNo);
		renderRequest.setAttribute("sum", pages);
		renderRequest.setAttribute("list", list);
		super.doView(renderRequest, renderResponse);
	}
	
	//分页
	public Map<String, Object> pagenation(RenderRequest req,String role){
		//当前页
		int pageNo;
		int page=ParamUtil.getInteger(req, "pageNo");
		if(page==0){
			pageNo=1;//默认当前页为1
		}else{
			pageNo=page;
		}
		int pageSize=10;//每页显示条数
		String sql = "";
		if(role == null || "".equals(role)){
			sql="SELECT * FROM hg_party_navigation_permissions ORDER BY navigation_to_role DESC, navigation_id asc ";
			return  navigationPermissionsServer.pagenation(pageNo, pageSize, sql);
		}else{
			sql="SELECT * FROM hg_party_navigation_permissions WHERE navigation_to_role=?  ORDER BY navigation_id asc ";
			return  navigationPermissionsServer.pagenation(pageNo, pageSize, sql,role);
		}
		
	}
	
	//sql
	public String generalSql(String role){
		String sql;
		if(role == null || "".equals(role)){
			sql="SELECT * FROM hg_party_navigation_permissions ORDER BY navigation_to_role DESC";
		}else{
			sql="SELECT * FROM hg_party_navigation_permissions WHERE navigation_to_role='"+role+"'";
		}
		
		return sql;
	}
}
