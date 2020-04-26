package hg.party.command.PartyBranch;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.partyBranch.PartyBranchService;
import hg.util.ConstantsKey;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
				"javax.portlet.name=" + PartyPortletKeys.NewPlan,
				"mvc.command.name=/hg/getPlace"
	    },
	    service = MVCResourceCommand.class
)
public class TaskPlace implements MVCResourceCommand{
	PartyBranchService service=new PartyBranchService();
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		String start = ParamUtil.getString(resourceRequest, "start");
		String last = ParamUtil.getString(resourceRequest, "last");
		start = HtmlUtil.escape(start);
		last = HtmlUtil.escape(last);
		
		String role=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "role");
		List<Map<String, Object>> places=new ArrayList<>();
		if (role.equals(ConstantsKey.ORG_PARTY)) {
			places = service.getPlace();
		}else if (role.equals(ConstantsKey.BRANCH_PARTY)) {
			String orgId=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
			places=service.getPlace(service.getParentId(orgId));
		}else if (role.equals(ConstantsKey.SECOND_PARTY)) {
			String orgId=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
			places=service.getPlace(orgId);
		}
		 
		LocalDateTime end;
		List<String> selectedPlaces;
		try {
				end = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			try {
				long lastMinutes = Long.valueOf(last);
				end = end.plusMinutes(lastMinutes);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			selectedPlaces = service.getPlace(start, end);
			
		} catch (DateTimeParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			selectedPlaces = null;
		}
		if (selectedPlaces != null) {
			for(Map<String, Object> place : places){
				if (selectedPlaces.contains(place.get("place_id"))) {
					place.put("selected", true);
				}
			}
		}
		try{
			PrintWriter printWriter = resourceResponse.getWriter();;
			printWriter.write(JSON.toJSONString(places));
			printWriter.flush();
			printWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
