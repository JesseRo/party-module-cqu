package party.portlet.fee.school;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.fee.dao.FeeDao;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.cqu",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=学校捐款列表",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/fee/school/donate_list.jsp",
                "javax.portlet.name=" + PartyPortletKeys.SchoolDonateListPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class FeeDonateListPortlet extends MVCPortlet {
    @Reference
    private FeeDao feeDao;
    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        String id = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(req)).getParameter("id");
        req.setAttribute("id", id);
        List<Map<String, Object>> donates = feeDao.allDonate();
        req.setAttribute("tasks", donates);
        super.doView(req, res);
    }
}
