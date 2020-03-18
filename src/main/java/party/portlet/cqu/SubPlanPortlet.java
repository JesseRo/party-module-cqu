package party.portlet.cqu;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.entity.organization.Organization;
import hg.party.server.organization.OrgService;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                "javax.portlet.name=" + PartyPortletKeys.Form,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SubPlanPortlet extends MVCPortlet {
    Logger logger = Logger.getLogger(SubPlanPortlet.class);
    @Reference
    private OrgService orgService;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String sessionId=renderRequest.getRequestedSessionId();
        String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
        String role =	SessionManager.getAttribute(sessionId, "role").toString();//用户选中角色
        PartyOrgAdminTypeEnum orgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnumByRole(role);
        Organization organization = new Organization() ;
        List<Map<String,Object>> userList = new ArrayList<>();
        if(orgAdminTypeEnum!=null){
            organization = orgService.findAdminOrg(userId, orgAdminTypeEnum);
            userList = orgService.findUsersByOrg(organization.getOrg_id(),orgAdminTypeEnum);
        }
        logger.info("userList size:"+userList.size());
        renderRequest.setAttribute("organization",organization);
        renderRequest.setAttribute("userList",userList);
        super.doView(renderRequest, renderResponse);
    }
}
