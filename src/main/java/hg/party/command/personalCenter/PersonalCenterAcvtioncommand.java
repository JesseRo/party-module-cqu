package hg.party.command.personalCenter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.personalCenter.PersonalCenterService;
import hg.util.MD5;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月11日上午10:03:43<br>
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
		"javax.portlet.name="+ PartyPortletKeys.PersonalCenter,
		"mvc.command.name=/personalCenter/updatePhone"
    },
    service = MVCActionCommand.class
)
public class PersonalCenterAcvtioncommand extends BaseMVCActionCommand {
	@Reference
	private PersonalCenterService personalCenterService;
	
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		String user_id = ParamUtil.getString(actionRequest, "user_id");//id
		String phone_number = ParamUtil.getString(actionRequest, "phone_number");//电话
		String member_mailbox = ParamUtil.getString(actionRequest, "member_mailbox");//邮箱
		String user_password = ParamUtil.getString(actionRequest, "user_password");//密码
		user_id = HtmlUtil.escape(user_id);
		phone_number = HtmlUtil.escape(phone_number);
		member_mailbox = HtmlUtil.escape(member_mailbox);
		user_password = HtmlUtil.escape(user_password);
		
		String user_passwordSql = personalCenterService.passWord(user_id).get(0).getUser_password();//判断密码是否改变
		if(!user_password.equals(user_passwordSql)){
			user_password = MD5.getMD5(user_password);	//密码加密
		}
		
		try {
			personalCenterService.updateUserPhone(phone_number,member_mailbox,user_password,user_id);
			personalCenterService.updateMemberPhone(phone_number,user_id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
