package hg.party.command.party;


import java.io.PrintWriter;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.party.MeetingPlan;
import hg.party.server.party.PartyMeetingPlanInfo;
import party.constants.PartyPortletKeys;
/**
 * 审批计划驳回command(二级党委)
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalBranch,
			"mvc.command.name=/PartyRejectedCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyRejectedCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(PartyRejectedCommand.class);
	
	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		String meeting_id = ParamUtil.getString(resourceRequest, "meeting_id2");//会议id
		String remark = ParamUtil.getString(resourceRequest, "should_");//驳回备注
		meeting_id = HtmlUtil.escape(meeting_id);
		remark = HtmlUtil.escape(remark);
		String sessionID=resourceRequest.getRequestedSessionId();
		String user_id = (String)SessionManager.getAttribute(sessionID, "user_name");//登录用户
		
		if(!"".equals(meeting_id) && null != meeting_id){
			List<MeetingPlan> meetingid = partyMeetingPlanInfo.meetingId(meeting_id);
			MeetingPlan meeting = meetingid.get(0);
			meeting.setTask_status("3");
			meeting.setAuditor(user_id);
			meeting.setRemark(remark);
			partyMeetingPlanInfo.saveOrUpdate(meeting);
		}
		
		logger.info("被驳回");
		try {
			  PrintWriter printWriter=resourceResponse.getWriter();
			  printWriter.write(JSON.toJSONString(meeting_id));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}


}