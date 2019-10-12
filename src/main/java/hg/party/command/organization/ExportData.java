package hg.party.command.organization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import hg.party.command.exportexcl.ExprotUntil;
import hg.party.dao.organization.GraftDao;
import hg.party.entity.organization.Export;
import hg.party.server.organization.GraftService;
import hg.party.server.partyBranch.PartyBranchService;
import hg.party.unity.ExcelUtil;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.AlreadyPublic,
			"mvc.command.name=/hg/export"
	    },
	      service = MVCResourceCommand.class
)
public class ExportData implements MVCResourceCommand{
	@Reference
	private GraftDao dao;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		 try {
			 String orgId=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
			// List<Map<String, Object>> list=grafservice.findAlreadyPublic(1,"",orgId);
			 List<Map<String, Object>> list=dao.exportPublicExcel(orgId, "1");
			 JSONArray jsonArray=new JSONArray();
			 for (Map<String, Object> map : list) {
				Export d=new Export();
				d.setMeeting_theme(map.get("meeting_type")+"");
				d.setMeeting_type(map.get("meeting_theme")+"");
				d.setRelease_time(ExprotUntil.getDateString(map.get("release_time")+"")); 
				d.setRelease_person(map.get("user_name")+"");
				jsonArray.add(d);
			}
			 Map<String,String> headMap = new LinkedHashMap<String,String>();
			 headMap.put("meeting_type","会议类型");
			 headMap.put("meeting_theme","会议主题");
			 headMap.put("release_time","发布时间");
			 headMap.put("release_person","发布人");
			//保存到本地
			// File f= new File("d:" + File.separator + "test.xls") ;// 声明File对象		
			// OutputStream out=new FileOutputStream(f) ;   // 准备好一个输出的对象  
			 String datePattern="yyyy-MM-dd HH:ss:mm";
			 SXSSFWorkbook workbook= ExcelUtil.exportExcelX("已经发布", headMap, jsonArray, datePattern, 0, null);
			 HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
			 res.addHeader("content-type","application/x-msdownload");
			 res.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("已经发布.xlsx", "UTF-8"));
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
