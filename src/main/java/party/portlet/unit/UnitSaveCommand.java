package party.portlet.unit;

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
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.entity.Retention;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.UnitNewPortlet,
				"mvc.command.name=/unit/save"
	    },
	    service = MVCResourceCommand.class
)
public class UnitSaveCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;
	@Reference
	private OrgDao orgDao;

	@Reference
	private UnitDao unitDao;


	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String sessionId = resourceRequest.getRequestedSessionId();
		String orgId = (String)SessionManager.getAttribute(sessionId, "orgId");
		String userId = (String) SessionManager.getAttribute(sessionId, "userName");
		Unit unit;
		int id = ParamUtil.getInteger(resourceRequest, "unitId");
		if (id <= 0){
			unit = new Unit();
		}else {
			unit = unitDao.findById(id);
		}
		String name = ParamUtil.getString(resourceRequest, "unitName");

		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		Gson gson = new Gson();
		try {
			unit.setUnit_name(name);
			unit.setUpdate_member_id(userId);
			unit.setUpdate_time(new Timestamp(System.currentTimeMillis()));
			unitDao.saveOrUpdate(unit);
			res.getWriter().write(gson.toJson(JsonResponse.Success()));
		} catch (Exception e) {
			try {
				e.printStackTrace();
				res.getWriter().write(gson.toJson(new JsonResponse(false, "未知错误", null)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

}
