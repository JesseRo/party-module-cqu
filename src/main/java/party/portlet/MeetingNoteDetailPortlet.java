package party.portlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.secondCommittee.MeetingNotesDao;
import hg.party.dao.secondCommittee.MeetingPlanDao;
import hg.party.entity.party.MeetingNote;
import hg.party.entity.partyMembers.Member;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.cqu",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=会议纪要详情",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/meetingNote/noteDetail.jsp",
                "javax.portlet.name=" + PartyPortletKeys.MeetingNoteDetail,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class MeetingNoteDetailPortlet extends MVCPortlet {
    Gson gson = new Gson();
    Logger logger = Logger.getLogger(MeetingNoteDetailPortlet.class);

    @Reference
    private MeetingPlanDao planDao;
    @Reference
    private MeetingNotesDao meetingNotesDao;

    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        HttpServletRequest request= PortalUtil.getHttpServletRequest(req);
        HttpServletRequest oriRequest = PortalUtil.getOriginalServletRequest(request);
        String meetingId = oriRequest.getParameter("meetingId");
        Map<String, Object> meetingData = planDao.queryMeetingPlanByMeetingId(meetingId);
        List<Map<String, Object>> participants = planDao.meetingParticipants(meetingId);
        MeetingNote meetingNote = meetingNotesDao.findByMeetingId(meetingId);
        if(meetingNote == null){
            meetingNote = new MeetingNote();
        }
        req.setAttribute("meeting", meetingData);
        req.setAttribute("meetingNote", meetingNote);
        List<Member> memberList  = meetingNotesDao.findAttendMember(meetingId);
        req.setAttribute("memberList", memberList.stream().map(Member::getMember_name).collect(Collectors.joining(",")));
        req.setAttribute("participants", participants.stream().map(p->(String)p.get("member_name")).collect(Collectors.joining(",")));
        super.doView(req, res);
    }
}


