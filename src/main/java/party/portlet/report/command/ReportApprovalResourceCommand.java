package party.portlet.report.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.entity.Report;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskReportPortlet,
                "mvc.command.name=/brunch/report/approval"
        },
        service = MVCResourceCommand.class
)
public class ReportApprovalResourceCommand  implements MVCResourceCommand {
    @Reference
    private ReportDao reportDao;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String reportId = ParamUtil.getString(resourceRequest, "report");
        int status = ParamUtil.getInteger(resourceRequest, "status");
        Report report = reportDao.findByReportId(reportId);
        report.setStatus(status);
        if (status == ConstantsKey.REJECTED){
            String reason = ParamUtil.getString(resourceRequest, "reason");
            report.setReason(reason);
        }
        reportDao.saveOrUpdate(report);

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
