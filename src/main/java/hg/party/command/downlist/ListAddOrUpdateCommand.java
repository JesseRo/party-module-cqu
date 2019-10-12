package hg.party.command.downlist;


import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.party.Hg_Value_Attribute_Info;
import hg.party.server.dwonlistserver.DownListServer;
import party.constants.PartyPortletKeys;

/**
 * 
 * 新增、修改保存
 */
@Component(
	immediate = true,
	property = {
			"javax.portlet.name=" + PartyPortletKeys.DownList,
			"mvc.command.name=/asset/addOrUpdateDeviceAction"
    },
    service = MVCActionCommand.class
)
public class ListAddOrUpdateCommand extends BaseMVCActionCommand{
	Logger logger = Logger.getLogger(ListAddOrUpdateCommand.class);
	@Reference
	private DownListServer server;
	
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) {
		try {
			logger.info("ListAddOrUpdateCommand render.........");
			String sessionId=actionRequest.getRequestedSessionId();
		   String orgId =	SessionManager.getAttribute(sessionId, "department").toString();
		   String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
			Hg_Value_Attribute_Info asset_List = null;
			String Id = ParamUtil.getString(actionRequest,"id");
			Id = HtmlUtil.escape(Id);
			String resources_key = ParamUtil.getString(actionRequest,"resources_key");
			resources_key = HtmlUtil.escape(resources_key);
			String resources_value = ParamUtil.getString(actionRequest,"resources_value");
			resources_value = HtmlUtil.escape(resources_value);
			String resources_type = ParamUtil.getString(actionRequest,"resources_type");
			resources_type = HtmlUtil.escape(resources_type);
			String remark = ParamUtil.getString(actionRequest,"remark");
			remark = HtmlUtil.escape(remark);
			
			if(!"".equals(Id) && null != Id){
				int id = Integer.parseInt(Id);
				asset_List  = server.findById(id);
			}
			
			if(asset_List == null){
				asset_List = new Hg_Value_Attribute_Info();
			}
//			asset_List.setId(id);
			asset_List.setResources_key(resources_key);
			asset_List.setResources_value(resources_value);
			asset_List.setResources_type(resources_type);
			asset_List.setRemark(remark);
			asset_List.setOrg_id(orgId);
			asset_List.setUser_id(userId);
			server.SaveOrUpdate(asset_List);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


