package party.portlet.period.person.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import org.osgi.service.component.annotations.Component;
import party.portlet.period.PeriodKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PeriodKeys.PersonApplications,
                "mvc.command.name=/period/person/volunteerApply"
        },
        service = MVCRenderCommand.class
)
//入党志愿申请
public class PersonVolunteerApplyRenderCommand implements MVCRenderCommand {


    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
        return "/jsp/period/person/volunteerApply.jsp";
    }
}
