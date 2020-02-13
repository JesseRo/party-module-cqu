package party.portlet.echarts;

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
 * 文件名称： 报表<br>
 * 创建人 　： Yu Jiang Xia<br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=二级党委统计",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/echarts/secondaryEcharts.jsp",
			"javax.portlet.name=" + PartyPortletKeys.SecondaryEcharts,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class SecondaryEchartsPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(SecondaryEchartsPortlet.class);

	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		super.doView(renderRequest, renderResponse);
	}

}

