package party.portlet.form;

import party.constants.PartyPortletKeys;
import party.portlet.form.Entity.FormDefinition;
import party.portlet.form.Entity.Variable;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.persistence.PortletUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.server.partyBranch.PartyBranchService;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

/**
 * @author Jesse
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=通用表单",
		"javax.portlet.init-param.template-path=/",
		
		"javax.portlet.portlet-mode=text/html;view,edit",

		"javax.portlet.init-param.view-template=/jsp/form/view.jsp",
		"javax.portlet.init-param.edit-template=/jsp/form/edit.jsp",
		"com.liferay.portlet.requires-namespaced-parameters=false",

		"javax.portlet.name=" + PartyPortletKeys.Form,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class PartyPortlet extends MVCPortlet {
	@Reference
	private FormDesignDao formDesignDao;
	@Reference
	private PartyBranchService partyBranchService;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		String designId = renderRequest.getPreferences().getValue("designId", null);

	    HttpServletRequest request=PortalUtil.getHttpServletRequest(renderRequest);
	    HttpServletRequest orgReq = PortalUtil.getOriginalServletRequest(request);

		PortletURL navigationURL = renderResponse.createRenderURL();
		if (!StringUtils.isEmpty(designId)) {
			FormDesign formDesign = formDesignDao.findOne(designId);
			if (!StringUtils.isEmpty(formDesign.getRender_command())) {
				navigationURL.setParameter("mvcRenderCommandName", formDesign.getRender_command());
			}else{
				navigationURL.setParameter("mvcRenderCommandName", "/form/render");
			}
			FormDefinition fd = new Gson().fromJson(formDesign.getDesign(), FormDefinition.class);
			List<Variable> variables = fd.getVariables();
			StringBuilder urlParams = new StringBuilder();
			if(variables != null){
				variables.stream().forEach(p->{
					String v = orgReq.getParameter(p.getName());
					if (StringUtils.isEmpty(v)) {
						v = p.getDefaultValue();
					}
					urlParams.append("&").append(p.getName()).append("=").append(v);
					
				});
			}
		    HttpServletResponse response = PortalUtil.getHttpServletResponse(renderResponse);
		    response.sendRedirect(response.encodeRedirectURL(navigationURL.toString() + urlParams.toString()));
		}else {
			super.doView(renderRequest, renderResponse);
		}

	}

	@Override
	public void doEdit(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		renderRequest.setAttribute("titles", new String[]{"名字", "提交command", "渲染页面", "渲染command", "描述", "设计", "操作"}); 
		renderRequest.setAttribute("designs", formDesignDao.findAll()); 
		super.doEdit(renderRequest, renderResponse);
	}
}