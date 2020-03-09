package party.portlet.transport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.entity.ReportTask;
import party.portlet.report.entity.view.ExcelHandler;
import party.portlet.transport.dao.TransportDao;
import party.portlet.transport.entity.Transport;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=组织关系转移申请",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/transport/transport.jsp",
                "javax.portlet.name=" + PartyPortletKeys.TransportApplyPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class TransportApplyPortlet extends MVCPortlet {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @Reference
    private OrgDao orgDao;

    @Reference
    private TransportDao transportDao;

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        String userId = (String)SessionManager.getAttribute(
                renderRequest.getRequestedSessionId(), "userName");



        String orgId = (String)SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "orgId");
        Organization organization = orgDao.findByOrgId(orgId);
        if (organization.getOrg_type().equals(ConstantsKey.ORG_TYPE_BRANCH)){
            organization = orgDao.findByOrgId(organization.getOrg_parent());
        }
        Organization root = orgDao.findRoot();

        List<Organization> brunchInSecondary = orgDao.findTree(organization.getOrg_id(), true, false);
        List<Organization> allOrg = orgDao.findTree(root.getOrg_id(), true, false);

        Map<String, List<Organization>> brunchGroup = new LinkedHashMap<>();
        brunchInSecondary.stream()
                .filter(p->p.getOrg_type().equals(ConstantsKey.ORG_TYPE_SECONDARY))
                .forEach( p-> {
                    List<Organization> orgs = brunchInSecondary.stream()
                            .filter(b -> b.getOrg_parent().equalsIgnoreCase(p.getOrg_id())).collect(Collectors.toList());
                    brunchGroup.put(p.getOrg_name(), orgs);
                });

        Map<String, List<Organization>> allBrunchGroup = new LinkedHashMap<>();
        allOrg.stream()
                .filter(p->p.getOrg_type().equals(ConstantsKey.ORG_TYPE_SECONDARY))
                .forEach( p-> {
                    List<Organization> orgs = allOrg.stream()
                            .filter(b -> b.getOrg_parent().equalsIgnoreCase(p.getOrg_id())).collect(Collectors.toList());
                    allBrunchGroup.put(p.getOrg_name(), orgs);
                });

        Transport transport = transportDao.findByUser(userId);
        String isResubmit = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest)).getParameter("resubmit");

        if (transport == null || ("1").equalsIgnoreCase(isResubmit)){
            renderRequest.setAttribute("transportJson", "null");
            renderRequest.setAttribute("already", 0);
        }else {
            renderRequest.setAttribute("already", 1);
            renderRequest.setAttribute("isResubmit", isResubmit);
            renderRequest.setAttribute("statusList", ConstantsKey.STATUS_LIST);
            renderRequest.setAttribute("transportJson", gson.toJson(transport));
            renderRequest.setAttribute("transport", transport);
        }
        renderRequest.setAttribute("allBrunchGroup", allBrunchGroup);
        renderRequest.setAttribute("brunchGroup", brunchGroup);

        super.doView(renderRequest, renderResponse);
    }


}
