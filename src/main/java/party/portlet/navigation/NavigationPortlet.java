package party.portlet.navigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;

import dt.session.SessionManager;
import hg.party.server.navigation.NavigationMessageServer;
import hg.party.server.navigation.NavigationPermissionsServer;
import hg.util.ConstantsKey;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月9日上午9:14:38<br>
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
			"com.liferay.portlet.instanceable=false",//设为false，主题直接引入
			"javax.portlet.display-name=左侧导航权限",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/navigation/view.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.Navigation,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class NavigationPortlet extends MVCPortlet {

	private static final String LOCATION="left";
	@Reference
	private NavigationPermissionsServer navigationPermissionsServer;
	@Reference
	private NavigationMessageServer navigationMessageServer;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String sessionId=renderRequest.getRequestedSessionId();
		String role=(String) SessionManager.getAttribute(sessionId, "role");
		role = 	HtmlUtil.escape(role);
		List<Map<String,Object>> lists = null;
		String department = null;
		List<Map<String,Object>> list = null;
		Map<String, List<Map<String, Object>>> groups = null;
		if("".equals(role) || role==null){
			
		}else{
			//组织id
//			String orgId=(String) SessionManager.getAttribute(sessionId, "department");
//			department=(String) navigationPermissionsServer.findOrgType(orgId).get("org_type");

			if((ConstantsKey.SECOND_PARTY).equals(role)){
				department = ConstantsKey.SECOND_PARTY;
			}else if("党支部".equals(role)){
				department = "党支部";
			}else if("组织部".equals(role)){
				department = "组织部";
			}else if("普通党员".equals(role) || "其他".equals(role)){//所有一级导航
				department = "null";			}
			else{
				department = ConstantsKey.ORG_PROPAGANDA;
			}
			lists=navigationPermissionsServer.findNavigationByRoleAndDepartment(department, role,LOCATION);
			//获取二级导航
			list=navigationPermissionsServer.findNavigationSecondaryPage(department, role);
			List<String> parentIds = new ArrayList<String>();
			for(Map<String,Object> map:list){
				parentIds.add((String) map.get("parent_id"));
			}
			groups = list.stream().collect(Collectors.groupingBy(p->(String)p.get("parent_id")));
			
			for(Map<String, Object> m : lists){
				if(!groups.containsKey(m.get("navigation_id"))){
					groups.put((String) m.get("navigation_id"), Collections.EMPTY_LIST);
				}
			}
			//导航消息提醒
			String sql = "";//sql语句
			String nav = "";//导航组件标识
			String message = "";//返回值
			String messageCount = "";//待办条数
			for (Map<String, Object> map: lists) {
				if(StringUtils.isEmpty(map.get("navigation_message"))){
					message = "f";
				}else {
					nav = (String) map.get("navigation_message");
					sql = NavMessSql(nav,renderRequest);
					message = navigationMessageServer.NavigationPermissions(sql).get("wait");
					messageCount=navigationMessageServer.NavigationPermissions(sql).get("wait_count");
				}
				map.put("navigation_message", message);
				map.put("messageCount", messageCount);
			}
			
			
		}
		renderRequest.setAttribute("lists", lists);
		renderRequest.setAttribute("groups", groups);
		renderRequest.setAttribute("role", role);
		renderRequest.setAttribute("user_name", SessionManager.getAttribute(sessionId, "user_name"));
		renderRequest.setAttribute("firstLogin", SessionManager.getAttribute(sessionId, "firstLogin"));
		SessionManager.setAttribute(sessionId, "firstLogin",true);
		int n=0;
		if (!StringUtils.isEmpty(SessionManager.getAttribute(sessionId, "loginCount"))) {
             n=(int)SessionManager.getAttribute(sessionId, "loginCount");
		     renderRequest.setAttribute("login_Count", n);
		     n=n+1;
		     SessionManager.setAttribute(sessionId, "loginCount", n);
		}		
		super.doView(renderRequest, renderResponse);
		
	}
	
	public String NavMessSql(String nav,RenderRequest renderRequest){
		String sql = "";//sql语句
		String sessionId=renderRequest.getRequestedSessionId();
		String department=(String) SessionManager.getAttribute(sessionId, "department");//组织id
		String user_id = (String)SessionManager.getAttribute(sessionId, "userName");//用户id
			if(nav.equals("组织部审批")){
				sql = "SELECT plan.meeting_id as meeting,plan.start_time as start_p,plan.end_time as end_p,* from "+
						"((hg_party_meeting_plan_info as plan "+
						"LEFT JOIN hg_party_meeting_notes_info as note on "+
						"plan.meeting_id = note.meeting_id) LEFT JOIN hg_party_org as org on "+
						"org.org_id = plan.organization_id) LEFT JOIN hg_users_info as usr on "+
						"usr.user_id = auditor "+
						"WHERE org.org_type='secondary' "+
						"and org.historic is false "+
						"AND plan.task_status='1' "+
						"ORDER BY plan.id ";
			}else if(nav.equals("二级党组织审批")){
				sql = "SELECT plan.meeting_id as meeting,plan.start_time as start_p,plan.end_time as end_p,* from "+
						"((hg_party_meeting_plan_info as plan "+
						"LEFT JOIN hg_party_meeting_notes_info as note on "+
						"plan.meeting_id = note.meeting_id) LEFT JOIN hg_party_org as org on "+
						"org.org_id = plan.organization_id) LEFT JOIN hg_users_info as usr on "+
						"usr.user_id = auditor "+
						"WHERE org.org_type='branch' "+
						"and org.historic is false "+
						"AND plan.task_status='1' "+
						"and org.org_parent='"+department+"' "+
						"ORDER BY plan.id ";
			}else if(nav.equals("组织部发文审批")){
				sql = "SELECT * FROM hg_content_management_info where approve_state=1 ORDER BY publish_time DESC";
			}else if(nav.equals("二级党组织发文审批")){
				sql = "select * from hg_content_management_info AS cont "+
					"LEFT JOIN hg_party_org AS org "+
					"ON cont.content_user_id=org.org_id "+
					"WHERE org.org_parent='"+department+"' "+
					"and cont.approve_state = 0 "+
					"AND org.historic is false "+
					"ORDER BY publish_time DESC ";
				
			}else if(nav.equals("二级党组织待办")){
				sql = "SELECT * FROM "+
					"(hg_party_org_inform_info AS i "+
					"LEFT JOIN PUBLIC .hg_party_inform_group_info AS G ON G .inform_id = i.inform_id "+
					"AND G .pub_org_id = '"+department+"' "+
					"AND has_resend IS NOT TRUE) "+
					"LEFT JOIN PUBLIC .hg_party_meeting_plan_info AS M ON i.inform_id = M .inform_id "+
					"AND G .pub_org_id = M .organization_id "+
					"WHERE "+
					"(G .pub_org_id = '"+department+"' AND has_resend IS NOT TRUE "+
					"OR i.org_type = '"+department+"') "+
					"AND (i.public_status = '1' or i.public_status = '2') and g.read_status='未读' ";
			}else if(nav.equals("党支部待办")){
				sql = "SELECT * FROM "+
						"hg_party_org_inform_info AS i "+
						"INNER JOIN hg_party_inform_group_info AS g ON i.inform_id = g.inform_id "+
						"LEFT JOIN hg_party_meeting_plan_info AS m ON g.inform_id = m.inform_id AND g.pub_org_id = m.organization_id "+
						"WHERE "+
						"g.pub_org_id = '"+department+"' "+
						"AND (i.public_status = '2' OR i.public_status = '1') "+
						"and g.read_status='未读' ";
			}else if(nav.equals("抽查")){
				sql = "SELECT plan.meeting_id as meeting,plan.start_time AS start_p,* FROM "+
						"(hg_party_meeting_plan_info AS plan "+
						"LEFT JOIN hg_party_meeting_notes_info AS notes "+
						"ON plan.meeting_id=notes.meeting_id) "+
						"LEFT JOIN hg_party_org AS org "+
						"ON plan.organization_id=org.org_id "+
						"WHERE plan.check_person_org='"+user_id+"' "+
						"AND plan.task_status_org='5' "+
						"and org.historic is false "+
						"ORDER BY plan.id ";
			}else if(nav.equals("检查")){
				sql = "SELECT plan.meeting_id as meeting,plan.start_time AS start_p,* FROM "+
						"(hg_party_meeting_plan_info AS plan "+
						"LEFT JOIN hg_party_meeting_notes_info AS notes "+
						"ON plan.meeting_id=notes.meeting_id) "+
						"LEFT JOIN hg_party_org AS org "+
						"ON plan.organization_id=org.org_id "+
						"WHERE plan.check_person='"+user_id+"' "+
						"AND plan.task_status='5' "+
						"and org.historic is false "+
						"ORDER BY plan.id ";
			}
		return sql;
	}
}
