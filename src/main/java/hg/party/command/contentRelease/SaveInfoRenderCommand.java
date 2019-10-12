/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hg.party.command.contentRelease;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.contentManagementInfo.Hg_Content_Management_Info;
import hg.party.server.contentInfo.ContentInfoServer;
import hg.party.server.publication.PublicationServer;
import party.constants.PartyPortletKeys;
/**
 * @author caoxm
 * 发文
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
		"mvc.command.name=/info/saveInfo"
    },
    service = MVCRenderCommand.class
)
public class SaveInfoRenderCommand implements MVCRenderCommand {
	Logger logger = Logger.getLogger(SaveInfoRenderCommand.class);
	@Reference
	private ContentInfoServer infoServer;
	@Reference
	private PublicationServer publicationServer;
	private int pageSize=8;//每页15条数据
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse){
		String contentPortletKey = "";
		try {
			logger.info("SaveInfoRenderCommand render.........");
			
			Hg_Content_Management_Info info = null;
			
			String resourceId = ParamUtil.getString(renderRequest, "resourceId");
			resourceId = 	HtmlUtil.escape(resourceId);
			contentPortletKey = ParamUtil.getString(renderRequest, "contentPortletKey");
			contentPortletKey = 	HtmlUtil.escape(contentPortletKey);
			renderRequest.setAttribute("contentPortletKey", contentPortletKey);
			
			if(null != resourceId && !"".equals(resourceId)){
				info = infoServer.findByResourceId(resourceId);
			}else{
				info = new Hg_Content_Management_Info();
				resourceId = UUID.randomUUID().toString();
			}
			
			String sessionID = renderRequest.getRequestedSessionId();
//			String userID = (String)SessionManager.getAttribute(sessionID, "userName");//获取用户的id
			String department = (String)SessionManager.getAttribute(sessionID, "department");//获取用户组织
			
			String publisher_id = (String )SessionManager.getAttribute(sessionID, "user_name");//获取中文名
			String attValue = ParamUtil.getString(renderRequest, "attValue");//主题(存remark字段)
			attValue = 	HtmlUtil.escape(attValue);
			String content_title = ParamUtil.getString(renderRequest, "content_title");//标题	
			content_title = 	HtmlUtil.escape(content_title);
			String content_description = ParamUtil.getString(renderRequest, "content_description");//描述
			content_description = 	HtmlUtil.escape(content_description);
			String content_type = ParamUtil.getString(renderRequest, "content_type");//内容类型
			content_type = 	HtmlUtil.escape(content_type);
			String uploadAjaxFileInput = ParamUtil.getString(renderRequest, "uploadAjaxFileInput");//附件
			uploadAjaxFileInput = 	HtmlUtil.escape(uploadAjaxFileInput);
			String content_body = ParamUtil.getString(renderRequest, "content_body");//正文
		//	content_body = 	HtmlUtil.escape(content_body);
			if(!"".equals(content_type)){
				info.setResources_id(resourceId);
				info.setContent_user_id(department);
				info.setContent_title(content_title);
				info.setContent_type(content_type);
				info.setContent_description(content_description);
				info.setPublisher_id(publisher_id);
				Timestamp time1 = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date(System.currentTimeMillis())));
				info.setPublish_time(time1);
				Timestamp time2 = Timestamp.valueOf("1970-01-01 00:00:00");
				info.setTo_top_time(time2);
//				info.setReviewer_id(reviewer_id);
				info.setContent_body(content_body);
				info.setIstop(0);
				info.setRemark(attValue);
				if("ContentRelease".equals(contentPortletKey)){
					info.setApprove_state(0);
				}
				if("SecondPartyContentRelease".equals(contentPortletKey)){
					info.setApprove_state(1);
				}
				if("OrganizationContentRelease".equals(contentPortletKey)){
					info.setApprove_state(7);
				}
				info.setFirst_approve_id("");
				info.setFirst_dismissal("");
				info.setSecond_approve_id("");
				info.setSecond_dismissal("");
				int i = infoServer.saveOrUpdate(info);
				if(i > 0){
					//附件处理 @26@,
					String doSql = "";
					if(!"".equals(uploadAjaxFileInput) && null != uploadAjaxFileInput){
						uploadAjaxFileInput = uploadAjaxFileInput/*.substring(0, uploadAjaxFileInput.length() - 1)*/.replace("@", "");
						String[] uploadAjaxFileStr = uploadAjaxFileInput.split("\\,");
						if(null != uploadAjaxFileStr && uploadAjaxFileStr.length > 0){
							String idsStr = "";
							for(String file : uploadAjaxFileStr){
								idsStr += "'"+file+"',";
							}
							if("" != idsStr){
								idsStr = idsStr.substring(0, idsStr.length() - 1);
								doSql = "update hg_content_management_attachment set resources_id = '"+resourceId+"' where id in ("+idsStr+")";
								logger.info("执行的sql语句：" + doSql);
								infoServer.doSql(doSql);
							}
						}
					}
				}
			}
			
			//当前页
			int pageNo = 0;
			int totalPage = 0;
			int page = ParamUtil.getInteger(renderRequest, "pageNo");
			totalPage = ParamUtil.getInteger(renderRequest, "total_page_");//总页码
			
			if(page==0){
				pageNo=1;//默认当前页为1
			}else if(page>totalPage){
				pageNo=totalPage;
			}else{
				pageNo=page;
			}
			//根据sql获取结果集和分页
			String sql = "";
			if("ContentRelease".equals(contentPortletKey)){						
				sql = "select * from hg_content_management_info where approve_state=0 or approve_state=5 or (approve_state<>0 and approve_state<>5 and first_approve_id<>'' ) ORDER BY to_top_time DESC,publish_time DESC";
			}
			if("SecondPartyContentRelease".equals(contentPortletKey)){	
				sql = "select * from hg_content_management_info where approve_state<>0 and approve_state<>5 and approve_state<>7 and first_approve_id=''  ORDER BY to_top_time DESC,publish_time DESC";
			}
			if("OrganizationContentRelease".equals(contentPortletKey)){	
				sql = "select * from hg_content_management_info where approve_state=7  ORDER BY to_top_time DESC,publish_time DESC";
			}
			Map<String, Object> postgresqlResults = infoServer.postGresqlFind(pageNo,pageSize,sql);
			List<Map<String, Object>> infos = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
			int sum = (int) postgresqlResults.get("totalPage");//获取总页码
						
			renderRequest.setAttribute("infos", infos);      //发送查询数据		
			renderRequest.setAttribute("pageNo", pageNo); //发送当前页码
			renderRequest.setAttribute("sum", sum);      //发送总页码
			renderRequest.setAttribute("pageSize", pageSize); //发送每页条数
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("ContentRelease".equals(contentPortletKey)){
			return  "/jsp/contentRelease/contentInfoList.jsp";
		}else if("SecondPartyContentRelease".equals(contentPortletKey)){
			return "/jsp/contentRelease/secondPartyContentInfoList.jsp";
		}else{
			return "/jsp/contentRelease/OrganizationContentInfoList.jsp";
		}
		
		
	}
	
	

}