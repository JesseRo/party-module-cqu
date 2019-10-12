package party.portlet.sms.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.sms.Status;
import hg.party.server.sms.SmsService;
import org.osgi.service.component.annotations.Component;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Sms,
                "mvc.command.name=/sms/status"
        },
        service = MVCResourceCommand.class
)
public class StatusResourceCommand implements MVCResourceCommand{
    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String meetingId = ParamUtil.getString(resourceRequest, "meeting");
        List<Status> statuses = SmsService.smsStatus(meetingId);
        try {
            HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponse);
            response.addHeader("content-type", "application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(new Gson().toJson(JsonResponse.Success(statuses)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
