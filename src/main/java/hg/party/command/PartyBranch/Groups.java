package hg.party.command.PartyBranch;

import java.awt.List;
import java.io.PrintWriter;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
			"mvc.command.name=/hg/getGroup"
	    },
	    service = MVCResourceCommand.class
)
public class Groups implements MVCResourceCommand{
	PartyBranchService service=new PartyBranchService();
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String orgId=ParamUtil.getString(resourceRequest, "orgId");
		orgId = HtmlUtil.escape(orgId);
		if (StringUtils.isEmpty(orgId)) {
	           orgId=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		}
		try {
			PrintWriter printWriter=resourceResponse.getWriter();
			String list=service.getGroups(orgId);
			printWriter.write(service.getGroups(orgId));
		} catch (Exception e) {
			
		}
		return false;
	}

}
