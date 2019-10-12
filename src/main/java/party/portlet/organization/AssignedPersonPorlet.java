package party.portlet.organization;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.server.organization.AssignedPersonService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=组织部-指派人员",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/organization/assignedPerson.jsp",
			"javax.portlet.name=" + PartyPortletKeys.AssignedPersonPorlet,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class AssignedPersonPorlet extends MVCPortlet{
	AssignedPersonService service=new AssignedPersonService();
	@Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
    		throws IOException, PortletException {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
		String resourceId = PortalUtil.getOriginalServletRequest(request).getParameter("resourceId");
		resourceId = HtmlUtil.escape(resourceId);
		List<Map<String, Object>> list=service.findDtail(resourceId);
    	renderRequest.setAttribute("list", list);
    	super.doView(renderRequest, renderResponse);
    }
}
