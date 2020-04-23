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

	private Gson gson = new Gson();
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		String meetingId = ParamUtil.getString(resourceRequest, "meeting_id");//会议id
		meetingId = HtmlUtil.escape(meetingId);
		String sessionID=resourceRequest.getRequestedSessionId();
		String user_id = (String)SessionManager.getAttribute(sessionID, "user_name");//登录用户
		transactionUtil.startTransaction();
		try {
			if(!"".equals(meetingId) && null != meetingId){
				List<MeetingPlan> meetings = partyMeetingPlanInfoService.meetingId(meetingId);
				MeetingPlan meeting = meetings.get(0);
				meeting.setTask_status("6");
				meeting.setTask_status_org("6");
				meeting.setAuditor(user_id);

				List<String> participants = gson.fromJson(meeting.getParticipant_group(), new TypeToken<List<String>>(){}.getType());
				partyMeetingPlanInfoService.save(meeting);
				for (String m : participants){
					meetingPlanDao.informParty(meetingId, m);
				}
			}
			transactionUtil.commit();
		}catch (Exception e){
			e.printStackTrace();
			transactionUtil.rollback();
		}

		logger.info("通过");
		try {
			  PrintWriter printWriter=resourceResponse.getWriter();
			  printWriter.write(JSON.toJSONString(meetingId));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}


}