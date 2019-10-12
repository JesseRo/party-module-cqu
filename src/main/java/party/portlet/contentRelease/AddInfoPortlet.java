package party.portlet.contentRelease;

import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.unity.ContentTypeConvert;
import hg.party.unity.ContentTypeEnum;
import party.constants.PartyPortletKeys;

/**
 * 新增新闻portlet
 * @author hg
 *
 */

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=addInfo Portlet",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/jsp/contentRelease/addContent.jsp",
			"javax.portlet.name=" + PartyPortletKeys.AddInfo,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class AddInfoPortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(AddInfoPortlet.class);
	@Override
	public void doView(RenderRequest req, RenderResponse resp){
		try {
			logger.info("AddInfoPortlet doView.......");
			HttpServletRequest request = PortalUtil.getHttpServletRequest(req);
			List<ContentTypeConvert> contentTypes = ContentTypeEnum.getConvertList();
			req.setAttribute("contentTypes", contentTypes);
			String contentPortletKey = ParamUtil.getString(req, "contentPortletKey");
			contentPortletKey =	HtmlUtil.escape(contentPortletKey);
			req.setAttribute("contentPortletKey", contentPortletKey);
			super.doView(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
