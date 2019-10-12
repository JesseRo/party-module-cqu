package hg.party.command.navigation;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;

import hg.party.server.navigation.NavigationPermissionsServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月17日下午5:08:44<br>
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
			"javax.portlet.name=" + PartyPortletKeys.NavigationManagement,
			"mvc.command.name=/navigation/sort"
	    },
		service=MVCResourceCommand.class
	)
public class SortResourceCommand implements MVCResourceCommand {

	@Reference
	private NavigationPermissionsServer navigationPermissionsServer;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		PrintWriter printWriter=null;
		try {
			String parentNode = resourceRequest.getParameter("parentNode");
			String role = resourceRequest.getParameter("role");
			String location = resourceRequest.getParameter("location");
			
			parentNode = HtmlUtil.escape(parentNode);
			role = HtmlUtil.escape(role);
			location = HtmlUtil.escape(location);
			
			List<Map<String,Object>> lists;
			if("无".equals(parentNode) || "".equals(parentNode) || parentNode == null){
				Map<String,Object> map = new HashMap<String,Object>();
				//根据角色和位置，获取父节点下拉选项值
				if("top".equals(location)){
					map = navigationPermissionsServer.findHomePage("网站首页");
				}
				map.put("navigation_name", "无");
				lists=navigationPermissionsServer.findParentNode(role, location);
				lists.add(map);
			}else{
				lists = navigationPermissionsServer.findSonNode(parentNode, role);
			}
			printWriter = resourceResponse.getWriter();
			printWriter.write(JSON.toJSONString(lists));//传值到jsp
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
