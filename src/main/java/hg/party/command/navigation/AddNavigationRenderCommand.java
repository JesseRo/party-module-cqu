package hg.party.command.navigation;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import hg.util.ConstantsKey;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月16日下午3:44:17<br>
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
			"mvc.command.name=/navigation/addNavigation"
	    },
	    service = MVCRenderCommand.class
	)
public class AddNavigationRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		//访问角色
		List<String> roles = new ArrayList<String>();
		roles.add("匿名");
		roles.add("组织部");
		roles.add(ConstantsKey.SECOND_PARTY);
		roles.add("党支部");
		roles.add("普通党员");
		renderRequest.setAttribute("roles", roles);
		//显示位置
		List<String> location = new ArrayList<String>();
		location.add("left");
		location.add("top");
		renderRequest.setAttribute("location", location);
		//父节点
		
		return "/jsp/navigation/addNavigation.jsp";
	}

}
