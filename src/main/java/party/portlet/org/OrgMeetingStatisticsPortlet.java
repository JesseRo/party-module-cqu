package party.portlet.org;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

/**
 * @author Jesse
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=组织生活开展统计列表",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/jsp/member/meetingStatistics.jsp",
		"com.liferay.portlet.requires-namespaced-parameters=false",

		"javax.portlet.name=" + PartyPortletKeys.MeetingStatistics,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class OrgMeetingStatisticsPortlet extends MVCPortlet {

	@Reference
	OrgDao orgDao;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String sessionId = renderRequest.getRequestedSessionId();
	    String orgId = (String)SessionManager.getAttribute(sessionId, "department");
		renderRequest.setAttribute("org", orgDao.findByOrgId(orgId));
		super.doView(renderRequest, renderResponse); 
	}

}