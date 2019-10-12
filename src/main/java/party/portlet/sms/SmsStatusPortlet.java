package party.portlet.sms;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import hg.party.entity.sms.Status;
import hg.party.server.sms.SmsService;
import org.osgi.service.component.annotations.Component;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Jesse
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=短信状态",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/jsp/sms/view.jsp",
                "com.liferay.portlet.requires-namespaced-parameters=false",

                "javax.portlet.name=" + PartyPortletKeys.Sms,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SmsStatusPortlet extends MVCPortlet {
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        // TODO Auto-generated method stub
        super.doView(renderRequest, renderResponse);
    }

}