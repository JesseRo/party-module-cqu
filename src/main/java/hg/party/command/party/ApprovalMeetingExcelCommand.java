package hg.party.command.party;

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
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.entity.party.ApprovalMeetingExecl;
import hg.party.server.party.PartyMeetingPlanInfo;
import hg.party.unity.ExcelUtil;
import hg.util.ConstantsKey;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： excel导出<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年3月8日下午3:47:50<br>
 * 版本号　 ： v1.0.0<br>
 * 修改内容： <br>
 */
@Component(
immediate = true,
property = {
	"javax.portlet.name="+ PartyPortletKeys.PartyApprovalPlan,
	"javax.portlet.name="+ PartyPortletKeys.PartyApprovalBranch,
	"mvc.command.name=/ApprovalMeetingExcelCommand"
},
  service = MVCResourceCommand.class
)

public class ApprovalMeetingExcelCommand implements MVCResourceCommand{
	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
//		HttpServletRequest request = PortalUtil.getHttpServletRequest(resourceRequest);
//		String perm = PortalUtil.getOriginalServletRequest(request).getParameter("perm");//判断是组织部还是二级党委
		String perm = "";
		String sessionId = resourceRequest.getRequestedSessionId();
		String role = (String) SessionManager.getAttribute(sessionId, "role");
		if("组织部".equals(role)){
			perm = "secondary";
		}else if(ConstantsKey.SECOND_PARTY.equals(role)){
			perm = "branch";
		}
		List<Map<String, Object>> lists = partyMeetingPlanInfo.approvalExcel(perm);
		try {
			JSONArray jsonArray=new JSONArray();
			for(Map<String, Object> map : lists){
				ApprovalMeetingExecl obj = new ApprovalMeetingExecl();
				obj.setOrg_name_excel(map.get("org_name")+"");
				obj.setMeeting_type_excel(map.get("meeting_type")+"");
				obj.setSubmit_time_excel(map.get("submit_time")+"");
				obj.setMeeting_theme_excel(map.get("meeting_theme")+"");
				obj.setStart_p_excel(map.get("start_p")+"");
				obj.setTotal_time_excel(map.get("total_time")+"");
				obj.setPlace_excel(map.get("place")+"");
				obj.setHost_excel(map.get("host")+"");
				obj.setContact_excel(map.get("contact")+"");
				obj.setContact_phone_excel(map.get("contact_phone")+"");
				if("1".equals(map.get("task_status")+"")){
					obj.setTask_status_excel("已提交");
				}else if("3".equals(map.get("task_status")+"")){
					obj.setTask_status_excel("被驳回");
				}else {
					obj.setTask_status_excel("已通过");
				}
				obj.setAuditor_excel(map.get("auditor")+"");
				jsonArray.add(obj);
			}
			 Map<String,String> headMap = new LinkedHashMap<String,String>();
			 headMap.put("org_name_excel","党组织名称");
			 headMap.put("meeting_type_excel","会议类型");
			 headMap.put("submit_time_excel","发布时间");
			 headMap.put("meeting_theme_excel","开展主题");
			 headMap.put("start_p_excel","开始时间");
			 headMap.put("total_time_excel","开展时长");
			 headMap.put("place_excel","开展地点");
			 headMap.put("host_excel","主持人");
			 headMap.put("contact_excel","联系人");
			 headMap.put("contact_phone_excel","联系人电话");
			 headMap.put("task_status_excel","任务状态");
			 headMap.put("auditor_excel","审核人");
			 
			 String datePattern="yyyy-MM-dd HH:ss:mm";
			 SXSSFWorkbook workbook= ExcelUtil.exportExcelX("会议审批", headMap, jsonArray, datePattern, 0, null);
			 HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
			 res.addHeader("content-type","application/x-msdownload");
			 res.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("会议审批.xlsx", "UTF-8"));
			 OutputStream out1= res.getOutputStream();//文件流输出
			 workbook.write(out1);
			 out1.flush();
			 out1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
