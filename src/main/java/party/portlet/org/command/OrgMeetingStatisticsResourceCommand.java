package party.portlet.org.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.entity.partyMembers.JsonPageResponse;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.server.organization.OrgService;
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
import java.util.Map;

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
                "mvc.command.name=/hg/org/meetingStatistics"
        },
        service = MVCResourceCommand.class
)
public class OrgMeetingStatisticsResourceCommand implements MVCResourceCommand  {
    @Reference
    private OrgService orgService;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws PortletException {

        int page = ParamUtil.getInteger(resourceRequest, "page");
        int size = ParamUtil.getInteger(resourceRequest, "limit");
        String name = ParamUtil.getString(resourceRequest, "search");
        String orgId = ParamUtil.getString(resourceRequest, "orgId");

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type","application/json");

        try {
            PageQueryResult<Map<String, Object>> data = orgService.orgStatisticsPage(page, size, orgId, name);
            JsonPageResponse jsonPageResponse = new JsonPageResponse();
            if (data != null){
                jsonPageResponse.setCode(0);
                jsonPageResponse.setCount(data.getCount());
                jsonPageResponse.setData(data.getList());
            }else {
                jsonPageResponse.setCode(0);
                jsonPageResponse.setCount(0);
                jsonPageResponse.setData(Collections.emptyList());
            }
            res.getWriter().write(gson.toJson(jsonPageResponse));
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
