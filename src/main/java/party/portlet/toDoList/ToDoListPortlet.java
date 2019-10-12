package party.portlet.toDoList;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.server.memberMeeting.MemberMeetingServer;
import hg.party.server.toDoList.ToDoListServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月22日下午4:42:17<br>
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
			"javax.portlet.display-name=普通党员-待办事项",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/toDoList/view.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp", 
			
			"javax.portlet.name=" + PartyPortletKeys.ToDoList,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class ToDoListPortlet extends MVCPortlet {

	@Reference
	private MemberMeetingServer memberMeetingServer;
	@Reference
	private ToDoListServer toDoListServer;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String sessionId=renderRequest.getRequestedSessionId();
		
		String nameId = (String) SessionManager.getAttribute(sessionId, "userName");//该党员id唯一，通过登录session获取
		
		nameId = HtmlUtil.escape(nameId);
		
		HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
		//判断该条信息是否为已读
		String meetingId = PortalUtil.getOriginalServletRequest(request).getParameter("meetingId");
		if(null==meetingId || "".equals(meetingId)){
			//会议id为空，说明不是从详细页面跳转过来的
		}else{
			//更新该条信息为已读
			memberMeetingServer.findByMeetingIdAndUserId(nameId, meetingId);
		}
		/*获取党员组织和支部*/
		if (StringUtils.isEmpty(nameId)) {
			nameId = "111111";
		}
		List<Map<String,Object>> toDoLists = toDoListServer.findOrgAndGroup(nameId);
		if(toDoLists.size() == 0 || null == toDoLists){
			
		}else{
			Map<String,Object> map = toDoLists.get(0);
			String sql = generalSql(nameId);
			Map<String,Object> postgre;//查询结果集
			postgre = pagenation(renderRequest,nameId);
			List<Map<String,Object>> lists = (List<Map<String, Object>>) postgre.get("pagelist");
			int pageNo=Integer.parseInt(postgre.get("pageNo").toString());//当前页
			int pages=Integer.parseInt(postgre.get("totalpage").toString());//总页数
			//所属支部
			renderRequest.setAttribute("group", map.get("party"));
			//所属组织
			renderRequest.setAttribute("org", map.get("org_name"));
			//保存处理获取后数据，传到页面
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
//			DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			Date date = new Date();
			String str = "";
			
			for(Map<String,Object> map1:lists){
				
				Map<String,Object> map2 = new HashMap<String,Object>();
				//会议id
				map2.put("meeting_id", map1.get("meeting_id"));
				//会议类型
				map2.put("meeting_type", map1.get("meeting_type"));
				//会议主题
				map2.put("meeting_theme", map1.get("meeting_theme"));
				//会议地点
				map2.put("place", map1.get("place"));
				//会议内容
				map2.put("content", map1.get("content"));
				//会议结束时间
				map2.put("endTime", map1.get("end_time"));
				//会议持续时间
				String time=dateFormat((Date)map1.get("start_time"),(Date)map1.get("end_time"));
				map2.put("time", time);
				//该会议是否已经评论
				map2.put("commentState", map1.get("comments_state"));
				//该用户是否已上传该会议的学习心得
				map2.put("experienceState", map1.get("upload_state"));
				//该会议是否启用评价
				map2.put("enableComment", map1.get("enable_comment"));
				//该会议查看状态
				map2.put("status", map1.get("check_status"));
				
				//判断结束时间是否大于当前时间
				if(date.getTime() > ((Timestamp)map1.get("end_time")).getTime()){
					str = "t";
				}else {
					str = "f";
				}
				map2.put("timp", str);
				list.add(map2);
			}
			
			renderRequest.setAttribute("pageNo", pageNo);
			renderRequest.setAttribute("pages", pages);
			renderRequest.setAttribute("nameId", nameId);
			renderRequest.setAttribute("list", list);
			
		}
		super.doView(renderRequest, renderResponse); 
		
	}
	
	//分页
	public Map<String,Object> pagenation(RenderRequest req,String nameId){
		//当前页
		int pageNo;
		int page=ParamUtil.getInteger(req, "pageNo");
		int totalPage = ParamUtil.getInteger(req, "total_page_");//总页码
		if(page==0){
			pageNo=1;//默认当前页为1
		}else if(page > totalPage){
			pageNo = totalPage;
		}else{
			pageNo=page;
		}
		int pageSize=8;//每页显示条数
		String sql = "SELECT " + 
				"plan.meeting_id,plan.meeting_type,plan.meeting_theme,plan.place,plan. CONTENT," + 
				"plan.start_time,plan.end_time,comm.comments_state,ex.upload_state,inform.enable_comment,mm.check_status " + 
				"FROM " + 
				"(((hg_party_meeting_member_info mm INNER JOIN hg_party_meeting_plan_info plan ON plan.meeting_id = mm.meeting_id) " + 
				"LEFT JOIN hg_party_org_inform_info AS inform ON inform.inform_id = plan.inform_id) " + 
				"LEFT JOIN hg_party_learning_experience AS ex ON ex.participant_id = mm.participant_id AND ex.meeting_id = plan.meeting_id) " + 
				"LEFT JOIN hg_party_comments_info AS comm ON comm.participant_id = mm.participant_id " +
				"AND comm.meeting_id = plan.meeting_id WHERE mm.participant_id ='" + nameId + "' " +
				"ORDER BY mm.check_status LIMIT "+pageSize+" OFFSET "+(pageNo-1)*pageSize+";";
		return  toDoListServer.pagenation(pageSize,pageNo,sql,generalSql(nameId));
	}
	
	public String generalSql(String nameId){
		String sql = "SELECT " + 
					"plan.meeting_id,plan.meeting_type,plan.meeting_theme,plan.place,plan. CONTENT," + 
					"plan.start_time,plan.end_time,comm.comments_state,ex.upload_state,inform.enable_comment,mm.check_status " + 
					"FROM " + 
					"(((hg_party_meeting_member_info mm INNER JOIN hg_party_meeting_plan_info plan ON plan.meeting_id = mm.meeting_id) " + 
					"LEFT JOIN hg_party_org_inform_info AS inform ON inform.inform_id = plan.inform_id) " + 
					"LEFT JOIN hg_party_learning_experience AS ex ON ex.participant_id = mm.participant_id AND ex.meeting_id = plan.meeting_id) " + 
					"LEFT JOIN hg_party_comments_info AS comm ON comm.participant_id = mm.participant_id " +
					"AND comm.meeting_id = plan.meeting_id WHERE mm.participant_id ='" + nameId + "' " +
					"ORDER BY mm.check_status";
		          return sql;
	}
	/*SELECT
	plan.meeting_id,plan.meeting_type,plan.meeting_theme,plan.place,plan. CONTENT,
	plan.start_time,plan.end_time,comm.comments_state,ex.upload_state,inform.enable_comment,mm.check_status
FROM
(((hg_party_meeting_member_info mm INNER JOIN hg_party_meeting_plan_info plan ON plan.meeting_id = mm.meeting_id)
	LEFT JOIN hg_party_org_inform_info AS inform ON inform.inform_id = plan.inform_id)
	LEFT JOIN hg_party_learning_experience AS ex ON ex.participant_id = mm.participant_id AND ex.meeting_id = plan.meeting_id)
LEFT JOIN hg_party_comments_info AS comm ON comm.participant_id = mm.participant_id
AND comm.meeting_id = plan.meeting_id
WHERE
mm.participant_id = '370685197904204818'
ORDER BY
mm.check_status*/
	//党员待办事项中时间格式化
	public String dateFormat(Date startTime,Date endTime){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm");
		String str1=sdf.format(startTime);
		String str3=sdf1.format(startTime);
		String str2=sdf.format(endTime);
		String str4=sdf1.format(endTime);
		String time="";
		if(str1.equals(str2)){
			time += str1+" "+str3+"-"+str4;
		}else{
			time += str1+" "+str3+"-"+str2+" "+str4;
		}
		return time;
	}

}
