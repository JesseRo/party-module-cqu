package party.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=会议纪要审核",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/meetingNote/audit.jsp",
                "javax.portlet.name=" + PartyPortletKeys.MeetingNoteAudit,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class MeetingNoteAuditPortlet extends MVCPortlet {
    @Reference
    private OrgDao orgDao;

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String orgId = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
        List<Organization> organizations = orgDao.findChildren(orgId);
        renderRequest.setAttribute("orgs", organizations);
        super.doView(renderRequest, renderResponse);
    }
}
