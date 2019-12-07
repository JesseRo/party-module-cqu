package hg.party.command.navigation;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.navigation.NavigationPermissionsServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月12日下午5:10:39<br>
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
			"mvc.command.name=/navigation/update"
	    },
	    service = MVCActionCommand.class
	)
public class SaveUpdateActionCommand extends BaseMVCActionCommand {

	@Reference
	private NavigationPermissionsServer navigationPermissionsServer;
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		String navName = ParamUtil.getString(actionRequest, "navName");
		String location = ParamUtil.getString(actionRequest, "location");
		String role = ParamUtil.getString(actionRequest, "role");
		String path = ParamUtil.getString(actionRequest, "path");
		String navigationId = ParamUtil.getString(actionRequest, "navigationId");

		navName =	HtmlUtil.escape(navName);
		path =	HtmlUtil.escape(path);
		location =	HtmlUtil.escape(location);
		role =	HtmlUtil.escape(role);
		navigationId =	HtmlUtil.escape(navigationId);
		
		
		navigationPermissionsServer.updateNavigation(navName, location, role, navigationId, path);
	}

}
