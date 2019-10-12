package party.portlet.form.command;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import party.constants.PartyPortletKeys;
import party.portlet.form.FormDesign;
import party.portlet.form.FormDesignDao;

@Component(
		immediate = true,
		property = {
		"javax.portlet.name=" + PartyPortletKeys.Form,
		"mvc.command.name=/form-configure/render"
		},
		service = MVCRenderCommand.class
	)
public class FormConfigureRenderCommand implements MVCRenderCommand {
	@Reference
	private FormDesignDao formDesignDao;
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
		HttpServletRequest originalRequest = PortalUtil.getOriginalServletRequest(request);
		String designId = originalRequest.getParameter("designId");
		designId = HtmlUtil.escape(designId);
		FormDesign formDesign = formDesignDao.findOne("form-configure");
		
		FormDesign formValue;
		if(!StringUtils.isEmpty(designId)){
			formValue = formDesignDao.findOne(designId);
		}else {
			formValue = new FormDesign();
			formValue.setDesign("{variables: [], columns: []}");
		}
		renderRequest.setAttribute("design", formDesign.getDesign());
		renderRequest.setAttribute("submitCommand", "/form/submit"); 

		renderRequest.setAttribute("formValue", formValue.getDesign());
		renderRequest.setAttribute("baseValue", new Gson().toJson(formValue));
		
		return "/jsp/form/configure.jsp";
	}
	
}