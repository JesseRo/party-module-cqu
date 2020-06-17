package hg.party.command.party;


import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.server.party.PartyMeetingNoteService;
import hg.party.server.party.PartyMeetingPlanInfoService;
import hg.util.postgres.PostgresqlPageResult;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 会议纪要审核分页查询
 */
@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.MeetingNoteDetailAudit,
				"mvc.command.name=/org/meetingNote/audit"
		},
		service = MVCResourceCommand.class
	)

public class MeetingNoteAuditCommand implements MVCResourceCommand {
	@Reference
	PartyMeetingNoteService partyMeetingNoteService;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		int noteId = ParamUtil.getInteger(resourceRequest, "noteId");
		boolean isPass = ParamUtil.getBoolean(resourceRequest, "isPass");
		String rejectReason = ParamUtil.getString(resourceRequest, "rejectReason");
		try {
			int ret = 0;
			Gson gson = new Gson();
			if(isPass){
				ret = partyMeetingNoteService.passMeetingNote(noteId);
			}else{
				rejectReason = rejectReason == null?"":rejectReason;
				ret =partyMeetingNoteService.rejectMeetingNote(noteId,rejectReason);
			}
			if(ret > 0){
				res.getWriter().write(gson.toJson(ResultUtil.success(ret)));
			}else{
				res.getWriter().write(gson.toJson(ResultUtil.fail(null)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
