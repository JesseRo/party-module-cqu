package hg.party.command.userMeetingCount;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSONArray;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.entity.userMeetingCount.UserMeetingCount;
import hg.party.server.party.PartyMeetingPlanInfoService;
import hg.party.unity.ExcelUtil;
import party.constants.PartyPortletKeys;

@Component(
immediate = true,
property = {
	"javax.portlet.name="+ PartyPortletKeys.UserMeetingCount,
	"mvc.command.name=/hg/ExclMeetingCount"
},
  service = MVCResourceCommand.class
)
public class ExclMeetingCount implements MVCResourceCommand{
	@Reference
	private PartyMeetingPlanInfoService partyMeetingPlanInfoService;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String sessionId = resourceRequest.getRequestedSessionId();
		String orgType = (String) SessionManager.getAttribute(sessionId, "orgType");//获取用户角色类型
		String orgId = (String) SessionManager.getAttribute(sessionId, "department");//获取组织id
		orgType = HtmlUtil.escape(orgType);
		orgId = HtmlUtil.escape(orgId);
//		String startTime = ParamUtil.getString(resourceRequest, "startTime");//开展时间
//		startTime = HtmlUtil.escape(startTime);
//		String endTime = ParamUtil.getString(resourceRequest, "endTime");//开展时间
//		endTime = HtmlUtil.escape(endTime);
		String seconedId = ParamUtil.getString(resourceRequest, "seconedId");//二级党委
		seconedId = HtmlUtil.escape(seconedId);
		String branchId = ParamUtil.getString(resourceRequest, "branchId");//二级党委
		branchId = HtmlUtil.escape(branchId);
		String userName = ParamUtil.getString(resourceRequest, "userName");//人员姓名
		userName = HtmlUtil.escape(userName);
		try {
			List<Map<String, Object>> list= partyMeetingPlanInfoService.userMeetingCount(userName,seconedId,branchId,orgType,orgId);
			
			JSONArray jsonArray=new JSONArray();
			for (Map<String, Object> map : list) {
				UserMeetingCount us = new UserMeetingCount();
				us.setOrg_names(map.get("org_names")+"");
				us.setOrg_name(map.get("org_nam")+"");
				us.setUser_name(map.get("user_name")+"");
				us.setMeeting_type(map.get("meeting_type")+"");
				us.setMeeting_theme(map.get("meeting_theme")+"");
				us.setMeeting_theme_secondary(map.get("meeting_theme_secondary")+"");
				us.setStart_time(map.get("start_time")+"");
				us.setPlace(map.get("place")+"");
				us.setHost(map.get("host")+"");
				us.setContact(map.get("contact")+"");
				us.setContact_phone(map.get("contact_phone")+"");
				us.setTask_status(status(map.get("task_status")+""));
				us.setAuditor(map.get("auditor")+"");
			    jsonArray.add(us);
			   
			}
			
			 Map<String,String> headMap = new LinkedHashMap<String,String>();
			 	 headMap.put("org_names","二级党组织");
				 headMap.put("org_name","党支部");
				 headMap.put("user_name","姓名");
				 headMap.put("meeting_type","会议类型");
				 headMap.put("meeting_theme","开展主题");
				 headMap.put("meeting_theme_secondary","党支部主题");
				 headMap.put("start_time","开展时间");
				 headMap.put("place","开展地点");
				 headMap.put("host","主持人");
				 headMap.put("contact","联系人");
				 headMap.put("contact_phone","联系人电话");
				 headMap.put("task_status","任务状态");
				 headMap.put("auditor","审核人");
				 String datePattern="yyyy-MM-dd HH:ss:mm";

				 SXSSFWorkbook workbook= ExcelUtil.exportExcelX("会议统计", headMap, jsonArray, datePattern, 0, null);


				 HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
				 res.addHeader("content-type","application/x-msdownload");
				 res.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("人员会议统计.xlsx", "UTF-8"));
				 OutputStream out1= res.getOutputStream();
				 workbook.write(out1);
				 out1.flush();
				 out1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String status(String str){
		String statu = "";
		if(!"".equals(str) && null != str){
			int aa = Integer.parseInt(str);
			if(aa == 1){
				statu = "已提交";
			}else if (aa == 2) {
				statu = "已撤回";
			}else if (aa == 3) {
				statu = "被驳回";
			}else if (aa == 4) {
				statu = "已通过";
			}else if (aa == 5) {
				statu = "已指派";
			}else if (aa == 6) {
				statu = "未检查";
			}else if (aa == 7) {
				statu = "已检查";
			}
		}else {
			statu = "";
		}
		return statu;
	}

}
