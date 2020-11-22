package party.portlet.fee.secondary;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.fee.dao.FeeDao;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.cqu",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=二级党委捐款统计",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/fee/secondary/donate_statistics.jsp",
                "javax.portlet.name=" + PartyPortletKeys.SecondaryDonateStatisticsPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SecondaryDonateStatisticsPortlet extends MVCPortlet {
    @Reference
    private FeeDao feeDao;
    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        HttpServletRequest request= PortalUtil.getHttpServletRequest(req);
        HttpServletRequest orgReq = PortalUtil.getOriginalServletRequest(request);

        PortletURL navigationURL = res.createRenderURL();
        navigationURL.setParameter("mvcRenderCommandName", "/donate/render");
        HttpServletResponse response = PortalUtil.getHttpServletResponse(res);
        String orgId = orgReq.getParameter("orgId");
        orgId = StringUtils.isEmpty(orgId) ? "" : orgId;
        response.sendRedirect(response.encodeRedirectURL(navigationURL.toString() + "&orgId=" + orgId));
    }
}
