package hg.party.command.navigation;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.entity.navigation.NavigationPermissions;
import hg.party.server.navigation.NavigationPermissionsServer;
import party.constants.PartyPortletKeys;

import java.util.UUID;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月18日下午2:05:53<br>
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
			"mvc.command.name=/party/addNav"
	    },
	    service = MVCActionCommand.class
	)
public class SaveAddNavigationActionCommand extends BaseMVCActionCommand {

	@Reference
	private NavigationPermissionsServer navigationPermissionsServer;
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		String navName = ParamUtil.getString(actionRequest, "navigationName");
		String accessRole = ParamUtil.getString(actionRequest, "accessRole");
		String showLocation = ParamUtil.getString(actionRequest, "showLocation");
		String parentNode = ParamUtil.getString(actionRequest, "parent_node");
		String navSort = ParamUtil.getString(actionRequest, "navSort");
		String url = ParamUtil.getString(actionRequest, "hrefUrl");
		
		navName = HtmlUtil.escape(navName);
		accessRole = HtmlUtil.escape(accessRole);
		showLocation = HtmlUtil.escape(showLocation);
		parentNode = HtmlUtil.escape(parentNode);
		navSort = HtmlUtil.escape(navSort);
		url = HtmlUtil.escape(url);
		
		NavigationPermissions navigation = new NavigationPermissions();
		//navigationId
		navigation.setNavigationId(UUID.randomUUID().toString());
		//导航名字
		navigation.setNavigationName(navName);
		//导航跳转路径
		navigation.setNavigationUrl(url);
		//导航显示位置
		navigation.setShowLocation(showLocation);
		//导航角色权限和部门
		if("普通党员".equals(accessRole)){
			navigation.setDepartment("null");
		}else{
			navigation.setDepartment(accessRole);
		}
		navigation.setNavigationToRole(accessRole);
		
		//parentId
		if("无".equals(parentNode)){
			
		}else{
			String parentId = (String) navigationPermissionsServer.findNavId(parentNode, accessRole).get("navigation_id");
			navigation.setParentId(parentId);
			//显示顺序
			if("无".equals(navSort) || "".equals(navSort) || navSort==null){
				navigation.setNavigationSort(0);
			}else{
				int sortNum = (int) navigationPermissionsServer.findNavSort(navSort, parentId).get("navigation_sort");
				navigation.setNavigationSort(sortNum+1);
			}
		}
		navigationPermissionsServer.saveNewNavigation(navigation);
	}

}
