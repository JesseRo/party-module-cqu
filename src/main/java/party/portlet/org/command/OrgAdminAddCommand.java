package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.entity.organization.Organization;
import hg.party.server.login.UserService;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"mvc.command.name=/org/adminAdd"
	    },
	    service = MVCResourceCommand.class
)
public class OrgAdminAddCommand implements MVCResourceCommand {
	@Reference
	private OrgDao orgDao;

	@Reference
	private UserService userService;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		orgId = HtmlUtil.escape(orgId);
		String userId = ParamUtil.getString(resourceRequest, "userId");
		userId = HtmlUtil.escape(userId);
		PrintWriter printWriter = null;
		try {
			printWriter = resourceResponse.getWriter();
			boolean suc;
			if (!StringUtils.isEmpty(userId)){
				Organization organization = orgDao.findByOrgId(orgId);
				Boolean  isUpdate = true;
				Organization adminOrg = orgDao.findAdminOrg(userId, PartyOrgAdminTypeEnum.getEnum(organization.getOrg_type()));
				if(adminOrg !=null){
					if(!adminOrg.getOrg_id().equals(orgId)){
						isUpdate = false;
						User user = userService.findByUserId(userId);
						printWriter.write(JSON.toJSONString(ResultUtil.fail(user.getUser_name()+" 不能同时管理两个同级组织..")));
					}
				}
				if(isUpdate == true){
					suc = orgDao.addAdmin(organization, userId);
					if (suc){
						printWriter.write(JSON.toJSONString(ResultUtil.success("保存成功！")));
					}else {
						printWriter.write(JSON.toJSONString(ResultUtil.fail("保存失败..")));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
