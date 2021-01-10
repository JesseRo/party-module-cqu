package party.portlet.fee.branch;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
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
                "javax.portlet.display-name=支部党费收交列表",
                "javax.portlet.init-param.template-path=/",

                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/fee/branch/fee_list.jsp",
                "javax.portlet.name=" + PartyPortletKeys.BranchFeeListPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class BranchFeeListPortlet extends MVCPortlet {
    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        super.doView(req, res);
    }
}
