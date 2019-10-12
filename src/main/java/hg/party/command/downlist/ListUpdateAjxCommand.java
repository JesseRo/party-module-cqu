package hg.party.command.downlist;


import java.io.PrintWriter;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.party.Hg_Value_Attribute_Info;
import hg.party.server.dwonlistserver.DownListServer;
import party.constants.PartyPortletKeys;
/**
 * 下拉菜单 新增、修改保存ajx提交
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.DownList,
			"mvc.command.name=/ListUpdateAjxCommand"
	    },
	    service = MVCResourceCommand.class
)
public class ListUpdateAjxCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(ListUpdateAjxCommand.class);
	
	@Reference
	private DownListServer server;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		try {
			logger.info("ListUpdateAjxCommand render.........");
			String sessionId=resourceRequest.getRequestedSessionId();
			String orgId = SessionManager.getAttribute(sessionId, "department").toString();
			String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
			String Id = ParamUtil.getString(resourceRequest, "id");
			String resources_key = ParamUtil.getString(resourceRequest,"key");
			String resources_value = ParamUtil.getString(resourceRequest,"val");
			String resources_type = ParamUtil.getString(resourceRequest,"type");
			String remark = ParamUtil.getString(resourceRequest,"remark");
			
			Id = HtmlUtil.escape(Id);
			resources_key = HtmlUtil.escape(resources_key);
			resources_value = HtmlUtil.escape(resources_value);
			resources_type = HtmlUtil.escape(resources_type);
			remark = HtmlUtil.escape(remark);
			
			Hg_Value_Attribute_Info asset_List = null;
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
			
			List<Hg_Value_Attribute_Info> num = server.repeat(resources_value,resources_type);
			if(num.size() >= 1){
				PrintWriter printWriter=resourceResponse.getWriter();
				printWriter.write(JSON.toJSONString("数据重复，保存失败！"));
			}else{
				server.SaveOrUpdate(asset_List);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}


}