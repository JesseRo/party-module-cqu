package hg.party.command.contentRelease;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.attachment.AttachmentServer;
import hg.party.server.contentInfo.ContentInfoServer;
import hg.party.unity.ResourceProperties;
import party.constants.PartyPortletKeys;

/**
 * 
 * 文件名称： contentRelease<br>
 * 内容摘要： <删除content_info和content_attachment信息renderCommand><br>
 * 创建人 　： Zhang Minggang<br>
 * 创建日期： 2017年9月19日上午10:50:00<br>
 */

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
		"mvc.command.name=/info/delInfoAndAttachment"
    },
    service = MVCRenderCommand.class
)
public class DelInfoAndAttRenderCommand implements MVCRenderCommand {
	@Reference
	private ContentInfoServer infoServer;
	@Reference
	private AttachmentServer attachmentServer;
	private int pageSize=8;
	@Override
	public String render(RenderRequest req, RenderResponse resp) throws PortletException {
		String resourceId = ParamUtil.getString(req, "resourceId");
		String contentPortletKey = ParamUtil.getString(req, "contentPortletKey");
		resourceId =	HtmlUtil.escape(resourceId);
		contentPortletKey =	HtmlUtil.escape(contentPortletKey);
		req.setAttribute("contentPortletKey", contentPortletKey);
		//修改为删除状态info
		infoServer.deleteByResourceId(resourceId);
		//删除attachment
//		List<Map<String,Object>> attachments = attachmentServer.findByResourceId(resourceId);
//		attachmentServer.deleteByResourceId(resourceId);
		//删除硬盘中的文件
		//保存文件最终路径
		ResourceProperties resourceProperties = new ResourceProperties();
		Properties properties = resourceProperties.getResourceProperties();//获取配置文件
		String fileUrl = properties.getProperty("uploadPath");
//		for (Map<String, Object> map : attachments) {
//			if(null != map && null != map.get("attachment_url") && "" != map.get("attachment_url")){
//				fileUrl += map.get("attachment_url");
//				File file = new File(fileUrl);
//				FileUtil.delete(file);
//			}
//		}
		
		contentPortletKey = ParamUtil.getString(req, "contentPortletKey");
		req.setAttribute("contentPortletKey", contentPortletKey);
		String sessionId = req.getRequestedSessionId();
		String user_name = (String) SessionManager.getAttribute(sessionId, "user_name");//昵称
		String department = (String) SessionManager.getAttribute(sessionId, "department");//部门id
		req.setAttribute("user_name", user_name);
		
		String date = ParamUtil.getString(req, "date");//发布日期
		date = HtmlUtil.escape(date);
		String title = ParamUtil.getString(req, "title");//标题
		title = HtmlUtil.escape(title);
		String publisher_id = ParamUtil.getString(req, "publisher_id");//发布人
		publisher_id = HtmlUtil.escape(publisher_id);
		String approve_state = ParamUtil.getString(req, "approve_state");//审核状态
		approve_state = HtmlUtil.escape(approve_state);
		int pageNo = ParamUtil.getInteger(req, "pageNo");
		
		
		List<Map<String, Object>> list = null;
		if("ContentRelease".equals(contentPortletKey)){
			list = infoServer.contentFind(date, title, publisher_id,approve_state,department);
		}
		if("SecondPartyContentRelease".equals(contentPortletKey)){
			list= infoServer.secondPartyContentFind(date, title, publisher_id,approve_state);
		}
		if("OrganizationContentRelease".equals(contentPortletKey)){
			list= infoServer.OrganizationContentFind(date, title, publisher_id);
		}
		//获取当前页
		
		 int totalCount=list.size();
	     int totalPage=totalCount/pageSize;
	     if (totalCount%pageSize!=0) {
	    	 totalPage=totalPage+1;
		}
	    if(pageNo==0){
			pageNo = 1;//默认当前页为1
		}else if(pageNo > totalPage){
			pageNo = totalPage;
		}
		
	    List<Map<String, Object>> listResult = null;
	    if("ContentRelease".equals(contentPortletKey)){
	    	listResult = infoServer.contentFind(date, title, publisher_id ,approve_state, pageSize, (pageNo-1)*pageSize,department);
		}
		if("SecondPartyContentRelease".equals(contentPortletKey)){
			listResult = infoServer.secondPartyContentFind(date, title, publisher_id ,approve_state, pageSize, (pageNo-1)*pageSize);
		}
		if("OrganizationContentRelease".equals(contentPortletKey)){
			listResult = infoServer.OrganizationContentFind(date, title, publisher_id , pageSize, (pageNo-1)*pageSize);
		}
	    
	    req.setAttribute("infos", listResult);      //发送查询数据		
	    req.setAttribute("pageNo", pageNo); //发送当前页码
	    req.setAttribute("sum", totalPage);      //发送总页码
	    req.setAttribute("contentPortletKey", "ContentRelease");
	    req.setAttribute("date", date);
	    req.setAttribute("title", title);
	    req.setAttribute("publisher_id", publisher_id);
	    req.setAttribute("approve_state", approve_state);
		/*
		String sql = "";
		String title=ParamUtil.getString(req, "title");
		req.setAttribute("title", title);
		if("ContentRelease".equals(contentPortletKey)){			
			if(null != title && !"".equals(title)){
				sql = "select * from hg_content_management_info where approve_state=0 or (approve_state<>0 and first_approve_id<>'' and first_approve_id is not null) ORDER BY to_top_time DESC,publish_time DESC";
			}else {
				sql = "select * from Hg_Content_Management_Info WHERE content_title LIKE '%"+title+"%' and (approve_state=0 or (approve_state<>0 and first_approve_id<>'' and first_approve_id is not null)) ORDER BY to_top_time DESC,publish_time DESC";
			}
		}
		if("SecondPartyContentRelease".equals(contentPortletKey)){	
			if(null != title && !"".equals(title)){
				sql = "select * from hg_content_management_info where approve_state<>0 and (first_approve_id='' or first_approve_id is null) ORDER BY to_top_time DESC,publish_time DESC";
			}else {
				sql = "SELECT * FROM Hg_Content_Management_Info WHERE content_title LIKE '%"+title+"%' and (approve_state<>0 and (first_approve_id='' or first_approve_id is null)) ORDER BY to_top_time DESC,publish_time DESC";
			}
		}
		//当前页
		int pageNo;
		int page = ParamUtil.getInteger(req, "pageNo");
		if(page==0){
			pageNo=1;//默认当前页为1
		}else{
			pageNo=page;
		}
		//根据sql获取结果集和分页
		PostgresqlQueryResult<Map<String, Object>> postgresqlResults = infoServer.postGresqlFind(pageNo,pageSize,sql);
		List<Map<String, Object>> list = postgresqlResults.getList();//获取集合
		int sum = postgresqlResults.getTotalPage();//获取总页码
					
		req.setAttribute("pageNo", pageNo); //发送当前页码
		req.setAttribute("sum", sum);      //发送总页码
		req.setAttribute("pageSize", pageSize); //发送每页条数
		
		req.setAttribute("infos", list);
		*/
		if("ContentRelease".equals(contentPortletKey)){		
			return "/jsp/contentRelease/contentInfoList.jsp";
		}else if("SecondPartyContentRelease".equals(contentPortletKey)){
			return "/jsp/contentRelease/secondPartyContentInfoList.jsp";
		}else{
			return "/jsp/contentRelease/OrganizationContentInfoList.jsp";
		}
	}

}
