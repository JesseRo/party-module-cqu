package hg.party.command.login;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要：设置cas统一身份认证地址<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月11日下午3:48:11<br>
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
	"javax.portlet.name="+ PartyPortletKeys.Hgg_login,
	"mvc.command.name=/login/preferences"/*页面请求到该路径*/
},
service = MVCActionCommand.class
)
public class PreferencesActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		PortletPreferences preferences = actionRequest.getPreferences();
		//获取请求参数
		String urlAddress = ParamUtil.getString(actionRequest, "url_address");	
		urlAddress = HtmlUtil.escape(urlAddress);
		//设置参数
		preferences.setValue("urlAddress", urlAddress);
		//存储
		preferences.store();
	}

}

