package party.portlet.cqu.command;


import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.secondCommittee.MeetingNotesDao;
import hg.party.entity.party.MeetingNote;
import hg.party.server.party.PartyMeetingNoteServer;
import hg.party.server.secondCommittee.SecondCommitteeService;
import hg.party.unity.ResourceProperties;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.MeetingNotePortlet,
			"mvc.command.name=/hg/meetingNote/submit"
	    },
	    service = MVCActionCommand.class
)
public class MeetingNoteSubmitCommand implements MVCActionCommand {
	Gson gson = new Gson();
	Logger logger = Logger.getLogger(MeetingNoteSubmitCommand.class);
	@Reference
	private MeetingNotesDao meetingNotesDao;
	@Override
	public boolean processAction(ActionRequest request, ActionResponse response) throws PortletException {
		try {
			String formId = ParamUtil.getString(request, "formId");
			String meetingId = ParamUtil.getString(request, "meetingId");
			String content = ParamUtil.getString(request, "meeting_content");
			String attendance = ParamUtil.getString(request, "attendances");
			List<String> attendances;
			if (StringUtils.isEmpty(attendance)){
				attendances = Collections.emptyList();
			}else {
				attendances = Arrays.asList(attendance.split(","));
			}
			if(!StringUtils.isEmpty(meetingId)){
				MeetingNote meetingNote = meetingNotesDao.findByMeetingId(meetingId);
				if(meetingNote == null ){
					meetingNote = new MeetingNote();
					meetingNote.setMeeting_id(meetingId);
				}
				meetingNote.setAttendance(gson.toJson(attendances));
				meetingNote.setAttachment(content);
				meetingNotesDao.saveOrUpdate(meetingNote);
			}
			response.sendRedirect("/meeting_check");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
