package hg.party.command.exportexcl;

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
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.partyBranch.PartyBranchDao;
import hg.party.entity.ExportExcelEntity.BranchTaskExportExcelEntity;
import hg.party.entity.ExportExcelEntity.SecondExportExecl;
import hg.party.unity.ExcelUtil;
import party.constants.PartyPortletKeys;

@Component(
immediate = true,
property = {
	"javax.portlet.name="+ PartyPortletKeys.TaskPortlet,
	"mvc.command.name=/hg/exportbranchToDoList"
},
  service = MVCResourceCommand.class
)
public class BranchTaskExportExecl implements MVCResourceCommand{
@Reference
private PartyBranchDao dao;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		try {
			 String sessionId =resourceRequest.getRequestedSessionId();
			 String orgId = SessionManager.getAttribute(sessionId, "department").toString();
			 List<Map<String, Object>> list=dao.exportBranchTask(orgId, "", "", 0);
			 JSONArray jsonArray=new JSONArray();
			 for ( Map<String, Object> map : list) {
				BranchTaskExportExcelEntity s=new BranchTaskExportExcelEntity();
				String Read_status=StringUtils.isEmpty(map.get("read_status")) ? "未读" : map.get("read_status")+"";
				s.setRead_status(Read_status);
				s.setImeetingtheme(map.get("imeetingtheme")+"");
				s.setImeetingtype(map.get("imeetingtype")+"");		
				s.setRelease_time(ExprotUntil.getDateString(map.get("release_time")+"")); 
				String task=StringUtils.isEmpty(map.get("task_status")) ? "" : map.get("task_status")+"";
				s.setTask_status(ExprotUntil.getTaskState(task));
				s.setUpload_meeting(StringUtils.isEmpty(map.get("check_status")) ? "" : map.get("check_status")+"");
				s.setRemark(StringUtils.isEmpty(map.get("mremark")) ? "" : map.get("mremark")+"");
				s.setOperation(ExprotUntil.getOperation(task));
				jsonArray.add(s);
			}
			 Map<String,String> headMap = new LinkedHashMap<String,String>();
			 headMap.put("read_status","消息状态");
			 headMap.put("imeetingtype","会议类型");
			 headMap.put("imeetingtheme","会议主题");
			 headMap.put("release_time","发布时间");
			 headMap.put("task_status","任务状态");
			 headMap.put("operation","操作");
			// headMap.put("task_status","已读回执");
			 headMap.put("upload_meeting","上传会议记录");
			 headMap.put("mremark","备注");
			 
			 String datePattern="yyyy-MM-dd HH:ss:mm";
			 SXSSFWorkbook workbook= ExcelUtil.exportExcelX("二级党组织带办", headMap, jsonArray, datePattern, 0, null);
			 HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
			 res.addHeader("content-type","application/x-msdownload");
			 res.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("党支部待办.xlsx", "UTF-8"));
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
