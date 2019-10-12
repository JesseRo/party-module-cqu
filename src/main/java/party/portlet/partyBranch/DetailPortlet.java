package party.portlet.partyBranch;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=党支部-详情页面",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/partyBranch/detial.jsp",
			"javax.portlet.name=" + PartyPortletKeys.DetailPortlet,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class DetailPortlet extends MVCPortlet{
	@Reference 
	PartyBranchService service;
	@Reference
	OrgDao orgDao;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
		String informId = PortalUtil.getOriginalServletRequest(request).getParameter("informId");
		informId = HtmlUtil.escape(informId);
		String orgId = PortalUtil.getOriginalServletRequest(request).getParameter("orgId");
		orgId = HtmlUtil.escape(orgId);
		
		if (!StringUtils.isEmpty(orgId)) {
			Organization org = orgDao.findByOrgId(orgId);
			renderRequest.setAttribute("org", org.getOrg_type());
		}
		/*
		if (StringUtils.isEmpty(informId)) {
			informId="6423ce91-e350-447e-950a-825fdf36f103"; 
		}*/
		List<Map<String, Object>> list=service.findDetail(informId, orgId);
		
		String read = list.get(0).get("read_status")+"";
		if(read.equals("未读")){
			service.findByMeetingStatus(informId,orgId);
		}
		
		renderRequest.setAttribute("detail", list.get(0));
		super.doView(renderRequest, renderResponse);
	}

}
