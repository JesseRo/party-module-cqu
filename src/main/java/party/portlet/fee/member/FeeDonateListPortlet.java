package party.portlet.fee.member;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.cqu",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=个人捐款列表",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/fee/member/donate_list.jsp",
                "javax.portlet.name=" + PartyPortletKeys.MemberDonateListPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class FeeDonateListPortlet extends MVCPortlet {
    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        String id = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(req)).getParameter("id");
        req.setAttribute("id", id);
        super.doView(req, res);
    }
}
