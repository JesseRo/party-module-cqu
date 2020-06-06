package party.portlet.noticeDetails;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import hg.party.entity.party.OrgInform;
import hg.party.server.PartyMeetingNoticeService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;


import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;
import party.constants.PartyPortletKeys;

/**
 * 通知信息详情页面
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=通知详情",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/noticeDetails/meetingNoticeDetail.jsp",
			"javax.portlet.name=" + PartyPortletKeys.NoticeDetails,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class NoticeDetailsPortlet extends MVCPortlet {

	@Reference
	private PartyMeetingNoticeService partyMeetingNoticeService;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		try {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
			String informId = PortalUtil.getOriginalServletRequest(request).getParameter("informId");
			informId = HtmlUtil.escape(informId);
			if(!StringUtils.isEmpty(informId)){
				//通知详情
				OrgInform informDetail = partyMeetingNoticeService.findInformDetail(informId);
				if(informDetail == null){
					informDetail = new OrgInform();
				}
				//通知组织单位list
				List<Map<String,Object>> orgList =  partyMeetingNoticeService.findInformOrgList(informId);
				//通知附件
				Map<String,Object> attachFile = partyMeetingNoticeService.findInformAttachFile(informId);
				informDetail.setContent(HtmlUtils.htmlUnescape(informDetail.getContent()));
				renderRequest.setAttribute("informDetail", informDetail);
				renderRequest.setAttribute("orgList", orgList);
				renderRequest.setAttribute("attachFile", attachFile);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		super.doView(renderRequest, renderResponse);
	}

}
