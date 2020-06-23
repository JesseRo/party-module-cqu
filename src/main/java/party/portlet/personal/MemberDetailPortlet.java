package party.portlet.personal;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.server.member.MemberEditService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.memberEdit.MemberEdit;
import party.portlet.unit.UnitDao;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Jesse
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=党员信息",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/jsp/member/memberDetail.jsp",
		"com.liferay.portlet.requires-namespaced-parameters=false",

		"javax.portlet.name=" + PartyPortletKeys.MemberDetailPortlet,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class MemberDetailPortlet extends MVCPortlet {
	@Reference
	private OrgDao orgDao;
	@Reference
	private UnitDao unitDao;
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String userId = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest)).getParameter("userId");
		if (!StringUtils.isEmpty(userId)) {
			List<Map<String, Object>> list = orgDao.findPersonByUserId(userId);
			if (list!=null&&list.size()>0) {
				renderRequest.setAttribute("info", list.get(0));
			}
		}
		renderRequest.setAttribute("units", unitDao.findAll());
		super.doView(renderRequest, renderResponse); 
	}

}