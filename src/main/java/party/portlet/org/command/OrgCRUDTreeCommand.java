package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import hg.party.entity.party.TreeNode;
import hg.party.server.organization.OrgService;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.PrintWriter;
import java.util.List;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgCRUD,
				"mvc.command.name=/org/tree"
	    },
	    service = MVCResourceCommand.class
)
public class OrgCRUDTreeCommand implements MVCResourceCommand{
	 Logger log = Logger.getLogger(OrgCRUDTreeCommand.class);
	@Reference
	private OrgService orgService;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

		try {
			PrintWriter printWriter=resourceResponse.getWriter();
			List<TreeNode> treeNodeList = orgService.getTreeData();
			printWriter.write(JSON.toJSONString(treeNodeList));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
}
