package party.portlet.form;
/**
 * 閸愬懎顔愰幗妯款洣閿涳拷 
 * 閸掓稑缂撴禍锟� 閵嗭拷閿涳拷 Zhong LiMei
 * 閸掓稑缂撻弮銉︽埂閿涳拷 2017楠烇拷10閺堬拷30閺冦儰绗呴崡锟�3:35:28
 */



import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.PortalUtil;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.Form,
			"mvc.command.name=/form-configure/set"
	    },
	    service = MVCActionCommand.class
	)
public class SetDesignCommand extends BaseMVCActionCommand  {
	
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(actionRequest);
		HttpServletRequest originalRequest = PortalUtil.getOriginalServletRequest(request);
		String designId = originalRequest.getParameter("designId");
		
		PortletPreferences preferences = actionRequest.getPreferences();
		preferences.setValue("designId", designId);
		preferences.store();
	}

}

