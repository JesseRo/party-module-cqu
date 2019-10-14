package party.portlet.partyBranch;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.server.partyBranch.PartyBranchService;
import hg.party.server.secondCommittee.SecondCommitteeService;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=党支部-上报计划列表",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/partyBranch/meetings.jsp",
			"javax.portlet.name=" + PartyPortletKeys.BrunchMeetingsPortlet,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class MeetingPortlet extends MVCPortlet{
	@Reference 
	PartyBranchService service;
	@Reference
	SecondCommitteeService secondCommitteeService;
	
	Logger logger = Logger.getLogger(MeetingPortlet.class);
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		try {
			String sessionId=renderRequest.getRequestedSessionId();
			HttpServletRequest servletRequest = PortalUtil.getHttpServletRequest(renderRequest);
			HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(servletRequest);
			String informId = httpServletRequest.getParameter("informId");
			String orgId = SessionManager.getAttribute(sessionId, "department").toString();
			int pageNo = ParamUtil.getInteger(renderRequest, "pageNo");
			int totalPage = ParamUtil.getInteger(renderRequest, "total_page_");//总页码
			if(pageNo > totalPage){
				pageNo = totalPage;
			}
			String meetingType = ParamUtil.getString(renderRequest, "meetingType");
			meetingType = HtmlUtil.escape(meetingType);
			String taskStatus = ParamUtil.getString(renderRequest, "taskStatus");
			taskStatus = HtmlUtil.escape(taskStatus);
			
			List<Map<String, Object>> meetingTypeList = secondCommitteeService.queryAllMeetingTypes("meetingType");
			List<Map<String, Object>> taskStatusList = secondCommitteeService.queryAllTaskStatus(4);
			
			Map<String, Object> listTsak1 = service.queryInformMeetingsByOrgIdAndInformId(orgId, meetingType, taskStatus, informId, pageNo);
			List<Map<String, Object>>  informMeetingList = (List<Map<String, Object>>) listTsak1.get("list");
			
			renderRequest.setAttribute("meetingTypeList", meetingTypeList);
			renderRequest.setAttribute("taskStatusList", taskStatusList);
			renderRequest.setAttribute("list", informMeetingList);
			renderRequest.setAttribute("meetingType", meetingType);
			renderRequest.setAttribute("taskStatus", taskStatus);
			renderRequest.setAttribute("pageNo", listTsak1.get("pageNow"));
			renderRequest.setAttribute("pages", listTsak1.get("totalPage"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.doView(renderRequest, renderResponse);
	}

}
