package hg.party.command.contentRelease;



import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.contentManagementInfo.Hg_Content_Management_Info;
import hg.party.server.contentInfo.ContentInfoServer;
import party.constants.PartyPortletKeys;
/**
 * 组织部通过发文审批
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.OrganizationReviewContent,
			"javax.portlet.name=" + PartyPortletKeys.SecondPartyReviewContent,
			"mvc.command.name=/content/agreeIt"
	    },
	    service = MVCResourceCommand.class
)
public class ContentAgreeCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(ContentAgreeCommand.class);
	
	@Reference
	private ContentInfoServer infoServer;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)throws PortletException {
		try {
			String resources_id = ParamUtil.getString(resourceRequest, "resourceId");
			String sessionID=resourceRequest.getRequestedSessionId();
			String user_name = (String)SessionManager.getAttribute(sessionID, "user_name");
			String contentPortletKey = ParamUtil.getString(resourceRequest, "contentPortletKey");
			resources_id=	HtmlUtil.escape(resources_id);
			contentPortletKey=	HtmlUtil.escape(contentPortletKey);
			Hg_Content_Management_Info info = infoServer.findByResourceId(resources_id);
			if("SecondPartyReviewContent".equals(contentPortletKey)){
				info.setApprove_state(1);
				info.setFirst_approve_id(user_name);
			}
			if("OrganizationReviewContent".equals(contentPortletKey)){
				info.setApprove_state(3);
				info.setSecond_approve_id(user_name);
			}
			infoServer.saveOrUpdate(info);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


}