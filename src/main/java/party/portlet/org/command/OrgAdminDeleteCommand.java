package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.dao.org.OrgDao;

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


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"mvc.command.name=/org/adminDelete"
	    },
	    service = MVCResourceCommand.class
)
public class OrgAdminDeleteCommand implements MVCResourceCommand {
	@Reference
	private OrgDao orgDao;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		orgId = HtmlUtil.escape(orgId);
		String userId = ParamUtil.getString(resourceRequest, "userId");
		userId = HtmlUtil.escape(userId);
		PrintWriter printWriter = null;
		try {
			printWriter = resourceResponse.getWriter();
			int suc;
			if (!StringUtils.isEmpty(userId)){
				suc = orgDao.deleteAdmin(orgId, userId);
				if (suc >0){
					printWriter.write(JSON.toJSONString(ResultUtil.success("删除成功！")));
				}else {
					printWriter.write(JSON.toJSONString(ResultUtil.fail("删除失败..")));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
