package hg.party.command.navigation;

import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.navigation.NavigationPermissionsServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月12日上午9:44:07<br>
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
			"mvc.command.name=/navigation/updateNavigation"
	    },
	    service = MVCRenderCommand.class
	)
public class UpdateNavigationRenderCommand implements MVCRenderCommand {

	@Reference
	private NavigationPermissionsServer navigationPermissionsServer;
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		String navigationId = ParamUtil.getString(renderRequest, "navigationId");
		
		navigationId= 	HtmlUtil.escape(navigationId);
		
		Map<String,Object> map = navigationPermissionsServer.findByNavigationId(navigationId);
		renderRequest.setAttribute("info", map);
		return "/jsp/navigation/updateNavigation.jsp";
	}

}
