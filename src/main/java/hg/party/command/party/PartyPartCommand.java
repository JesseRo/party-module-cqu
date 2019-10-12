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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.entity.organization.Organization;
import hg.party.server.party.PartyOrgServer;
import party.constants.PartyPortletKeys;
/**
 * 分配command
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PartyMeetingPlan,
			"mvc.command.name=/PartyPartCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyPartCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(PartyPartCommand.class);
	@Reference
	private PartyOrgServer partyOrgServer;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		String org_id = ParamUtil.getString(resourceRequest, "organization_id");
		org_id = HtmlUtil.escape(org_id);
		List<Organization> listOrg = null;
		
		try {
			if(!"".equals(org_id) && null != org_id){
				listOrg = partyOrgServer.findByOrgId(org_id);
			}
			logger.info("org_id2==="+org_id);
		
		   PrintWriter printWriter=resourceResponse.getWriter();
		   printWriter.write(JSON.toJSONString(listOrg));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}


}