package hg.party.command.downlist;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.entity.party.Hg_Value_Attribute_Info;
import hg.party.server.dwonlistserver.DownListServer;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.DownList,
			"mvc.command.name=/asset/addOrUpdateDeviceRender"
	    },
	    service = MVCRenderCommand.class
	)
public class ListAddOrUpdateRenderCommand implements MVCRenderCommand {
	Logger logger = Logger.getLogger(ListAddOrUpdateRenderCommand.class);
	@Reference
	private DownListServer listServer;

//	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
//		logger.info("AssetAddOrUpdateRenderCommand render....");
//		int id = Integer.parseInt(ParamUtil.getString(renderRequest,"deldevie"));
//			Hg_Value_Attribute_Info asset_Attributes = listServer.findById(id);
//			renderRequest.setAttribute("asset_Attributes", asset_Attributes);
//			renderRequest.setAttribute("id", id);  
//		
//		return "/jsp/downlist/listAddOrUpdate.jsp";
//	}
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		logger.info("AssetAddOrUpdateRenderCommand render....");
		int id = ParamUtil.getInteger(renderRequest, "id");
		if(id != 0){
			Hg_Value_Attribute_Info asset_Attributes = listServer.findById(id);

			renderRequest.setAttribute("asset_Attributes", asset_Attributes);  
		}
		
		return "/jsp/downlist/listAddOrUpdate.jsp";
	}

}
