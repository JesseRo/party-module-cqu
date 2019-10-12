package party.portlet.org;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.util.ConstantsKey;
import party.constants.PartyPortletKeys;

/**
 * @author Jesse
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=组织",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/jsp/member/view.jsp",
		"com.liferay.portlet.requires-namespaced-parameters=false",

		"javax.portlet.name=" + PartyPortletKeys.Org,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class OrgPortlet extends MVCPortlet {

	@Reference
	OrgDao orgDao;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		HttpServletRequest request=PortalUtil.getHttpServletRequest(renderRequest);
		String org=PortalUtil.getOriginalServletRequest(request).getParameter("org");
		String sessionId = renderRequest.getRequestedSessionId();
	    String orgId = (String)SessionManager.getAttribute(sessionId, "department");
	    String orgType = (String)SessionManager.getAttribute(sessionId, "orgType");
	    Organization root = orgDao.findRoot();
	    
		List<Organization> secondaries = orgDao.findSecondary();
		List<Organization> branches = orgDao.findBranch();
		List<Organization> historicSecondaries = orgDao.findHistoricSecondary();
		List<Organization> historicBranches = orgDao.findHistoricBranch();
		if (ConstantsKey.ORG_TYPE_BRANCH.equals(orgType)) {
			secondaries = orgDao.findbranchSecondary(orgId);
			branches = orgDao.findBranch(orgId);
		}else if (ConstantsKey.ORG_TYPE_ROOT.equals(orgType)) {
			
		}else if (ConstantsKey.ORG_TYPE_SECONDARY.equals(orgType)) {
			 secondaries = orgDao.findSecondary(orgId);
			 branches = orgDao.findsecodBranch(orgId);
			
		}
		Map<String,List<Organization>> brunchGroups = branches.stream()
				.collect(Collectors.groupingBy(Organization::getOrg_parent, Collectors.toList()));
		Map<String,List<Organization>> historicBrunchGroups = historicBranches.stream()
				.collect(Collectors.groupingBy(Organization::getOrg_parent, Collectors.toList()));
		//Organization root = orgDao.findRoot();
		Gson gson = new Gson();
		renderRequest.setAttribute("secondaries", gson.toJson(secondaries));
		renderRequest.setAttribute("branches", gson.toJson(brunchGroups));
		renderRequest.setAttribute("historicSecondaries", gson.toJson(historicSecondaries));
		renderRequest.setAttribute("historicBranches", gson.toJson(historicBrunchGroups));
		renderRequest.setAttribute("root",  gson.toJson(root));
		renderRequest.setAttribute("org_type", orgType);
		renderRequest.setAttribute("org", org);
		super.doView(renderRequest, renderResponse); 
	}

}