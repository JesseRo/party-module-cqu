package hg.party.command.contentRelease;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.contentInfo.ContentInfoServer;
import hg.party.server.party.ValueAttributeService;
import hg.party.unity.ContentTypeConvert;
import hg.party.unity.ContentTypeEnum;
import party.constants.PartyPortletKeys;

/**
 * 
 * 文件名称： contentRelease<br>
 * 内容摘要： 从列表视图跳转到新增内容信息页面<br>
 * 创建人 　： Zhang Minggang<br>
 * 创建日期： 2017年9月18日下午3:54:26<br>
 */

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
		"mvc.command.name=/info/addInfo"
    },
    service = MVCRenderCommand.class
)
public class AddInfoRenderCommand implements MVCRenderCommand {
	@Reference
	private ValueAttributeService valueAttributeService;
	@Reference
	private ContentInfoServer contentInfoServer;
	@Override
	public String render(RenderRequest req, RenderResponse resp) throws PortletException {
		
		String contentPortletKey = ParamUtil.getString(req, "contentPortletKey");
		contentPortletKey =	HtmlUtil.escape(contentPortletKey);
		req.setAttribute("contentPortletKey", contentPortletKey);
		
		List<ContentTypeConvert> contentTypes = ContentTypeEnum.getConvertList();//内容类型
		req.setAttribute("contentTypes", contentTypes);
		
		List<String> attValues = valueAttributeService.findValues();//新闻类型
		req.setAttribute("attValues", attValues);
		
		String resourceId = ParamUtil.getString(req, "resourceId");
		if(null != resourceId && !"".equals(resourceId)){
			List<Map<String,Object>> list = contentInfoServer.findContentAndAttachmentByResourcesId(resourceId);
			req.setAttribute("list", list.get(0));
		}
		return "/jsp/contentRelease/addContent.jsp";
	}

}
