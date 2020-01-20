package party.portlet.org.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"javax.portlet.name=" + PartyPortletKeys.Form,
				"mvc.command.name=/org/memberGroup"
	    },
	    service = MVCResourceCommand.class
)
public class OrgMemberCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;
	@Reference
	private OrgDao orgDao;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		Gson gson = new Gson();
		try {
			List<Organization> organizations = orgDao.findTree(orgId, true, false);
			Organization root = organizations.stream().filter(p->p.getOrg_id().equals(orgId)).findFirst().get();
			List<String> orgIds = organizations.stream().map(Organization::getOrg_id).collect(Collectors.toList());
			List<Member> members = memberDao.findByOrg(orgIds);
			Map<String, List<Member>> memberGroup;
			switch (root.getOrg_type()) {
				case ConstantsKey.ORG_TYPE_ROOT:
					memberGroup = members.stream().collect(Collectors.groupingBy(Member::getMember_party_committee));
					break;
				case ConstantsKey.ORG_TYPE_SECONDARY:
				case ConstantsKey.ORG_TYPE_BRANCH:
					memberGroup = members.stream().collect(Collectors.groupingBy(Member::getMember_org));
					for (Map.Entry<String, List<Member>> entry: memberGroup.entrySet()){
						Organization org = organizations.stream().filter(p->p.getOrg_id().equals(entry.getKey())).findFirst().get();
						entry.getValue().forEach(p->p.setMember_org(org.getOrg_name()));
					}
					memberGroup = members.stream().collect(Collectors.groupingBy(Member::getMember_org));
					break;
				default:
					throw new RuntimeException();
			}
			List<String> admins = orgDao.findAdminByOrg(orgId);
			Map<String, Object> data = new HashMap<>();
			data.put("admins", admins);
			data.put("candidates", memberGroup);
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
