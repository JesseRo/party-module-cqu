package party.portlet.sms.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.secondCommittee.MeetingPlanDao;
import hg.party.entity.partyMembers.JsonResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Sms,
                "mvc.command.name=/sms/meeting"
        },
        service = MVCResourceCommand.class
)
public class MeetingResourceCommand implements MVCResourceCommand {
    @Reference
    MeetingPlanDao planDao;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        Integer page = ParamUtil.getInteger(resourceRequest, "page");
        Map<String,Object> meetingPlans = planDao.queryMeetingListByPage(page);
        try {
            HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponse);
            PrintWriter pw = response.getWriter();
            response.addHeader("content-type", "application/json");
            pw.write(new Gson().toJson(JsonResponse.Success(meetingPlans)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
