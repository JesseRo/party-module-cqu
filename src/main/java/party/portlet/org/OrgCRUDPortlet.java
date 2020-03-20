package party.portlet.org;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import org.osgi.service.component.annotations.Component;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

/**
 * @author Jesse
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=基础数据管理-党组织管理",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/jsp/member/org_crud.jsp",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.name=" + PartyPortletKeys.OrgCRUD,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class OrgCRUDPortlet extends MVCPortlet {
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String sessionId=renderRequest.getRequestedSessionId();
        Object roleObj =	SessionManager.getAttribute(sessionId, "role");
        String role = "";
        if(roleObj != null){
           PartyOrgAdminTypeEnum partyOrgAdminTypeEnum =  PartyOrgAdminTypeEnum.getEnumByRole(roleObj.toString());
            role = partyOrgAdminTypeEnum.getType();
        }
        renderRequest.setAttribute("role",role);
        super.doView(renderRequest, renderResponse);
    }

}