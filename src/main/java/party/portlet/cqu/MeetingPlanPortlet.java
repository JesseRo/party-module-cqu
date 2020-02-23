package party.portlet.cqu;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.secondCommittee.MeetingPlanDao;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author jesse
 * @Filename MeetingPlanPortlet
 * @description
 * @Version 1.0
 * @History <br/>
 * @Date: 2020/2/22</li>
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.cqu",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=拟定计划",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/partyBranch/sconedParty.jsp",
                "javax.portlet.name=" + PartyPortletKeys.MeetingPlanPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class MeetingPlanPortlet extends MVCPortlet {
    @Reference
    private MeetingPlanDao planDao;

    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {

        super.doView(req, res);
    }
}
