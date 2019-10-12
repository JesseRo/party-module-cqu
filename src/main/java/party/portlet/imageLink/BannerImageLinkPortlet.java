package party.portlet.imageLink;

import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import hg.party.entity.chainingInfo.Hg_Chaining_Management_Info;
import hg.party.server.chainingServer.ChainingManagementInfoServer;
import party.constants.PartyPortletKeys;

/**
 * 内容摘要： banner图片链接
 * 创建人 　： Zhong LiMei
 * 创建日期： 2017年10月31日上午9:46:32
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=Banner链接",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/banner/view.jsp",
			"javax.portlet.init-param.edit-template=/jsp/banner/edit.jsp",
			"javax.portlet.name=" + PartyPortletKeys.BannerImageLink,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class BannerImageLinkPortlet extends MVCPortlet{
	
	Logger logger = Logger.getLogger(BannerImageLinkPortlet.class);
	
	@Reference
	private ChainingManagementInfoServer chainingServer;
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse){
		try {
			logger.info("BannerImageLinkPortlet doView...........");
			
			PortletPreferences portletPreferences = renderRequest.getPreferences();
			String size = portletPreferences.getValue(renderResponse.getNamespace()+"pageSize", "");
			String chaining_type = portletPreferences.getValue(renderResponse.getNamespace()+"chaining_type", "");
			
			if(chaining_type.length()==0){
				chaining_type="party";
			}
			List<Hg_Chaining_Management_Info> list = (List<Hg_Chaining_Management_Info>)chainingServer.findByChainingType("chaining_type", chaining_type);
			if(size.length()>0 && list.size()>Integer.parseInt(size)){
				list = list.subList(0, Integer.parseInt(size));
			}
//			renderRequest.setAttribute("list", list); 只要一张图片，不轮播
			renderRequest.setAttribute("list", list.get(0));
			super.doView(renderRequest, renderResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

