package hg.party.command.PartyBranch;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xdgf.usermodel.section.geometry.Ellipse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.partyBranch.PartyBranchDao;
import hg.party.entity.party.MeetingPlan;
import hg.party.server.partyBranch.PartyBranchService;
import hg.party.unity.ResourceProperties;
import party.constants.PartyPortletKeys;

//党支部报送计划
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Form,
                "mvc.command.name=/hg/postSubmissions"
        },
        service = MVCActionCommand.class
)
public class Submission extends BaseMVCActionCommand {
    PartyBranchService service = new PartyBranchService();
    @Reference
    private PartyBranchDao dao;

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        String orgId = (String) SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "department");
        //获取组织类型
        String orgType = service.findSconedAndBranch(orgId);
        String formId = ParamUtil.getString(actionRequest, "formId");
        String infromid = ParamUtil.getString(actionRequest, "infrom_id");
        String startDate = ParamUtil.getString(actionRequest, "timeDuring");
        String attendMeetingPerson = ParamUtil.getString(actionRequest, "participate");
        String host = ParamUtil.getString(actionRequest, "host");
        String linkMan = ParamUtil.getString(actionRequest, "contact");
        String linkManTelephone = ParamUtil.getString(actionRequest, "phoneNumber");
        String content = ParamUtil.getString(actionRequest, "new_12");
        String branch = ParamUtil.getString(actionRequest, "branch");
        String conferenceType = ParamUtil.getString(actionRequest, "conferenceType");
        String meeting_theme = ParamUtil.getString(actionRequest, "subject");
        String timeLasts = ParamUtil.getString(actionRequest, "timeLasts");
        String location = ParamUtil.getString(actionRequest, "location");
        String newAndOld = ParamUtil.getString(actionRequest, "newAndOld");
        String sit = ParamUtil.getString(actionRequest, "sit");
        String meetingId = ParamUtil.getString(actionRequest, "meeting_id");
        String customTheme = ParamUtil.getString(actionRequest, "customTheme");
        formId = HtmlUtil.escape(formId);
        infromid = HtmlUtil.escape(infromid);
        startDate = HtmlUtil.escape(startDate);
        attendMeetingPerson = HtmlUtil.escape(attendMeetingPerson);
        host = HtmlUtil.escape(host);
        linkMan = HtmlUtil.escape(linkMan);
        linkManTelephone = HtmlUtil.escape(linkManTelephone);
      //  content = HtmlUtil.escape(content);
        branch = HtmlUtil.escape(branch);
        conferenceType = HtmlUtil.escape(conferenceType);
        meeting_theme = HtmlUtil.escape(meeting_theme);
        timeLasts = HtmlUtil.escape(timeLasts);
        location = HtmlUtil.escape(location);
        newAndOld = HtmlUtil.escape(newAndOld);
        sit = HtmlUtil.escape(sit);
        meetingId = HtmlUtil.escape(meetingId);
        customTheme = HtmlUtil.escape(customTheme);
        
        String fileName = "attachment";
        UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
        File[] uploadFiles = uploadPortletRequest.getFiles(fileName);

        MeetingPlan m = null;
        if (newAndOld.equals("edit")) {
            m = dao.findMeetingPlan(meetingId);
        } else {
            m = new MeetingPlan();
            m.setTask_status("1");
        }
        m.setMeeting_theme_secondary(customTheme);
        m.setOrganization_id(branch);
        m.setMeeting_type(conferenceType);
        m.setMeeting_theme(meeting_theme);
        m.setStart_time(paerse(startDate));
        m.setTotal_time(Integer.parseInt(timeLasts));
        m.setEnd_time(endTime(startDate, timeLasts));
        m.setPlace(location);
        m.setParticipant_group(attendMeetingPerson);
        m.setHost(host);
        m.setContact(linkMan);
        m.setContact_phone(linkManTelephone);
        m.setContent(content);
        m.setInform_id(infromid);
        m.setSit(sit);
        LocalDateTime ldTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        Timestamp t = Timestamp.valueOf(ldTime);
        m.setSubmit_time(t);
        String meeting_id = UUID.randomUUID().toString();
        if (uploadFiles != null && uploadFiles.length > 0 && uploadFiles[0] != null) {
            m.setAttachment("t");
            m.setMeeting_id(meeting_id);
        } else {
            if (!StringUtils.isEmpty(meetingId)) {
                meeting_id = meetingId;
                m.setMeeting_id(meeting_id);
            } else {
                m.setMeeting_id(meeting_id);
            }
        }

        
        synchronized (PortalUtil.getHttpServletRequest(actionRequest).getSession()) {
            String originalFormId = (String) SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "formId-Submission");
            if (formId.equals(originalFormId)) {
                if (newAndOld.equals("未读")) {
                    service.save(m);
                } else if (newAndOld.equals("old")) {
                   // int n = service.save("DELETE from hg_party_meeting_plan_info WHERE inform_id='" + infromid + "' AND organization_id='" + branch + "'");
                    int n = service.deleteMeetingPlan(infromid, branch);
                	if (n == 1) {
                        service.save(m);
                    }
                } else if (newAndOld.equals("edit")) {
                    dao.update(m);
                }
                if (uploadFiles != null && uploadFiles.length > 0 && uploadFiles[0] != null) {
                    saveAttchment(uploadFiles, meeting_id, uploadPortletRequest);
                }
                SessionManager.setAttribute(actionRequest.getRequestedSessionId(), "formId-Submission", "null");
            }else{
                return;
            }
        }
        //转跳
        if (newAndOld.equals("edit") && "secondary".equals(orgType)) {
            actionResponse.sendRedirect("/approvalplantwo");
        } else if ("branch".equals(orgType)) {
            actionResponse.sendRedirect("/web/guest/task");
        } else if ("secondary".equals(orgType)) {
            actionResponse.sendRedirect("/backlogtwo");
        } else {
            actionResponse.sendRedirect("/approvalplanone");
        }
    }

    //解析时间
    public static Timestamp paerse(String string) {
        try {
            Timestamp ts = Timestamp.valueOf(string);
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * @param startTime
     * @param totalTime
     * @return 结束时间
     */
    public static Timestamp endTime(String startTime, String totalTime) {
        try {
            Timestamp tt = paerse(startTime);
            long dataLong = tt.getTime() + Integer.parseInt(totalTime) * 1000 * 60;
            tt = new Timestamp(dataLong);
            return tt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //保存附件
    public void saveAttchment(File[] uploadFiles, String resoureid, UploadPortletRequest uploadPortletRequest) {
        if (uploadFiles != null) {
            for (File file : uploadFiles) {
                String sourceFileName = uploadPortletRequest.getFileName("attachment");
                String[] strArr = sourceFileName.split("\\.");
                String suffix = "";//文件后缀
                if (strArr != null && strArr.length > 0) {
                    suffix = strArr[strArr.length - 1];
                }
                //保存文件最终路径
                ResourceProperties resourceProperties = new ResourceProperties();
                Properties properties = resourceProperties.getResourceProperties();//获取配置文件
                String uploadPath = properties.getProperty("uploadPath");
                File folder = new File(uploadPath);
                String fileUrl = "/" + sourceFileName;
                String saveName = UUID.randomUUID().toString();
                if ("" != suffix) {
                    fileUrl = "/" + "ajaxFileName" + "/" + saveName + sourceFileName;
                }

                File filePath = new File(folder.getAbsolutePath() + fileUrl);
                //保存文件到物理路径
                try {
                    FileUtil.copyFile(file, filePath);
                    Timestamp timestamp = new Timestamp(new Date().getTime());
                    String sql = "INSERT INTO hg_party_attachment (\"resource_id\", \"attachment_name\", \"attachment_type\", \"attachment_url\", \"attachment_date\") VALUES ('" + resoureid + "','" + sourceFileName + "', '" + suffix + "', '" + fileUrl + "','" + timestamp + "')";
                    service.save(sql);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
