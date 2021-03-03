package hg.party.command.exportexcl;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.util.ConstantsKey;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.entity.ExportExcelEntity.OrgMeetingConut;
import hg.party.server.party.PartyMeetingPlanInfoService;
import hg.party.unity.ExcelUtil;
import party.constants.PartyPortletKeys;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.MeetingRecord,
                "javax.portlet.name=" + PartyPortletKeys.SecondMeetingRecordPortlet,
                "javax.portlet.name=" + PartyPortletKeys.BranchMeetingRecordPortlet,
                "mvc.command.name=/hg/MeetingCountExport"
        },
        service = MVCResourceCommand.class
)
public class MeetingCount implements MVCResourceCommand {
    @Reference
    private PartyMeetingPlanInfoService partyMeetingPlanInfoService;
    @Reference
    private OrgDao orgDao;
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
        String meetTheme = ParamUtil.getString(resourceRequest, "meetTheme");//会议主题
        meetTheme = HtmlUtil.escape(meetTheme);
        String meetType = ParamUtil.getString(resourceRequest, "meetType");//会议类型
        meetType = HtmlUtil.escape(meetType);
        boolean ifExportAll = ParamUtil.getBoolean(resourceRequest, "ifExportAll");//是否导出所有
        String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
        Organization organization = orgDao.findByOrgId(orgId);
        String orgType = organization.getOrg_type();

        if (orgType.equals(ConstantsKey.ORG_TYPE_SECONDARY)) {
            seconedId = orgId;
        }
        try {
            List<Map<String, Object>> list = partyMeetingPlanInfoService.find(startTime, endTime, meetType, meetTheme, seconedId, branchId,null,null, "",null);


            JSONArray jsonArray = new JSONArray();
            for (Map<String, Object> map : list) {
                OrgMeetingConut o = new OrgMeetingConut();
                o.setSecond_name((String) map.get("second_name"));
                o.setBranch_name((String) map.get("branch_name"));
                o.setCheck_person_name(StringUtils.isEmpty(map.get("check_person_name")) ? "" : map.get("check_person_name").toString());
                o.setCheck_person_org_name(StringUtils.isEmpty(map.get("check_person_org_name")) ? "" : map.get("check_person_org_name").toString());
                o.setMeeting_type((String) map.get("meeting_type"));
                o.setRelease_time(ExprotUntil.getDateString(map.get("submit_time") + ""));
                o.setMeeting_theme((String) map.get("meeting_theme"));
                o.setMeeting_theme_secondary((String) map.get("meeting_theme_secondary"));
                o.setStart_time(ExprotUntil.getDateString(map.get("start_time") + ""));
                o.setPlace( String.valueOf(map.get("campus")) + map.get("place_name"));
                o.setHost((String) map.get("host_name"));
                o.setContact((String) map.get("contact_name"));
                o.setContact_phone((String) map.get("contact_phone"));
                String plan_state = StringUtils.isEmpty(map.get("plan_state").toString()) ? "" : map.get("plan_state").toString();
                o.setPlan_state(StringUtils.isEmpty(map.get("note_id")) ? ExprotUntil.getMeetingRecordState(plan_state) : "已纪要");
                o.setAuditor(StringUtils.isEmpty(map.get("auditor")) ? "" : map.get("auditor") + "");
                o.setNote(ExprotUntil.getNote(map));
                jsonArray.add(o);
            }

            Map<String, String> headMap = new LinkedHashMap<String, String>();
            if (ifExportAll) {
				headMap.put("second_name", "二级党组织");
				headMap.put("branch_name", "党支部");
				headMap.put("meeting_type", "会议类型");
				headMap.put("release_time", "发布时间");
				headMap.put("meeting_theme", "开展主题");
//				headMap.put("meeting_theme_secondary", "党支部主题");
				headMap.put("start_time", "开展时间");
				headMap.put("place", "开展地点");
				headMap.put("host", "主持人");
				headMap.put("contact", "联系人");
				headMap.put("contact_phone", "联系人电话");
				headMap.put("plan_state", "任务状态");
				headMap.put("auditor", "审核人");
				headMap.put("check_person_name", "检查人");
//				 headMap.put("check_person_org_name","抽查人");
//				headMap.put("shoule_persons", "应到人数");
//				headMap.put("actual_persons", "实到人数人");
//				headMap.put("leave_persons", "请假人员");
//				headMap.put("attendance", "出勤率");
				headMap.put("note", "备注");
			}else {
				headMap.put("branch_name", "党支部");
				headMap.put("meeting_type", "会议类型");
				headMap.put("meeting_theme", "开展主题");
				headMap.put("start_time", "开展时间");
				headMap.put("contact", "联系人");
				headMap.put("plan_state", "任务状态");
			}
            String datePattern = "yyyy-MM-dd HH:ss:mm";
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
