package hg.party.command.secondCommittee;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.liferay.portal.kernel.image.Ghostscript;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import party.constants.PartyPortletKeys;
import party.portlet.form.FormDesign;
import party.portlet.form.FormDesignDao;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.SeocndCommitteeToDoList,
				"mvc.command.name=/secondary/loadBranch"
	    },
	    service = MVCResourceCommand.class
)
public class LoadBranchResourceCommand implements MVCResourceCommand {
	@Reference
	private OrgDao orgDao;
	Gson gson = new Gson();

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{

		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		String sessionId = resourceRequest.getRequestedSessionId();
		String departmentId = (String)SessionManager.getAttribute(sessionId, "department");

		try {
			if(StringUtils.isEmpty(departmentId)){
				res.getWriter().write(gson.toJson(JsonResponse.Failure("未登录")));
				return false;
			}
			List<Organization> organizations = orgDao.findBranchBySecondary(departmentId);
			res.getWriter().write(gson.toJson(JsonResponse.Success(organizations)));
		} catch (Exception e) {
			// TODO: handle exception
			try {
				e.printStackTrace();
				res.getWriter().write(gson.toJson(JsonResponse.Failure("出错")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return false;
	}

}
