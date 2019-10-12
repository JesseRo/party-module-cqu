package hg.party.command.secondCommittee;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.server.secondCommittee.SecondCommitteeService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.PrintWriter;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.SeocndCommitteeArrangeVenue,
			"mvc.command.name=/hg/checkUniquePlace"
	    },
	    service = MVCResourceCommand.class
)
public class CheckUniquePlace implements MVCResourceCommand {
	@Reference
    SecondCommitteeService secondCommitteeService;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String place = ParamUtil.getString(resourceRequest, "pstr");
		place = HtmlUtil.escape(place);
		PrintWriter printWriter=null;
		String sessionId=(String)resourceRequest.getRequestedSessionId();
		
		try {
			printWriter=resourceResponse.getWriter();
		    String orgId = SessionManager.getAttribute(sessionId, "department").toString();
			Boolean placeExist = secondCommitteeService.isPlaceExist(place,orgId);
			String message = "";
			if (placeExist) {
				message = "地点已存在，请重新录入。";
				printWriter.write(message);
			}else{
				message = "当前地点可用。";
				printWriter.write(message);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
