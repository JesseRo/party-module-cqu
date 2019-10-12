package hg.party.command.party;


import java.io.PrintWriter;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import hg.party.entity.party.Hg_Value_Attribute_Info;
import hg.party.server.dwonlistserver.DownListServer;
import party.constants.PartyPortletKeys;
/**
 * 驳回理由command
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalBranch,
			"mvc.command.name=/PartyReasonCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyReasonCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(PartyReasonCommand.class);
	@Reference
	private DownListServer downListServer;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
//		String org_id = ParamUtil.getString(resourceRequest, "organization_id");
		List<Hg_Value_Attribute_Info> listValue = null;
		
		try {
			listValue = downListServer.reasson();
		    PrintWriter printWriter=resourceResponse.getWriter();
		    printWriter.write(JSON.toJSONString(listValue));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}


}