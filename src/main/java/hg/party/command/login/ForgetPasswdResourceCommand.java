package hg.party.command.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.dao.resetPasswd.ResetPasswdDao;
import hg.party.entity.login.ResetPasswd;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;
import hg.party.server.resetPasswd.ResetPasswdService;
import hg.party.unity.PasswdCreate;
import hg.party.unity.ResourceProperties;
import hg.util.MD5;
import hg.util.MailSend;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： 忘记密码<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月16日下午4:39:09<br>
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
		"mvc.command.name=/hg/forgetPasswd"
    },
    service = MVCResourceCommand.class
)
public class ForgetPasswdResourceCommand implements MVCResourceCommand{
	@Reference
	private ResetPasswdService resetPasswdService;
	PasswdCreate passwd = new PasswdCreate();
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		PrintWriter printWriter = null;
		try {
			printWriter = resourceResponse.getWriter();
			Map map = new HashMap<String, String>();
			String user_id = ParamUtil.getString(resourceRequest, "user_id");//账号
			user_id = 	HtmlUtil.escape(user_id);
			String user_name = ParamUtil.getString(resourceRequest, "user_name");//姓名
			user_name = 	HtmlUtil.escape(user_name);
			User user = resetPasswdService.findUserByUserIdAndUserName(user_id, user_name);
			if(user==null){
				map.put("status", "输入信息有误，请重新输入！");
				printWriter.write(JSON.toJSONString(map));
				return false;
			}
			if(permitCrearPasswd(user_id)){
				//更改密码
				String newPasswd = passwd.createPassWord(6);
				resetPasswdService.updateUserPasswd(MD5.getMD5(newPasswd), user_id);
				//获取账号邮箱
				String receiveMailAccount = resetPasswdService.findMemberByIdentity(user_id).getUser_mailbox();
				ResourceProperties resource = new ResourceProperties();
				Properties re = resource.getResourceProperties();
				String sserMailboxes = re.getProperty("UserMailboxes");//发件人邮箱
				String authorizationCode = re.getProperty("AuthorizationCode");//发件人授权码
				List<String> receiveMailAccountList = null;
				StringBuffer receiveMailAccountBuf = null;
				MailSend mail = new MailSend(sserMailboxes, authorizationCode, receiveMailAccount, receiveMailAccountList, receiveMailAccountBuf);
				String mailTitle = "您正在修改党务工作信息平台密码";
				String msgPassword = "西南大学党务工作信息平台提醒您：\n您的密码修改成功，密码："+newPasswd+",请妥善保管!";
				if(mail.sendMail(mailTitle, msgPassword)){
					map.put("status", "密码已发送至您邮箱，请查看!");
					printWriter.write(JSON.toJSONString(map));
					resetPasswdService.saveResetPasswd(user_id, user_name, newPasswd, new Timestamp(new Date().getTime()));
					return false;
				}else{
					map.put("status", "邮件发送失败，请联系系统管理员!");
					printWriter.write(JSON.toJSONString(map));
					return false;
				}
			}else{
				map.put("status", "不能频繁修改密码，请稍后再试!");
				printWriter.write(JSON.toJSONString(map));
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	/**
	 * 判断距离上次修改时间是否超过10分钟
	 * @param userId 账号
	 * @return
	 */
	public boolean permitCrearPasswd(String userId){
		boolean permit = false;
		Timestamp sysTime = new Timestamp(new Date().getTime());
		ResetPasswd resetPasswd = resetPasswdService.findResetPasswdByUserId(userId);
		if(resetPasswd==null){
			return true;
		}else{
			Timestamp lastUpdateTime = resetPasswd.getUpdate_time();
			long time = sysTime.getTime()-lastUpdateTime.getTime();
			if(time>28800000){
				permit = true;
			}
			return permit;
		}	
	}
}
