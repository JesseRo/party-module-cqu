package hg.party.command.party;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import hg.util.TransactionUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.party.MeetingNote;
import hg.party.entity.party.MeetingPlan;
import hg.party.server.party.PartyAssignServer;
import hg.party.server.party.PartyMeetingNoteServer;
import hg.party.server.party.PartyMeetingPlanInfoService;
import party.constants.PartyPortletKeys;
import party.portlet.cqu.dao.CheckPersonDao;

/**
 * 录入提交command(二级党组织)
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartyMeetingPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartyEntry,
			"javax.portlet.name=" + PartyPortletKeys.PartyMeetingPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartyEntryOrg,
			"mvc.command.name=/PartyWriteActionCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyWriteActionCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(PartyWriteActionCommand.class);
	@Reference
	private PartyMeetingNoteServer partyMeetingNoteServer;
	@Reference
	private PartyMeetingPlanInfoService partyMeetingPlanInfoService;
	@Reference
	private PartyAssignServer partyAssignServer;
	@Reference
	private CheckPersonDao checkPersonDao;

	@Reference
	private TransactionUtil transactionUtil;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String sessionID=resourceRequest.getRequestedSessionId();
		String user_name = (String)SessionManager.getAttribute(sessionID, "user_name");//获取用户名
		
		String meeting_id = ParamUtil.getString(resourceRequest, "meeting_id2");//会议id
		String remarks = ParamUtil.getString(resourceRequest, "should_"); //备注
		String meeting_state = ParamUtil.getString(resourceRequest, "text"); //抽选结果
		String image = ParamUtil.getString(resourceRequest, "image");//图片名字
		meeting_id = HtmlUtil.escape(meeting_id);
		remarks = HtmlUtil.escape(remarks);
		meeting_state = HtmlUtil.escape(meeting_state);
		image = HtmlUtil.escape(image);
		transactionUtil.startTransaction();
		try {
			if(!"".equals(meeting_id)){
				List<MeetingPlan> meetingPlan = partyMeetingPlanInfoService.meetingId(meeting_id);
				MeetingPlan mPlan = meetingPlan.get(0);
				mPlan.setTask_status("7"); 
				partyMeetingPlanInfoService.saveOrUpdate(mPlan);
				
				List<MeetingNote> meetingNote = partyMeetingNoteServer.meetingNote(meeting_id);
				if(meetingNote.size() == 0){
					MeetingNote meetingN = new MeetingNote();
					meetingN.setMeeting_id(meeting_id);
					meetingN.setMeeting_state(meeting_state);
					meetingN.setRemarks(remarks);
					meetingN.setImage(image);
					partyMeetingNoteServer.saveOrUpdate(meetingN);
				}else{
					MeetingNote meetingN = meetingNote.get(0);
					meetingN.setMeeting_state(meeting_state);
					meetingN.setRemarks(remarks);
					meetingN.setImage(image);
					partyMeetingNoteServer.saveOrUpdate(meetingN);
				}

				checkPersonDao.addCount(mPlan.getCheck_person());
			}
			transactionUtil.commit();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("meeting_id", meeting_id);
		
		   	PrintWriter printWriter=resourceResponse.getWriter();
		   	printWriter.write(JSON.toJSONString(meeting_id));
		} catch (Exception e) {
			e.printStackTrace();
			transactionUtil.rollback();
		}
		
		return false;
	}


}