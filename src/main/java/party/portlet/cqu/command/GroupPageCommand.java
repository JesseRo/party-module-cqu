package party.portlet.cqu.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.server.meetingPlan.MeetingPlanMemberGroupService;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.NewPlan,
                "mvc.command.name=/hg/group/page"
        },
        service = MVCResourceCommand.class
)
public class GroupPageCommand implements MVCResourceCommand {
    @Reference
    MeetingPlanMemberGroupService meetingPlanMemberGroupService;
    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws PortletException {
        String orgId = ParamUtil.getString(resourceRequest, "orgId");
        if (StringUtils.isEmpty(orgId)) {
            orgId = (String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
        }
        String keyword = ParamUtil.getString(resourceRequest, "keyword");
        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        int page = ParamUtil.getInteger(resourceRequest, "page");
        int size = ParamUtil.getInteger(resourceRequest, "limit");
        try {
            PostgresqlPageResult<Map<String, Object>> data = new PostgresqlPageResult(null, 0,0);
            if(orgId!=null && !StringUtils.isEmpty(String.valueOf(orgId))){
                if (StringUtils.isEmpty(keyword)){
                    data = meetingPlanMemberGroupService.searchMemberGroupPage(page, size,orgId,null);
                }else {
                    data = meetingPlanMemberGroupService.searchMemberGroupPage(page, size, orgId,keyword);
                }
            }
            Gson gson = new Gson();
            res.getWriter().write(gson.toJson(data.toJsonPageResponse()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
