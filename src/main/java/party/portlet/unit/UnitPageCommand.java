package party.portlet.unit;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
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
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;

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
				"javax.portlet.name=" + PartyPortletKeys.UnitPortlet,
				"mvc.command.name=/unit/page"
	    },
	    service = MVCResourceCommand.class
)
public class UnitPageCommand implements MVCResourceCommand {

	@Reference
	private UnitDao unitDao;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String orgId = (String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");

		int page = ParamUtil.getInteger(resourceRequest, "page");
		int size = ParamUtil.getInteger(resourceRequest, "limit");
		PostgresqlQueryResult<Map<String, Object>> data = unitDao.findPage(page,size);

		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		try {
			JsonPageResponse jsonPageResponse = new JsonPageResponse();
			if (data != null){
				jsonPageResponse.setCode(0);
				jsonPageResponse.setCount(data.getTotalPage() * size);
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
