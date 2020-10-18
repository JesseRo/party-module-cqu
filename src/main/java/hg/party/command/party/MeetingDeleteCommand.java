package hg.party.command.party;


import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.dao.secondCommittee.MeetingPlanDao;
import hg.party.entity.party.MeetingPlan;
import hg.party.server.party.PartyMeetingPlanInfoService;
import hg.util.TransactionUtil;
import hg.util.result.Result;
import hg.util.result.ResultUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 审批计划通过command(二级党委)
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalBranch,
			"javax.portlet.name=" + PartyPortletKeys.PartyApproval,
			"javax.portlet.name=" + PartyPortletKeys.SeocndCommitteeToDoList,
			"mvc.command.name=/meeting/delete"
	    },
	    service = MVCResourceCommand.class
)
public class MeetingDeleteCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(MeetingDeleteCommand.class);

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
		meetingPlanDao.deleteByMeetingId(meetingId);
		PrintWriter printWriter= null;
		try {
			printWriter = resourceResponse.getWriter();
			printWriter.write(JSON.toJSONString(ResultUtil.success(null)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

}