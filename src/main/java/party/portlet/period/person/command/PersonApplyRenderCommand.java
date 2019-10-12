package party.portlet.period.person.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.period.PeriodKeys;
import party.portlet.report.dao.ReportDao;

import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PeriodKeys.PersonApplications,
                "mvc.command.name=/period/person/apply"
        },
        service = MVCRenderCommand.class
)
//入党申请
public class PersonApplyRenderCommand implements MVCRenderCommand {


    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
        return "/jsp/period/person/apply.jsp";
    }
}
