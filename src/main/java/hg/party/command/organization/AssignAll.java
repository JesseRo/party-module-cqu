package hg.party.command.organization;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.organization.AssignedPersonService;
import hg.party.server.organization.PublicInformationService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.AssignedPersonPorlet,
			"javax.portlet.name="+ PartyPortletKeys.PartySecondary,
			"javax.portlet.name="+ PartyPortletKeys.PartyMeetingPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
			"mvc.command.name=/hg/assignedAddPersonAll"
	    },
	    service = MVCResourceCommand.class
)
public class AssignAll implements MVCResourceCommand{
	 AssignedPersonService service=new AssignedPersonService();
	 PublicInformationService ss=new PublicInformationService();
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String ids = ParamUtil.getString(resourceRequest, "ids");
		ids = HtmlUtil.escape(ids);
		List<Map<String, Object>> assignPersons = service.findAssignPerson(0);
		String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		String orgType = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "orgType");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			String str = id[i];
			List<Map<String, Object>> list = service.getAssignPerson(str, orgType, orgId);
			if (list != null && list.size() > 0) {
				String assignId = assignPersons.get(0).get("assigne_user_id").toString();
				// String sql="update hg_party_meeting_plan_info set
				// check_person='"+assignId+"' ,task_status='"+5+"' where
				// id='"+str+"'";
				// String sqlOrg="update hg_party_meeting_plan_info set
				// check_person_org='"+assignId+"' ,task_status_org='"+5+"'
				// where id='"+id+"'";
				int n = 0;
				if ("organization".equals(orgType)) {
					// n= ss.saveAttachment(sqlOrg);
					n = ss.orgAssign(assignId, Integer.parseInt(str));
				} else {
					// n= ss.saveAttachment(sql);
					n = ss.seconedAssign(assignId, Integer.parseInt(str));
				}
				//ss.saveAttachment("update hg_party_assigne set state='1' where assigne_user_id='" + assignId + "'");
				ss.updateAssignPersonState(assignId);
			}

		}
		return false;
	}

}
