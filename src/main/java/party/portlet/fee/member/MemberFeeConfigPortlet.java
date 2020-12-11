package party.portlet.fee.member;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.cqu",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=个人党费设置",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/fee/member/config.jsp",
                "javax.portlet.name=" + PartyPortletKeys.MemberFeeConfigPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class MemberFeeConfigPortlet extends MVCPortlet {
    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        HttpServletRequest request= PortalUtil.getHttpServletRequest(req);
        HttpServletRequest orgReq = PortalUtil.getOriginalServletRequest(request);

        PortletURL navigationURL = res.createRenderURL();
        navigationURL.setParameter("mvcRenderCommandName", "/member/config/render");
        HttpServletResponse response = PortalUtil.getHttpServletResponse(res);
        String resubmit = orgReq.getParameter("resubmit");
        response.sendRedirect(response.encodeRedirectURL(navigationURL.toString() + "&resubmit=" + resubmit));
    }
}
