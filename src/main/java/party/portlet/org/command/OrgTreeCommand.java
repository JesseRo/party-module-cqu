package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import hg.party.entity.organization.Organization;
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
import java.util.*;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"mvc.command.name=/org/admin/orgTree"
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
			List<Organization> organizationList = orgService.findAll();
			log.info("organizationList size :"+organizationList.size());
			List<TreeNode> treeNodeList = initOrgTreeData(organizationList,"-");
			printWriter.write(JSON.toJSONString(treeNodeList));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	private List<TreeNode> initOrgTreeData(List<Organization> organizationList,String orgParent){
		List<TreeNode> treeNodeList = new ArrayList<>();
		if(organizationList.size()>0){
			for(Organization organization:organizationList){
				String pId =  organization.getOrg_parent();
				if((orgParent==null && pId == null) ||(orgParent!=null && orgParent.equals(pId))){
					TreeNode parentNode = new TreeNode();
					parentNode.setChecked(false);
					parentNode.setId(organization.getId());
					parentNode.setName(organization.getOrg_name());
					parentNode.setOpen(false);
					parentNode.setData(organization);
					List<TreeNode> children = initOrgTreeData(organizationList,organization.getOrg_id());
					parentNode.setChildren(children);
					treeNodeList.add(parentNode);
				}

			}
		}
		return treeNodeList;
	}
	
}
