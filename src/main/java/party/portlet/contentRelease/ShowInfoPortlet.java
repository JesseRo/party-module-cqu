package party.portlet.contentRelease;

import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.entity.contentManagementInfo.Hg_Content_Management_Info;
import hg.party.server.contentInfo.ContentInfoServer;
import party.constants.PartyPortletKeys;

/**
 * @author caoxm
 * 树形portlet组件
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=showInfo Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/jsp/contentRelease/showContent.jsp",
		"javax.portlet.name=" + PartyPortletKeys.ShowInfo,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ShowInfoPortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(ShowInfoPortlet.class);
	@Reference
    private ContentInfoServer infoServer;
	
	public void doView(RenderRequest req, RenderResponse res){
		try {
			logger.info("ShowInfoPortlet doView........");
			HttpServletRequest request = PortalUtil.getHttpServletRequest(req);
			String id = PortalUtil.getOriginalServletRequest(request).getParameter("id");
		    id = HtmlUtil.escape(id);
			if(null != id && !"".equals(id)){
				Hg_Content_Management_Info info = infoServer.resInfoById(Integer.parseInt(id));
				if(null != info){
					req.setAttribute("content_title", info.getContent_title());
					req.setAttribute("content_body", info.getContent_body());
				}
			}else{
				String content_title = PortalUtil.getOriginalServletRequest(request).getParameter("content_title_show");
				content_title = HtmlUtil.escape(content_title);
				String content_body = PortalUtil.getOriginalServletRequest(request).getParameter("content_body_show");
				content_body = HtmlUtil.escape(content_body);
				req.setAttribute("content_title", content_title);
				req.setAttribute("content_body", content_body);
			}
			super.doView(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}