package hg.party.command.downlist;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.dwonlistserver.DownListServer;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.DownList,
				"mvc.command.name=/asset/delDevice"
	    },
	    service = MVCActionCommand.class
	)
public class ListDeleteCommand extends BaseMVCActionCommand{
	@Reference
	private DownListServer listServer;
	Logger logger = Logger.getLogger(ListDeleteCommand.class);
	
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		logger.info("ListDeleteCommand doProcessAction........");
		int id = Integer.parseInt(ParamUtil.getString(actionRequest,"id"));
		//Hg_Value_Attribute_Info asset_Attributes = listServer.findByResourcesId(resources_id);
//		if(null != id){
//			int id = listServer.findByResourcesId(resources_id).getId();
			listServer.deleteById(id);
//		}
	}
}
