package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.TreeNode;
import hg.party.server.organization.OrgService;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgCRUD,
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"javax.portlet.name=" + PartyPortletKeys.Org,
				"javax.portlet.name=" + PartyPortletKeys.OrganizationStatistics,
				"javax.portlet.name=" + PartyPortletKeys.MeetingStatistics,
				"mvc.command.name=/org/tree"
	    },
	    service = MVCResourceCommand.class
)
public class OrgTreeCommand implements MVCResourceCommand{
	 Logger log = Logger.getLogger(OrgTreeCommand.class);
	@Reference
	private OrgService orgService;


	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

		try {
			PrintWriter printWriter=resourceResponse.getWriter();
			Boolean isFilter = ParamUtil.getBoolean(resourceRequest, "isFilter");
			List<TreeNode> treeNodeList = new ArrayList<>();
			if(isFilter){//是否展示无权限组织树节点
				String sessionId=resourceRequest.getRequestedSessionId();
				Object roleObj = SessionManager.getAttribute(sessionId, "role");
				String userId = (String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");
				if(roleObj != null){
					PartyOrgAdminTypeEnum partyOrgAdminTypeEnum =  PartyOrgAdminTypeEnum.getEnumByRole(roleObj.toString());
					Organization organization = orgService.findAdminOrg(userId,partyOrgAdminTypeEnum);
					if(organization!=null){
						treeNodeList = orgService.getTreeData(organization);
					}
				}
			}else{
				treeNodeList = orgService.getTreeData(null);
			}
			printWriter.write(JSON.toJSONString(treeNodeList));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
}
