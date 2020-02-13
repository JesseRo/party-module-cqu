package hg.party.command.exportexcl;

import com.alibaba.fastjson.JSONArray;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.party.PartyMeetingPlanInfoDao;
import hg.party.entity.ExportExcelEntity.OrgMeetingConut;
import hg.party.unity.ExcelUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.LeaderMeetingStatistics,
                "mvc.command.name=/hg/leader/MeetingExport"
        },
        service = MVCResourceCommand.class
)
public class LeaderMeeting implements MVCResourceCommand {
    @Reference
    private PartyMeetingPlanInfoDao partyMeetingPlanInfoDao;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws PortletException {
        String startTime = ParamUtil.getString(resourceRequest, "startTime");//开展时间
        startTime = HtmlUtil.escape(startTime);
        String endTime = ParamUtil.getString(resourceRequest, "endTime");//开展时间
        endTime = HtmlUtil.escape(endTime);
        String seconedId = ParamUtil.getString(resourceRequest, "seconedId");//二级党委
        seconedId = HtmlUtil.escape(seconedId);
        String branchId = ParamUtil.getString(resourceRequest, "branchId");//二级党委
        branchId = HtmlUtil.escape(branchId);
        String leader = ParamUtil.getString(resourceRequest, "leader");//
        boolean ifExportAll = ParamUtil.getBoolean(resourceRequest, "ifExportAll");//是否导出所有

        try {
            List<Map<String, Object>> list = partyMeetingPlanInfoDao.leaderMeeting(seconedId, branchId, startTime, endTime, leader);
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(list);
            Map<String, String> headMap = new LinkedHashMap<String, String>();
            if (ifExportAll) {
				headMap.put("org_name", "党组织");
				headMap.put("meeting_type", "会议类型");
				headMap.put("submit_time", "发布时间");
				headMap.put("meeting_theme", "开展主题");
				headMap.put("start_time", "开展时间");
				headMap.put("place", "开展地点");
				headMap.put("host", "主持人");
				headMap.put("contact", "联系人");
				headMap.put("contact_phone", "联系人电话");
				headMap.put("plan_state", "任务状态");
				headMap.put("auditor", "审核人");
				headMap.put("checker", "检查人");
//				 headMap.put("check_person_org_name","抽查人");
//				headMap.put("shoule_persons", "应到人数");
//				headMap.put("actual_persons", "实到人数人");
//				headMap.put("leave_persons", "请假人员");
//				headMap.put("attendance", "出勤率");
//				headMap.put("remark", "备注");
			}else {
				headMap.put("org_name", "党组织");
				headMap.put("meeting_type", "会议类型");
				headMap.put("meeting_theme", "开展主题");
				headMap.put("start_time", "开展时间");
				headMap.put("contact", "联系人");
				headMap.put("leader", "参会领导");
			}
            String datePattern = "yyyy-MM-dd HH:mm";
            SXSSFWorkbook workbook = ExcelUtil.exportExcelX("会议统计", headMap, jsonArray, datePattern, 0, null);
            HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
            res.addHeader("content-type", "application/x-msdownload");
            res.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("会议统计.xlsx", "UTF-8"));
            OutputStream out1 = res.getOutputStream();
            workbook.write(out1);
            out1.flush();
            out1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
