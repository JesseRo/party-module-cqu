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
import hg.party.server.party.PartyMeetingPlanInfo;
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
			"javax.portlet.display-name=组织部审批",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/party/PartyEntryPlan.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalPlan,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartyApprovalPlanPortlet extends MVCPortlet{
	
	Logger logger = Logger.getLogger(PartyApprovalPlanPortlet.class);
	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	
	private int pageSize = 8;//每页条数
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
//		String meeting_id = ParamUtil.getString(renderRequest, "meeting_id");//会议id
		String orgId = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
		
		//获取当前页
		List<Map<String, Object>> list = null;
		int totalPage = 0;
		int pageNo;
		int page = ParamUtil.getInteger(renderRequest, "pageNo");
		totalPage = ParamUtil.getInteger(renderRequest, "total_page_");//总页码
		if(page==0){
			pageNo = 1;//默认当前页为1
		}else if(page > totalPage){
			pageNo = totalPage;
		}else{
			pageNo = page;
		}
		
			
//		String sql = "SELECT plan.meeting_id as meeting,plan.start_time as start_p,plan.end_time as end_p,* from "+
//					"((hg_party_meeting_plan_info as plan "+
//					"LEFT JOIN hg_party_meeting_notes_info as note on "+
//					"plan.meeting_id = note.meeting_id) LEFT JOIN hg_party_org as org on "+
//					"org.org_id = plan.organization_id) LEFT JOIN hg_users_info as usr on "+
//					"usr.user_id = auditor "+
//					"WHERE org.org_type='secondary' "+
//					"and org.historic is false "+
//					"AND (plan.task_status='1' "+
//					"OR plan.task_status='3' "+
//					"OR plan.task_status='4' "+
//					"OR plan.task_status='5' "+
//					"OR plan.task_status='6') "+
//					"ORDER BY plan.id DESC ";
		
		String sql = "SELECT plan.meeting_id as meeting,plan.start_time as start_p,plan.end_time as end_p,plan.task_status as task_st,* from "+
					"((hg_party_meeting_plan_info as plan  "+
					"LEFT JOIN hg_party_meeting_notes_info as note on  "+
					"plan.meeting_id = note.meeting_id "+
					"LEFT JOIN hg_party_org_inform_info as orginfo "+
					"ON plan.inform_id=orginfo.inform_id "+
					") LEFT JOIN hg_party_org as org on  "+
					"org.org_id = plan.organization_id) LEFT JOIN hg_users_info as usr on  "+
					"usr.user_id = auditor  "+
					"WHERE org.org_type='secondary'  "+
					"and org.historic is false  "+
					"AND (plan.task_status='1'  "+
					"OR plan.task_status='3'  "+
					"OR plan.task_status='4'  "+
					"OR plan.task_status='5'  "+
					"OR plan.task_status='6') "+
					"AND orginfo.org_type= '"+orgId+"'"+
					"ORDER BY plan.id DESC ";	
						
		Map<String, Object> postgresqlResults = partyMeetingPlanInfo.postGresqlFind(pageNo, pageSize, sql);
		list = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
		totalPage = (int) postgresqlResults.get("totalPage");//获取总页码
		
		renderRequest.setAttribute("list", list);
		renderRequest.setAttribute("pageNo", pageNo);
		renderRequest.setAttribute("totalPage", totalPage);
		
		super.doView(renderRequest, renderResponse);
	}

}

