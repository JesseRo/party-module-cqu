package party.portlet.transport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.dao.TransportDao;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=组织关系转移申请-审批",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/transport/approval.jsp",
                "javax.portlet.name=" + PartyPortletKeys.TransportApproval,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class TransportApprovalPortlet extends MVCPortlet {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @Reference
    private OrgDao orgDao;
    @Reference
    private TransportDao transportDao;
    @Reference
    private RetentionDao retentionDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String department = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
        renderRequest.setAttribute("department", department);
        super.doView(renderRequest, renderResponse);
    }


}
