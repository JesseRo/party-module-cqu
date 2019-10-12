package hg.party.command.party;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.And;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.entity.party.MeetingPlan;
import hg.party.server.party.PartyMeetingPlanInfo;
import party.constants.PartyPortletKeys;
/**
 * 分配command 提交
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartyMeetingPlan,
			"mvc.command.name=/PartyPartActionCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyPartActionCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(PartyPartActionCommand.class);
	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		String orgName = ParamUtil.getString(resourceRequest, "orgName");//书记名字或id
		String meeting_id = ParamUtil.getString(resourceRequest, "meeting_id");//会议id
		orgName = HtmlUtil.escape(orgName);
		meeting_id = HtmlUtil.escape(meeting_id);
		
		if(!"".equals(meeting_id) && null != meeting_id){
			List<MeetingPlan> meetingPlan = partyMeetingPlanInfo.meetingId(meeting_id);
			
			if(!"".equals(orgName) && meetingPlan.size()>0){
				MeetingPlan mPlan = meetingPlan.get(0);
				mPlan.setAuditor(orgName);
				mPlan.setTask_status("待审批");
				partyMeetingPlanInfo.saveOrUpdate(mPlan);
			}
		}
		logger.info("orgName=="+orgName);
		logger.info("meeting_id=="+meeting_id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("meeting_id", meeting_id);
		try {
			  PrintWriter printWriter=resourceResponse.getWriter();
			  printWriter.write(JSON.toJSONString(map));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}


}