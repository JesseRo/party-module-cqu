package hg.party.command.party;


import java.io.PrintWriter;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hg.party.dao.secondCommittee.MeetingPlanDao;
import hg.util.TransactionUtil;
import hg.util.result.Result;
import hg.util.result.ResultUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.party.MeetingPlan;
import hg.party.server.party.PartyMeetingPlanInfoService;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
/**
 * 审批计划通过command(二级党委)
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalBranch,
			"javax.portlet.name=" + PartyPortletKeys.PartyApproval,
			"mvc.command.name=/PartyPassCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyPassCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(PartyPassCommand.class);

	@Reference
	private PartyMeetingPlanInfoService partyMeetingPlanInfoService;
	@Reference
	private MeetingPlanDao meetingPlanDao;

	@Reference
	TransactionUtil transactionUtil;

	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		String meetingId = ParamUtil.getString(resourceRequest, "meetingId");//会议id
		meetingId = HtmlUtil.escape(meetingId);
		String sessionID=resourceRequest.getRequestedSessionId();
		String user_id = (String)SessionManager.getAttribute(sessionID, "user_name");//登录用户
		transactionUtil.startTransaction();
		try {
			PrintWriter printWriter=resourceResponse.getWriter();
			if(!"".equals(meetingId) && null != meetingId){
				List<MeetingPlan> meetings = partyMeetingPlanInfoService.meetingId(meetingId);
				MeetingPlan meeting = meetings.get(0);
				meeting.setTask_status("6");
				meeting.setTask_status_org("6");
				meeting.setAuditor(user_id);
				int ret = partyMeetingPlanInfoService.save(meeting);
				if(ret > 0){
					transactionUtil.commit();
					logger.info("通过");
					Result msgResult = null;
					if(meeting.getAutoPhoneMsg() > 0){
						msgResult = partyMeetingPlanInfoService.sendPhoneNoticeMsg(meetingId);
					}
					printWriter.write(JSON.toJSONString(ResultUtil.success(msgResult)));
				}else{
					transactionUtil.rollback();
					printWriter.write(JSON.toJSONString(ResultUtil.fail("操作失败。")));
				}

			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("参数meeting_id不能为空。")));
			}

		}catch (Exception e){
			e.printStackTrace();
			transactionUtil.rollback();
		}
		return false;
	}


}