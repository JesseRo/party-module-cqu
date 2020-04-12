package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.entity.organization.Organization;
import hg.party.server.organization.OrgService;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"mvc.command.name=/org/users"
	    },
	    service = MVCResourceCommand.class
)
public class OrgUsersCommand implements MVCResourceCommand {
	@Reference
	private OrgService orgService;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		int id = ParamUtil.getInteger(resourceRequest, "id");
		PrintWriter printWriter = null;
		try {
			printWriter = resourceResponse.getWriter();
			if(!StringUtils.isEmpty(id)){
				Organization organization = orgService.findOrgById(id);
				if(organization!=null){
					List<Map<String,Object>> userList = orgService.findAllUsers(organization);
					printWriter.write(JSON.toJSONString(ResultUtil.success(userList)));
				}else{
					printWriter.write(JSON.toJSONString(ResultUtil.fail("组织不存在！")));
				}
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("组织id不能为空！")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

}
