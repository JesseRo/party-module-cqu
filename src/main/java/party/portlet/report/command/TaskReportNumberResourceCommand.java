package party.portlet.report.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.dao.ReportTaskOrgDao;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskReportPortlet,
                "mvc.command.name=/hg/report/number"
        },
        service = MVCResourceCommand.class
)
public class TaskReportNumberResourceCommand implements MVCResourceCommand {
    @Reference
    private ReportDao reportDao;

    private Gson gson = new Gson();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String taskId = ParamUtil.getString(resourceRequest, "taskId");
        Integer approved = reportDao.countByTaskIdAndStatus(taskId, ConstantsKey.APPROVED);
        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        try {
            res.getWriter().write(gson.toJson(JsonResponse.Success(approved)));
            res.setHeader("content-type", "application/json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
