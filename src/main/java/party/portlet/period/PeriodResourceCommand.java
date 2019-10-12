package party.portlet.period;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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
                "javax.portlet.name=" + PartyPortletKeys.PeriodPortlet,
                "mvc.command.name=/period/view"
        },
        service = MVCResourceCommand.class
)
public class PeriodResourceCommand implements MVCResourceCommand {
    @Reference
    private ReportDao reportDao;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
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
