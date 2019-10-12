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
 * 审批计划通过command(组织部)
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalBranch,
			"mvc.command.name=/PartyOrganizationsPassCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyOrganizationsPassCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(PartyOrganizationsPassCommand.class);
	
	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
	try {
		String meeting_id = ParamUtil.getString(resourceRequest, "meeting_id");//会议id
		meeting_id = HtmlUtil.escape(meeting_id);
		String sessionID=resourceRequest.getRequestedSessionId();
		String user_id = (String)SessionManager.getAttribute(sessionID, "user_name");//登录用户
		
		if(!"".equals(meeting_id) && null != meeting_id){
			List<MeetingPlan> meetingid = partyMeetingPlanInfo.meetingId(meeting_id);
			MeetingPlan meeting = meetingid.get(0);
			meeting.setTask_status("4");
			meeting.setTask_status_org("6");
			meeting.setAuditor(user_id);
			partyMeetingPlanInfo.saveOrUpdate(meeting);
		}
		
		logger.info("通过");
	
			  PrintWriter printWriter=resourceResponse.getWriter();
			  printWriter.write(JSON.toJSONString(meeting_id));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}


}