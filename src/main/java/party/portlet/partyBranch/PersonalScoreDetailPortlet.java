package party.portlet.partyBranch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.google.gson.JsonObject;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import dt.session.SessionManager;
import hg.party.command.secondCommittee.InformPartyCommand;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=个人评分详情页面",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/partyBranch/personalScoreDetail.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PersonalScoreDetail,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PersonalScoreDetailPortlet extends MVCPortlet{
	@Reference 
	PartyBranchService service;
	
	Logger logger = Logger.getLogger(PersonalScoreDetailPortlet.class);
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
//		try {
//			HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
//			String meetingId = PortalUtil.getOriginalServletRequest(request).getParameter("meetingId");
//			String userId = PortalUtil.getOriginalServletRequest(request).getParameter("userId");
////		String meetingId = ParamUtil.getString(renderRequest, "meetingId");
////		logger.info("meetingId is" + meetingId);
////		String userId = ParamUtil.getString(renderRequest, "userId");
////		logger.info("userId is" + userId);
//			meetingId = (StringUtils.isEmpty(meetingId)) ? "26904396-429e-403f-9eff-19455508b7f4":meetingId;
//			userId = (StringUtils.isEmpty(userId)) ? "222222":userId;
//			JSONObject meetingComment = null;
//			meetingComment = new JSONObject(service.findPersonalMeetingComment(meetingId, userId).get(0));
//			renderRequest.setAttribute("comment", meetingComment);
//			logger.info("meetingComment :" + meetingComment);
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//		}
		
		super.doView(renderRequest, renderResponse);
	}

}
