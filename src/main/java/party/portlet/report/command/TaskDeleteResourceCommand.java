package party.portlet.report.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.entity.Report;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskDraftPortlet,
                "mvc.command.name=/hg/report/delete"
        },
        service = MVCResourceCommand.class
)
public class TaskDeleteResourceCommand implements MVCResourceCommand {
    @Reference
    private ReportTaskDao reportTaskDao;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String taskId = ParamUtil.getString(resourceRequest, "task");
        String[] taskIds = taskId.split(",");
        reportTaskDao.deleteALl(taskIds);

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);

        try {
            res.getWriter().write("{\"success\": true}");
            res.setHeader("content-type", "application/json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
