package party.portlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.secondCommittee.MeetingNotesDao;
import hg.party.dao.secondCommittee.MeetingPlanDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.MeetingNote;
import hg.party.entity.partyMembers.Member;
import hg.party.server.organization.OrgService;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.cqu",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=会议纪要详情审核",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/meetingNote/noteDetailAudit.jsp",
                "javax.portlet.name=" + PartyPortletKeys.MeetingNoteDetailAudit,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class MeetingNoteDetailAuditPortlet extends MVCPortlet {
    Gson gson = new Gson();
    Logger logger = Logger.getLogger(MeetingNoteDetailAuditPortlet.class);

    @Reference
    private MeetingPlanDao planDao;
    @Reference
    private MeetingNotesDao meetingNotesDao;
    @Reference
    private OrgService orgService;

    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        HttpServletRequest request= PortalUtil.getHttpServletRequest(req);
        HttpServletRequest oriRequest = PortalUtil.getOriginalServletRequest(request);
        String meetingId = oriRequest.getParameter("meetingId");
        Object orgId = SessionManager.getAttribute(req.getRequestedSessionId(), "department");
        Map<String, Object> meetingData = planDao.queryMeetingPlanByMeetingId(meetingId);
        String permission = "read";
        if(meetingData != null){
          String organization_id = (String) meetingData.get("organization_id");
          if(organization_id != null){
              Organization org = orgService.findOrgByOrgId(organization_id);
              if(org.getOrg_parent() != null && org.getOrg_parent().equals(orgId)){
                  permission = "audit";
              }
          }
        }
        List<Map<String, Object>> participants = planDao.meetingParticipants(meetingId);
        MeetingNote meetingNote = meetingNotesDao.findByMeetingId(meetingId);
        List<Member> memberList  = meetingNotesDao.findAttendMember(meetingId);
        if(meetingNote == null){
            meetingNote = new MeetingNote();
        }
        req.setAttribute("memberList", memberList);
        req.setAttribute("permission", permission);
        req.setAttribute("meeting", meetingData);
        req.setAttribute("meetingNote", meetingNote);
        req.setAttribute("participants", participants);
        super.doView(req, res);
    }
}


