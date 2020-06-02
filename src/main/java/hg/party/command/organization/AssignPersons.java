package hg.party.command.organization;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import hg.party.dao.secondCommittee.MeetingPlanDao;
import hg.party.entity.party.MeetingPlan;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.server.organization.AssignedPersonService;
import party.constants.PartyPortletKeys;
import party.portlet.cqu.dao.CheckPersonDao;
import party.portlet.cqu.entity.CheckPerson;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.AssignedPersonPorlet,
			"javax.portlet.name="+ PartyPortletKeys.PartySecondary,
			"javax.portlet.name="+ PartyPortletKeys.PartyMeetingPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
			"mvc.command.name=/hg/getAssignPersons"
	    },
	    service = MVCResourceCommand.class
)
/**
 * 获取指派人员列表
 * PartySecondary
 */
public class AssignPersons implements MVCResourceCommand{
	Logger logger=Logger.getLogger(AssignPersons.class);

	@Reference
	private CheckPersonDao checkPersonDao;

	@Reference
	private MeetingPlanDao meetingPlanDao;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String id=ParamUtil.getString(resourceRequest, "id");
		id = HtmlUtil.escape(id);
		logger.info("获取指派人员command  id  value  "+id);
		PrintWriter printWriter=null;
		String orgId=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		String orgType=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "orgType");
			try {
				printWriter=resourceResponse.getWriter();
				MeetingPlan meetingPlan = meetingPlanDao.getEntityById(Integer.valueOf(id));

				List<Map<String, Object>> list = checkPersonDao.getCampusPerson(meetingPlan.getCampus());
				printWriter.write(JSON.toJSONString(list));
			} catch (Exception e) {
			e.printStackTrace();
			}
		return false;
	}

}

