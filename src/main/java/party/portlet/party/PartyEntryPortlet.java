package party.portlet.party;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.party.PartyMeetingNoteServer;
import hg.party.server.party.PartyMeetingPlanInfoService;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月2日下午6:14:43<br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=指派录入(二级党组织)",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/party/PartyApprovalPlan2.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PartyEntry,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartyEntryPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(PartyEntryPortlet.class);
	@Reference
	private PartyMeetingPlanInfoService partyMeetingPlanInfoService;
	@Reference
	private PartyMeetingNoteServer partyMeetingNoteServer;
	
	private int pageSize = 8;//每页条数
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
//		String informId = ParamUtil.getString(renderRequest, "informId");//通知id
		
		String sessionID=renderRequest.getRequestedSessionId();
		String user_id = (String)SessionManager.getAttribute(sessionID, "userName");//用户id
		if("".equals(user_id) || null == user_id){
			user_id = "222222";
		}
		//获取当前页
		List<Map<String, Object>> list = null;
		int pageNo = 0;
		int page = ParamUtil.getInteger(renderRequest, "pageNo"); //获取当前页
		int totalPage = ParamUtil.getInteger(renderRequest, "total_page_");//总页码
		if(page==0){
			pageNo = 1;//默认当前页为1
		}else if(page > totalPage){
			pageNo = totalPage;
		}else{
			pageNo = page;
		}
		
		
		if(!"".equals(user_id) && null != user_id){
				String sql = "SELECT plan.meeting_id as meeting,plan.start_time AS start_p,* FROM "+
							"(hg_party_meeting_plan_info AS plan "+
							"LEFT JOIN hg_party_meeting_notes_info AS notes "+
							"ON plan.meeting_id=notes.meeting_id) "+
							"LEFT JOIN hg_party_org AS org "+
							"ON plan.organization_id=org.org_id "+
							"WHERE plan.check_person=? "+
							"and org.historic is false "+
							"AND (plan.task_status='5' "+
							"OR plan.task_status='7') "+
							"ORDER BY plan.id desc ";
				Map<String, Object> postgresqlResults = partyMeetingPlanInfoService.postGresqlFind(pageNo, pageSize, sql, user_id);
				list = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
				totalPage = (int) postgresqlResults.get("totalPage");//获取总页码
			
		}
		logger.info("list====="+list);
//		renderRequest.setAttribute("informId", meeting_id); //通知id
		renderRequest.setAttribute("list", list);
		renderRequest.setAttribute("pageNo", pageNo);
		renderRequest.setAttribute("totalPage", totalPage);
		
		super.doView(renderRequest, renderResponse);
	}

}

