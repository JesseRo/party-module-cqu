package hg.party.command.organization;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.server.party.PartyMeetingPlanInfoService;
import party.constants.PartyPortletKeys;
@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
			"mvc.command.name=/hg/findCheckState"
	    },
	    service = MVCResourceCommand.class
)
/**
 *
 */
public class FindCheckStateCommend implements MVCResourceCommand{
	 @Reference
	 private PartyMeetingPlanInfoService partyMeetingPlanInfoService;
	 
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		int id = ParamUtil.getInteger(resourceRequest, "id");
        Map<String, Object> map=new HashMap<>();
	    PrintWriter printWriter=null;
	    try {
	    	List<Map<String, Object>> list= partyMeetingPlanInfoService.findChecKStateById(id);
	    	printWriter=resourceResponse.getWriter();
			if (list!=null&&list.size()>0) {
				map.put("state", true);
				map.put("messages", list.size());
				printWriter.write(JSON.toJSONString(map));
			}else {
				map.put("state", false);
				printWriter.write(JSON.toJSONString(map));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
