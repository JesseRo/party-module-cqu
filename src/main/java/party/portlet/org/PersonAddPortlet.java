package party.portlet.org;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.dao.org.OrgDao;
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
	private UnitDao unitDao;
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		HttpServletRequest request=PortalUtil.getHttpServletRequest(renderRequest);
		String orgId=PortalUtil.getOriginalServletRequest(request).getParameter("orgId");
		String userId=PortalUtil.getOriginalServletRequest(request).getParameter("userId");
		if (!StringUtils.isEmpty(userId)) {//用户id存在为修改用户信息
			List<Map<String, Object>> list = orgDao.findPersonByUserId(userId);
			if (list!=null&&list.size()>0) {
				renderRequest.setAttribute("info", list.get(0));
			}
			orgId = (String)list.get(0).get("member_org");
		}
		Map<String, Object> organization = orgDao.findOrgAndPathByOrgId(orgId);
		renderRequest.setAttribute("organization", organization);
		renderRequest.setAttribute("orgId", orgId);
		renderRequest.setAttribute("userId", userId);
		renderRequest.setAttribute("jobArr", PartyConst.JOBS);
		renderRequest.setAttribute("nationalArr", PartyConst.NATIONAL);
		renderRequest.setAttribute("units", unitDao.findAll());
		super.doView(renderRequest, renderResponse); 
	}

}