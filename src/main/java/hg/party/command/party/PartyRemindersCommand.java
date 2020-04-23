package hg.party.command.party;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import hg.party.entity.login.User;
import hg.party.entity.party.MeetingPlan;
import hg.party.entity.unity.ResourceProperties;
import hg.party.server.party.PartyMeetingPlanInfoService;
import hg.party.server.party.PartyMemberServer;
import hg.party.server.party.PartyUserServer;
import hg.util.MailSend;
import party.constants.PartyPortletKeys;
/**
 * 催办command确定
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartyMeetingPlan,
			"mvc.command.name=/PartyRemindersCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyRemindersCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(PartyRemindersCommand.class);
	@Reference
	private PartyMeetingPlanInfoService partyMeetingPlanInfoService;
	@Reference
	private PartyMemberServer partyMemberServer;
	@Reference
	private PartyUserServer partyUserServer;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("succee", false);
		try {
			PrintWriter printWriter=resourceResponse.getWriter();
	//		String sessionID=resourceRequest.getRequestedSessionId();
	//		String user_id = (String)SessionManager.getAttribute(sessionID, "userName");//获取登录用户
			
			ResourceProperties resourceProperties = new ResourceProperties();
			Properties properties = resourceProperties.getResourceProperties();//获取配置文件
			String UserMailboxes = properties.getProperty("UserMailboxes");//获取发件人邮箱
			String AuthorizationCode = properties.getProperty("AuthorizationCode");//获取发件人授权码
			
			String meeting_id = ParamUtil.getString(resourceRequest, "meeting_id");//会议id
			String auditor = "";//审核人
			String memberMailbox = "";//审核人邮箱
			String meeting_theme = "";//会议主题
			
			List<String> receiveMailAccountList = null;//群发
			StringBuffer receiveMailAccountBuf = null;//群发
			
			if(!"".equals(meeting_id)){
				List<MeetingPlan> meetingPlan = partyMeetingPlanInfoService.meetingId(meeting_id);
				auditor = meetingPlan.get(0).getAuditor();	
//				List<Member> member_name = partyMemberServer.MemberMailbox(auditor);
				List<User> member_name = partyUserServer.userName(auditor);
				if(Validator.isNull(member_name) || member_name.size() == 0){
					printWriter.write(JSON.toJSONString(map));
					return false;
				}
				memberMailbox = member_name.get(0).getUser_mailbox();
				meeting_theme = meetingPlan.get(0).getMeeting_theme();
				
			}
			String body_ = "请您审核"+meeting_theme;//发送内容
			
			MailSend mail = new MailSend(UserMailboxes, AuthorizationCode, memberMailbox, receiveMailAccountList, receiveMailAccountBuf);
			Boolean mailReturns = mail.sendMail(meeting_theme, body_);
		
			map.put("succee", mailReturns);
			printWriter.write(JSON.toJSONString(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


}