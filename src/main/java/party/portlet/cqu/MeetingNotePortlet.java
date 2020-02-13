package party.portlet.cqu;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.secondCommittee.MeetingPlanDao;
import hg.party.server.dwonlistserver.DownListServer;
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
                "javax.portlet.display-name=会议纪要",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/secondCommittee/meetingNote.jsp",
                "javax.portlet.name=" + PartyPortletKeys.MeetingNotePortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class MeetingNotePortlet extends MVCPortlet {
    Gson gson = new Gson();
    Logger logger = Logger.getLogger(MeetingNotePortlet.class);

    @Reference
    private MeetingPlanDao planDao;

    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        HttpServletRequest request= PortalUtil.getHttpServletRequest(req);
        HttpServletRequest oriRequest = PortalUtil.getOriginalServletRequest(request);
        String meetingId = oriRequest.getParameter("meetingId");
        Map<String, Object> meetingData = planDao.queryMeetingPlanByMeetingId(meetingId);
        List<Map<String, Object>> participants = planDao.meetingParticipants(meetingId);

        String formId = UUID.randomUUID().toString();
        SessionManager.setAttribute(request.getRequestedSessionId(), "formId-MeetingNote", formId);
        req.setAttribute("formId", formId);
        req.setAttribute("meeting", meetingData);
        req.setAttribute("participants", gson.toJson(participants));
        super.doView(req, res);
    }
}


