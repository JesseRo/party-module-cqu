package party.portlet.org.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.server.organization.OrgService;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"mvc.command.name=/org/users/page"
	    },
	    service = MVCResourceCommand.class
)
public class OrgUsersPageCommand implements MVCResourceCommand {
	@Reference
	private OrgService orgService;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{

		int id = ParamUtil.getInteger(resourceRequest, "id");
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		int page = ParamUtil.getInteger(resourceRequest, "page");
		int size = ParamUtil.getInteger(resourceRequest, "limit");
		try {
			PostgresqlPageResult<Map<String, Object>> data = orgService.searchOrgUsersPage(page, size,id);
			Gson gson = new Gson();
			res.getWriter().write(gson.toJson(data.toJsonPageResponse()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
