package party.portlet.party;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import party.constants.PartyPortletKeys;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

/**
 * 文件名称： party<br>
 * 内容摘要： 党费计算<br>
 * 创建人 　： lcw<br>
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=党费计算",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.portlet-mode=text/html;view,edit",
		"javax.portlet.init-param.view-template=/jsp/party/duesCalculate.jsp",
		"javax.portlet.name=" + PartyPortletKeys.DuesCalculate,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DuesCalculatePortlet extends MVCPortlet {
	Logger log = Logger.getLogger(DuesCalculatePortlet.class);
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		super.doView(renderRequest, renderResponse);
	}
}
