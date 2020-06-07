package hg.party.command.party;


import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.server.organization.GraftService;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 查询本组织发布的计划
 */
@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.AlreadyPublic,
				"javax.portlet.name=" + PartyPortletKeys.MeetingNotice,
				"mvc.command.name=/party/inform/page"
		},
		service = MVCResourceCommand.class
	)

public class InformPageCommand implements MVCResourceCommand {
	@Reference
	GraftService graftService;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		int page = ParamUtil.getInteger(resourceRequest, "page");
		int size = ParamUtil.getInteger(resourceRequest, "limit");
		String dateType = ParamUtil.getString(resourceRequest, "dateType");
		String keyword = HtmlUtil.escape(ParamUtil.getString(resourceRequest, "keyword"));
		String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		Boolean isSender = ParamUtil.getBoolean(resourceRequest, "isSender");
		try {
			PostgresqlPageResult<Map<String, Object>> data;
			if(isSender){
				if (StringUtils.isEmpty(keyword)){
					data = graftService.searchSendInformPage(page, size,dateType,orgId,null);
				}else {
					data = graftService.searchSendInformPage(page, size,dateType,orgId,keyword);
				}
			}else{
				if (StringUtils.isEmpty(keyword)){
					data = graftService.searchPage(page, size,dateType,orgId,1,null);
				}else {
					data = graftService.searchPage(page, size,dateType,orgId,1,keyword);
				}
			}
			Gson gson = new Gson();
			res.getWriter().write(gson.toJson(data.toJsonPageResponse()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
