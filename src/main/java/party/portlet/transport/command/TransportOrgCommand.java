package party.portlet.transport.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.TransportApplyPortlet,
				"mvc.command.name=/transport/org"
	    },
	    service = MVCResourceCommand.class
)
public class TransportOrgCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;
	@Reference
	private OrgDao orgDao;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String orgId = (String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "orgId");
		Organization organization = orgDao.findByOrgId(orgId);
		if (organization.getOrg_type().equals(ConstantsKey.ORG_TYPE_BRANCH)){
			organization = orgDao.findByOrgId(organization.getOrg_parent());
		}
		Organization root = orgDao.findRoot();


		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		Gson gson = new Gson();
		try {
			List<Organization> brunchInSecondary = orgDao.findTree(organization.getOrg_id(), true, false);
			List<Organization> allOrg = orgDao.findTree(root.getOrg_id(), true, false);

			Map<Object, List<Organization>> brunchGroup = new LinkedHashMap<>();
			brunchInSecondary.stream()
					.filter(p->p.getOrg_type().equals(ConstantsKey.ORG_TYPE_SECONDARY))
					.forEach( p-> {
						List<Organization> orgs = brunchInSecondary.stream()
								.filter(b -> b.getOrg_parent().equalsIgnoreCase(p.getOrg_id())).collect(Collectors.toList());
						brunchGroup.put(p, orgs);
					});

			Map<Object, List<Organization>> allBrunchGroup = new LinkedHashMap<>();
			brunchInSecondary.stream()
					.filter(p->p.getOrg_type().equals(ConstantsKey.ORG_TYPE_SECONDARY))
					.forEach( p-> {
						List<Organization> orgs = allOrg.stream()
								.filter(b -> b.getOrg_parent().equalsIgnoreCase(p.getOrg_id())).collect(Collectors.toList());
						allBrunchGroup.put(p, orgs);
					});

			Map<String, Object> data = new HashMap<>();
			data.put("brunchInSecondary", brunchGroup);
			data.put("allOrg", allBrunchGroup);
			res.getWriter().write(gson.toJson(JsonResponse.Success(data)));
		} catch (Exception e) {
			try {
				e.printStackTrace();
				res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

}
