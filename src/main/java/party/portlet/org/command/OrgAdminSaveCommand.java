package party.portlet.org.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"mvc.command.name=/org/adminSave"
	    },
	    service = MVCResourceCommand.class
)
public class OrgAdminSaveCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;
	@Reference
	private OrgDao orgDao;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		orgId = HtmlUtil.escape(orgId);
		String adminStr = ParamUtil.getString(resourceRequest, "admin");
		adminStr = HtmlUtil.escape(adminStr);
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		Gson gson = new Gson();
		try {
 			if ( ! StringUtils.isEmpty(orgId)){
				boolean suc;
				if (StringUtils.isEmpty(adminStr)){
					suc = orgDao.changeAdmin(orgId );
				}else {
					String[] admins = adminStr.split(",");
					suc = orgDao.changeAdmin(orgId, admins);
				}
				if (suc){
					res.getWriter().write(gson.toJson(JsonResponse.Success("保存成功！")));
				}else {
					res.getWriter().write(gson.toJson(JsonResponse.Failure("保存失败..")));
				}
			}
		} catch (Exception e) {
			try {
				e.printStackTrace();
				res.getWriter().write(gson.toJson(JsonResponse.Failure("未知错误..")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

}
