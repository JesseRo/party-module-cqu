package party.portlet.fee.branch;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
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
                "com.liferay.portlet.display-category=category.cqu",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=支部党费收交查询",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/fee/branch/search.jsp",
                "javax.portlet.name=" + PartyPortletKeys.BranchFeeSearchPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class FeeSearchPortlet extends MVCPortlet {
    @Reference
    private OrgDao orgDao;

    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        String orgId = (String) SessionManager.getAttribute(req.getRequestedSessionId(), "department");
        List<Organization> organizations = orgDao.findChildren(orgId);
        req.setAttribute("orgs", organizations);
        super.doView(req, res);
    }
}
