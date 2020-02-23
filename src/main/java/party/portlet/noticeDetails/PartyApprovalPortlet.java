package party.portlet.noticeDetails;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.dao.party.PartyMeetingPlanInfoDao;
import hg.party.dao.secondCommittee.MeetingNotesDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.MeetingNote;
import hg.party.entity.partyMembers.Member;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.entity.party.MeetingPlan;
import hg.party.server.party.PartyMeetingPlanInfo;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建日期： 2017年11月2日上午11:32:50<br>
 * 版本号　 ： v1.0.0<br>
 * 修改内容： <br>
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=审批查看详情",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/noticeDetails/Approvalview.jsp",
                "javax.portlet.name=" + PartyPortletKeys.PartyApproval,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class PartyApprovalPortlet extends MVCPortlet {

    @Reference
    private PartyMeetingPlanInfoDao partyMeetingPlanInfo;

    @Reference
	private OrgDao orgDao;

    @Reference
    private MeetingNotesDao notesDao;

    @Reference
    private MemberDao memberDao;

    private Gson gson = new Gson();

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        try {
            HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
            String meetingId = PortalUtil.getOriginalServletRequest(request).getParameter("meetingId");
            meetingId = HtmlUtil.escape(meetingId);
            String orgType = PortalUtil.getOriginalServletRequest(request).getParameter("orgType");
            orgType = HtmlUtil.escape(orgType);
            String orgId = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
            if ("".equals(meetingId) || null == meetingId) {
                meetingId = "aca7500c-8603-4bac-b7c3-e6404b7dd720";
            }
            Map<String, Object> meetingPlan = partyMeetingPlanInfo.meetingDetail(meetingId);

                //根据meetingId获取该会议信息
            Organization org = orgDao.findByOrgId((String)meetingPlan.get("organization_id"));
            //会议主题
            String meetingTheme = (String) meetingPlan.get("meeting_theme");
            //会议类型
            String type = (String) meetingPlan.get("meeting_type");
            //会议时间
            String time = dateFormat((Date) meetingPlan.get("start_time"), (Date)meetingPlan.get("end_time"));
            //会议内容
            String content = (String) meetingPlan.get("content");
            //附件
            String attachment = (String) meetingPlan.get("attachment");
            //参会人员
            String meetingUserId = (String) meetingPlan.get("participant_group");
            List<String> meetingUserList = gson.fromJson(meetingUserId, new TypeToken<List<String>>(){}.getType());
            List<Member> participants = memberDao.findMemberByUserId(meetingUserList);
            String meetingUserName = participants.stream().map(Member::getMember_name).collect(Collectors.joining(","));

            MeetingNote meetingNote = notesDao.findByMeetingId(meetingId);
            if (meetingNote != null){
                renderRequest.setAttribute("hasNote", true);
                renderRequest.setAttribute("note", meetingNote);
                List<String> meetingAttendances = gson.fromJson(meetingNote.getAttendance(), new TypeToken<List<String>>(){}.getType());
                List<Member> members = memberDao.findMemberByUserId(meetingAttendances);
                renderRequest.setAttribute("attendances", members.stream()
                        .map(Member::getMember_name).collect(Collectors.joining(",")));
            }

            System.out.println("orgType=" + orgType);

            renderRequest.setAttribute("meetingTheme", meetingTheme);
            renderRequest.setAttribute("type", type);
            renderRequest.setAttribute("time", time);
            renderRequest.setAttribute("content", content);
            renderRequest.setAttribute("meetingId", meetingId);
            renderRequest.setAttribute("attachment", attachment);
            renderRequest.setAttribute("meetingPlan", meetingPlan);
            renderRequest.setAttribute("meetingUserName", meetingUserName);
            renderRequest.setAttribute("orgType", orgType);
            renderRequest.setAttribute("org", org);
            renderRequest.setAttribute("isSelf", orgId.equals(org.getOrg_id()));


        } catch (Exception e) {
            e.printStackTrace();
        }
        super.doView(renderRequest, renderResponse);
    }

    //党员待办事项中时间格式化
    public String dateFormat(Date startTime, Date endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        String str1 = sdf.format(startTime);
        String str3 = sdf1.format(startTime);
        String str2 = sdf.format(endTime);
        String str4 = sdf1.format(endTime);
        String time = "";
        if (str1.equals(str2)) {
            time += str1 + " " + str3 + "-" + str4;
        } else {
            time += str1 + " " + str3 + "-" + str2 + " " + str4;
        }
        return time;
    }
}
