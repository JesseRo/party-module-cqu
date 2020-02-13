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
		
		//	window.location.href='/addperson?oagName'+oagName+'&orgId='+orgId+'&oagName='+oagName; 
		HttpServletRequest request=PortalUtil.getHttpServletRequest(renderRequest);
		HttpServletRequest oriRequest = PortalUtil.getOriginalServletRequest(request);
		String orgName=PortalUtil.getOriginalServletRequest(request).getParameter("orgName");
		String orgId=PortalUtil.getOriginalServletRequest(request).getParameter("orgId");
		String seconedName=PortalUtil.getOriginalServletRequest(request).getParameter("seconedName");
		String userId=PortalUtil.getOriginalServletRequest(request).getParameter("userId");
		List<Map<String, Object>> jobs =memberDao.findMemeberJob();
		List<Hg_Value_Attribute_Info> positior=server.positior();
		List<Hg_Value_Attribute_Info> room=server.room();
		List<String> po=new ArrayList<>();
		for (int i = 0; i < positior.size(); i++) {
			Hg_Value_Attribute_Info value=positior.get(i);
			String p=value.getResources_value();
			po.add(p);
		}
		List<String> ro=new ArrayList<>();
		for (int i = 0; i < room.size(); i++) {
			Hg_Value_Attribute_Info value=room.get(i);
			String p=value.getResources_value();
			ro.add(p);
		}
		String state="";
		String sessionId=renderRequest.getRequestedSessionId();
		String role=(String) SessionManager.getAttribute(sessionId, "role");
		if (!StringUtils.isEmpty(userId)) {
			List<Map<String, Object>> list = orgDao.findPersonByuserId(userId);
			if (list!=null&&list.size()>0) {
				User user = UserDao.findUserByEthnicity(userId);
				 list.get(0).put("email", user.getUser_mailbox());
				renderRequest.setAttribute("info", list.get(0));
			}
			orgName +=">编辑人员";
			state = "edit";
			orgId = (String)list.get(0).get("member_org");
		}else {
			orgName +=">新增人员";
			state = "add";
		}
		renderRequest.setAttribute("portlet_name", "party");
		renderRequest.setAttribute("state", state);
		renderRequest.setAttribute("orgName", orgName);
		renderRequest.setAttribute("orgId", orgId);
		renderRequest.setAttribute("seconedName", seconedName);
		renderRequest.setAttribute("jobs", JSON.toJSON(jobs));
		renderRequest.setAttribute("positior",po);
		renderRequest.setAttribute("room",ro);
		renderRequest.setAttribute("role",role);
		renderRequest.setAttribute("units", unitDao.findAll());
		String uuid=UUID.randomUUID().toString();
		SessionManager.setAttribute(sessionId, "addperson-formId", uuid);
		renderRequest.setAttribute("addpersonformid",uuid);
		super.doView(renderRequest, renderResponse); 
	}

}