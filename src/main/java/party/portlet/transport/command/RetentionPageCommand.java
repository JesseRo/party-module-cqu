package party.portlet.transport.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonPageResponse;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.entity.PageQueryResult;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.TransportApproval,
				"javax.portlet.name=" + PartyPortletKeys.TransportCompleted,
				"mvc.command.name=/retention/page"
	    },
	    service = MVCResourceCommand.class
)
public class RetentionPageCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;
	@Reference
	private OrgDao orgDao;
	@Reference
	private RetentionDao retentionDao;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String orgId = (String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		Organization organization = orgDao.findByOrgId(orgId);

		int page = ParamUtil.getInteger(resourceRequest, "page");
		int size = ParamUtil.getInteger(resourceRequest, "limit");
		String name = ParamUtil.getString(resourceRequest, "memberName");
		String startDate = ParamUtil.getString(resourceRequest, "startDate");
		String endDate = ParamUtil.getString(resourceRequest, "endDate");
		boolean completed = ParamUtil.getBoolean(resourceRequest, "completed");
		PageQueryResult<Map<String, Object>> data = null;
		if (!StringUtils.isEmpty(name)){
			name = "%" + name + "%";
		}
		if (organization.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_SECONDARY)){
			data = retentionDao.findSecondaryPage(page, size, orgId, name, completed, startDate, endDate);
		}else if (organization.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_ROOT)){
			data = retentionDao.findRootPage(page, size, name, completed, startDate, endDate);
		} else if (organization.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_BRANCH)) {
			data = retentionDao.findBrunchPage(page, size, orgId, name, completed, startDate, endDate);
		} else {
			data = null;
		}

		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		try {
			JsonPageResponse jsonPageResponse = new JsonPageResponse();
			if (data != null){
				jsonPageResponse.setCode(0);
				jsonPageResponse.setCount(data.getCount());
				jsonPageResponse.setData(data.getList());
			}else {
				jsonPageResponse.setCode(0);
				jsonPageResponse.setCount(0);
				jsonPageResponse.setData(Collections.emptyList());
			}
			res.getWriter().write(gson.toJson(jsonPageResponse));
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
