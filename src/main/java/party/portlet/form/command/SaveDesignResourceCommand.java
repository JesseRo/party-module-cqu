package party.portlet.form.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.form.FormDesign;
import party.portlet.form.FormDesignDao;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.Form,
				"mvc.command.name=/form-configure/save"
	    },
	    service = MVCResourceCommand.class
)
public class SaveDesignResourceCommand implements MVCResourceCommand {
	@Reference
	private FormDesignDao formDesignDao;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String formDesignJson = ParamUtil.getString(resourceRequest, "formDesign");
		formDesignJson =	HtmlUtil.escape(formDesignJson);
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		try {
			FormDesign formDesign = new Gson().fromJson(formDesignJson, FormDesign.class);
			formDesignDao.saveOne(formDesign);
			res.getWriter().write("{\"result\": true}");
		} catch (Exception e) {
			// TODO: handle exception
			try {
				e.printStackTrace();
				res.getWriter().write("{\"result\": false}");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return false;
	}

}
