package party.portlet.org;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

/**
 * @author Jesse
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=组织管理-管理员设置",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/jsp/member/org_admin.jsp",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class OrgAdminPortlet extends MVCPortlet {

    @Reference
    OrgDao orgDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String orgId = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");

        List<Organization> tree = orgDao.findTree(orgId, true, false);


        //以上级组织进行分组
        Map<String, List<Organization>> orgTree = tree.stream()
                .filter(p->!p.getOrg_id().equals(orgId))
                .collect(Collectors.groupingBy(Organization::getOrg_parent));
        Organization root = tree.stream().findFirst().orElse(null);
        renderRequest.setAttribute("root", root);
        renderRequest.setAttribute("tree", orgTree);

        super.doView(renderRequest, renderResponse);
    }

}