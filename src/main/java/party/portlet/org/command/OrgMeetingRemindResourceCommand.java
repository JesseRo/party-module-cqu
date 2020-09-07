package party.portlet.org.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.MeetingStatistics;
import hg.party.entity.partyMembers.JsonPageResponse;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.server.CQUMsgService;
import hg.party.server.memberMeeting.MemberMeetingServer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.transport.entity.PageQueryResult;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author jesse
 * @Filename OrgStatisticsResourceCommand
 * @description
 * @Version 1.0
 * @History <br/>
 * @Date: 2020/6/7</li>
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.MeetingStatistics,
                "mvc.command.name=/hg/org/meetingStatistics/sms"
        },
        service = MVCResourceCommand.class
)
public class OrgMeetingRemindResourceCommand implements MVCResourceCommand {

    @Reference
    private OrgDao orgDao;
    private static String smsTemplate = "【重庆大学】\n" +
            "%s：支部在%s——%s之间开展了%s次组织生活，特此通报。\n" +
            "%s";
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws PortletException {

        String orgId = ParamUtil.getString(resourceRequest, "id");
        String start = ParamUtil.getString(resourceRequest, "start");
        String end = ParamUtil.getString(resourceRequest, "end");
        long num = ParamUtil.getLong(resourceRequest, "num");
        boolean template = ParamUtil.getBoolean(resourceRequest, "template");

        String currentOrgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
        Organization currentOrg = orgDao.findByOrgId(currentOrgId);
        Organization organization = orgDao.findByOrgId(orgId);

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type", "application/json");

        try {
            if (template) {
                res.getWriter().write(gson.toJson(JsonResponse.Success(String.format(smsTemplate, organization.getOrg_name(), start, end,num, currentOrg.getOrg_name()))));
            } else {
                List<String> phones = orgDao.findAdminPhoneNumberIn(Collections.singletonList(orgId));
                if (phones != null && phones.size() > 0) {
                    CQUMsgService.sendPhoneNoticeMsg(String.join(",", phones),
                            String.format(smsTemplate, organization.getOrg_name(), start, end, currentOrg.getOrg_name()));
                }
                res.getWriter().write(gson.toJson(JsonResponse.Success()));
            }

        } catch (Exception e) {
            try {
                e.printStackTrace();
                res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return false;
    }
}
