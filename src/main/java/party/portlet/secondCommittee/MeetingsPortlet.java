package party.portlet.secondCommittee;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.server.secondCommittee.SecondCommitteeService;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Mingxiang Duan<br>
 * 创建日期： 2017年12月26日上午10:54:05<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */


@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=二级党委--拟定计划列表",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/secondCommittee/meetings.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.SeocndCommitteeMeetings,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class MeetingsPortlet extends MVCPortlet {
	@Reference
	SecondCommitteeService secondCommitteeService;
	Logger logger = Logger.getLogger(MeetingsPortlet.class);


	
	
	@Override
	@Transactional
	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		try {
			String sessionId=request.getRequestedSessionId();
			String orgId = SessionManager.getAttribute(sessionId, "department").toString();
			HttpServletRequest servletRequest = PortalUtil.getHttpServletRequest(request);
			HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(servletRequest);
			String informId = httpServletRequest.getParameter("informId");
			String meetingType = ParamUtil.getString(request, "meetingType");
			meetingType = HtmlUtil.escape(meetingType);
			String taskStatus = ParamUtil.getString(request, "taskStatus");
			taskStatus = HtmlUtil.escape(taskStatus);
			
			if(!StringUtils.isEmpty(informId) && !StringUtils.isEmpty(orgId) ){
				String informStatus = (String) secondCommitteeService.queryInformByInformorgId(informId, orgId).get(0).get("read_status");
				logger.info("informStatus   is " + informStatus );
				
				if( "未读".equals( informStatus )){
					String nextStatus ="已查看";
					secondCommitteeService.updateInformStatus(informId, orgId, nextStatus);
				}
			}
			
			List<Map<String, Object>> meetingTypeList = secondCommitteeService.queryAllMeetingTypes("meetingType");
			List<Map<String, Object>> taskStatusList = secondCommitteeService.queryAllTaskStatus(4);
			logger.info("meetingTypeList is  " + meetingTypeList );
			logger.info("taskStatusList is  " + taskStatusList );
			Map<Long, Object> taskStatuses = new HashMap<>();
			for(Map<String, Object> status: taskStatusList){
				taskStatuses.put(Long.valueOf((String)status.get("resources_key")), status.get("resources_value"));
			}
			String type = ParamUtil.getString(request, "mettingType");
			String status = ParamUtil.getString(request, "taskstatus");
			
			int pageNo = ParamUtil.getInteger(request, "pageNo");
			int totalPage = ParamUtil.getInteger(request, "total_page_");//总页码
			if(pageNo <= 0){
				pageNo = 1;//默认当前页为1
			}else if(pageNo > totalPage){
				pageNo = totalPage;
			}
			
			Map<String, Object>  informMeetingList = secondCommitteeService.queryInformMeetingsByInformId(orgId, meetingType, taskStatus, informId, pageNo);
			logger.info("informMeetingList :" + informMeetingList);
			request.setAttribute("type", type);
			request.setAttribute("status", status);
			request.setAttribute("orgId", orgId );
			request.setAttribute("meetingTypeList", meetingTypeList);
			request.setAttribute("taskStatusList", taskStatusList);
			request.setAttribute("statusMap", taskStatuses);
			request.setAttribute("meetingType", meetingType);
			request.setAttribute("taskStatus", taskStatus);
			request.setAttribute("pageNo", informMeetingList.get("pageNow"));
			request.setAttribute("pages", informMeetingList.get("totalPage"));
//			request.setAttribute("informList", informList);
//			request.setAttribute("meetingList", meetingList); 
			request.setAttribute("informMeetingList", informMeetingList.get("list"));
		
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		super.doView(request, response);
	}
}
