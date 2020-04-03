package party.portlet.org;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.Hg_Value_Attribute_Info;
import hg.party.server.dwonlistserver.DownListServer;
import party.constants.PartyConst;
import party.constants.PartyPortletKeys;
import party.portlet.unit.UnitDao;

/**
 * @author Jesse
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=组织人员添加",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/jsp/member/addPerson.jsp",
		"com.liferay.portlet.requires-namespaced-parameters=false",

		"javax.portlet.name=" + PartyPortletKeys.PersonAddPortlet,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class PersonAddPortlet extends MVCPortlet {
	@Reference
	private OrgDao orgDao;
	@Reference
	 private UserDao UserDao;
	@Reference
	private MemberDao memberDao;
	@Reference
	private DownListServer server;
	@Reference
	private UnitDao unitDao;
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		HttpServletRequest request=PortalUtil.getHttpServletRequest(renderRequest);
		String orgId=PortalUtil.getOriginalServletRequest(request).getParameter("orgId");
		String userId=PortalUtil.getOriginalServletRequest(request).getParameter("userId");
		if (!StringUtils.isEmpty(userId)) {
			List<Map<String, Object>> list = orgDao.findPersonByuserId(userId);
			if (list!=null&&list.size()>0) {
				User user = UserDao.findUserByEthnicity(userId);
				list.get(0).put("email", user.getUser_mailbox());
				renderRequest.setAttribute("info", list.get(0));
			}
			orgId = (String)list.get(0).get("member_org");
		}
		renderRequest.setAttribute("orgId", orgId);
		renderRequest.setAttribute("userId", userId);
		renderRequest.setAttribute("jobArr", PartyConst.JOBS);
		renderRequest.setAttribute("nationalArr", PartyConst.NATIONAL);
		renderRequest.setAttribute("units", unitDao.findAll());
		super.doView(renderRequest, renderResponse); 
	}

}