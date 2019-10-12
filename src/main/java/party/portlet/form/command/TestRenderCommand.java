package party.portlet.form.command;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
		"javax.portlet.name=" + PartyPortletKeys.Form,
		"mvc.command.name=/form/testRender"
		},
		service = MVCRenderCommand.class
	)
public class TestRenderCommand extends FormRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		// TODO Auto-generated method stub
		String sb = ParamUtil.getString(renderRequest, "sb");
		return super.render(renderRequest, renderResponse);
	}
	
}