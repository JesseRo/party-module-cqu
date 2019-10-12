package hg.party.command.navigation;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月12日上午10:14:35<br>
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
				"mvc.command.name=/navigation/findRole"
		},
		service = MVCActionCommand.class
		)
public class FindNavigationByRoleActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		// TODO Auto-generated method stub
		return false;
	}

}
