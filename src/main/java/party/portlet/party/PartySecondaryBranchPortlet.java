package party.portlet.party;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.server.party.PartyMeetingPlanInfo;
import hg.party.server.party.PartyMemberServer;
import hg.party.server.toDoList.EvaluationServer;
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
			"javax.portlet.display-name=组织部查看活动进度(查看支部)",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/party/PartySecondaryMeetingPlanBranch.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartySecondaryBranchPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(PartySecondaryBranchPortlet.class);
	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	@Reference
	private PartyMemberServer partyMemberServer;
	@Reference
	private EvaluationServer evaluationServer;
	
	private int pageSize = 8;//每页条数
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String orgType=SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "orgType").toString();
		String informId = ParamUtil.getString(renderRequest, "informId");//通知id
		informId = HtmlUtil.escape(informId);
		String org_name = ParamUtil.getString(renderRequest, "orgName");//上级名字
		org_name = HtmlUtil.escape(org_name);
		
		HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
		String meeting_id = PortalUtil.getOriginalServletRequest(request).getParameter("inform_id");//通知id
		meeting_id = HtmlUtil.escape(meeting_id);
		String orgName = PortalUtil.getOriginalServletRequest(request).getParameter("orgName");//上级名字
		orgName = HtmlUtil.escape(orgName);
		if("".equals(meeting_id) || null == meeting_id){
			if("".equals(informId) || null == informId){
				informId = "eca28ccf-ad61-4fc6-9084-224037aba15f";
				org_name ="法学院党委";
			}
			meeting_id = informId;
			orgName = org_name;
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
		
		
		if(!"".equals(meeting_id) && null != meeting_id){
				String sql = "select tt.*, us.user_name as check_person_us  from "+ 
							"(SELECT note.attachment AS attachment_n,plan.meeting_id as meeting,plan.id AS plan_id,info.meeting_theme as theme_,org.org_id as org_id_u, "+
							"info.start_time as start_,info.end_time as end_,plan.start_time as start_p,plan.end_time as end_p,orgs.org_name AS org_names,org.org_name AS org_namez,* "+ 
							"FROM (((((hg_party_inform_group_info AS gr "+
							"Inner JOIN hg_party_org_inform_info AS info ON "+
							"info.inform_id = gr.inform_id) "+
							"inner JOIN hg_party_meeting_plan_info AS plan ON gr.inform_id = plan.inform_id AND gr.pub_org_id = plan.organization_id) "+
							"LEFT JOIN hg_party_meeting_notes_info AS note ON plan.meeting_id = note.meeting_id) "+
							"LEFT JOIN hg_party_org AS org ON org.org_id = gr.pub_org_id) "+
							"LEFT JOIN hg_users_info AS usr ON usr.user_id = plan.auditor) "+
							"LEFT JOIN hg_party_org AS orgs ON info.org_type = orgs.org_id "+
							"where info.parent = ? "+
							"AND orgs.org_name = ? "+
							"and org.historic is false "+
							"and orgs.historic is false "+
							"ORDER BY read_status, plan.start_time DESC ) as tt "+
							"LEFT OUTER JOIN hg_users_info as us "+
				            "on tt.check_person_org=us.user_id ";
				Map<String, Object> postgresqlResults = partyMeetingPlanInfo.postGresqlFind(pageNo, pageSize, sql, meeting_id, orgName);
				list = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
				
				String meeting_Id = "";
				String average = "";
				if(null != list){
					for(int i = 0; i<list.size(); i++){
						meeting_Id = (String)list.get(i).get("meeting");
						if(null != meeting_Id){
							List<Map<String, Object>> evalu = evaluationServer.evaluationList(meeting_Id);
							if(null != evalu && evalu.size() > 0){
								Map<String, Object> map = evaluationServer.meetingRating(meeting_Id);
								if(null != map){
									average = String.valueOf(map.get("ave"));
									if(list.get(i).containsKey("evaluation_score")){
										list.get(i).put("evaluation_score", average);
									}
								}
							}
						}
					}
				}
				totalPage = (int) postgresqlResults.get("totalPage");//获取总页码
			
		}
		logger.info("list====="+list);
		renderRequest.setAttribute("informId", meeting_id); //通知id
		renderRequest.setAttribute("orgName", orgName); //上级名字
		renderRequest.setAttribute("list", list);
		renderRequest.setAttribute("pageNo", pageNo);
		renderRequest.setAttribute("totalPage", totalPage);
		renderRequest.setAttribute("orgType", orgType);
		
		super.doView(renderRequest, renderResponse);
	}

}

