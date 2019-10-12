package hg.party.command.organization;

import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.organization.GraftService;
import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Graft,
			"mvc.command.name=/hg/deleteGrafts"
	    },
	    service = MVCResourceCommand.class
)
public class DeleteGraft implements MVCResourceCommand{
    @Reference GraftService service;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String resourcesId=ParamUtil.getString(resourceRequest, "resourcesId");
		resourcesId = HtmlUtil.escape(resourcesId);
		System.out.println(resourcesId);
		int n=0;
		PrintWriter printWriter=null;
		try {
			 printWriter=resourceResponse.getWriter();
			if (resourcesId!=null&&!resourcesId.trim().equals("")) {
				String[] str=resourcesId.split(",");
				if (str.length>1) {
					for (int i = 0; i < str.length; i++) {
					 n= service.deleteGraft(str[i]);
					}
				}else{
					n= service.deleteGraft(resourcesId);
				}
			}
			
			if (n==1) {
				printWriter.write("succee");
			}else{
				printWriter.write("fail");}
			
		  } catch (Exception e){
		  }
		     return false;
	}

}
