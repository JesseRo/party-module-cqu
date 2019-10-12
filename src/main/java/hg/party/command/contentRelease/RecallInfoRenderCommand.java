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
import java.util.Properties;
import java.util.UUID;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.contentManagementInfo.Hg_Content_Management_Info;
import hg.party.server.contentInfo.ContentInfoServer;
import hg.party.server.publication.PublicationServer;
import party.constants.PartyPortletKeys;
/**
 * 撤回
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
		"mvc.command.name=/info/recallInfoUrl"
    },
    service = MVCRenderCommand.class
)
public class RecallInfoRenderCommand implements MVCRenderCommand {
	Logger logger = Logger.getLogger(RecallInfoRenderCommand.class);
	@Reference
	private ContentInfoServer infoServer;
	@Reference
	private PublicationServer publicationServer;
	private int pageSize=8;//每页15条数据
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse){
		String contentPortletKey = "";
		try {
			logger.info("RecallInfoRenderCommand render.........");
			
			contentPortletKey = ParamUtil.getString(renderRequest, "contentPortletKey");
			contentPortletKey = HtmlUtil.escape(contentPortletKey);
			renderRequest.setAttribute("contentPortletKey", contentPortletKey);
			String sessionId = renderRequest.getRequestedSessionId();
			String user_name = (String) SessionManager.getAttribute(sessionId, "user_name");//昵称
			String department = (String) SessionManager.getAttribute(sessionId, "department");//部门id
			renderRequest.setAttribute("user_name", user_name);
			String date = ParamUtil.getString(renderRequest, "date");//发布日期
			date = HtmlUtil.escape(date);
			String title = ParamUtil.getString(renderRequest, "title");//标题
			title = HtmlUtil.escape(title);
			String publisher_id = ParamUtil.getString(renderRequest, "publisher_id");//发布人
			publisher_id = HtmlUtil.escape(publisher_id);
			String approve_state = ParamUtil.getString(renderRequest, "approve_state");//审核状态
			approve_state = HtmlUtil.escape(approve_state);
			int pageNo = ParamUtil.getInteger(renderRequest, "pageNo");
			
			String resourceId = ParamUtil.getString(renderRequest, "resourceId");
			resourceId = HtmlUtil.escape(resourceId);
			Hg_Content_Management_Info info = infoServer.findByResourceId(resourceId);
			
			if("ContentRelease".equals(contentPortletKey)){
				info.setApprove_state(5);
			}
			if("SecondPartyContentRelease".equals(contentPortletKey)){
				info.setApprove_state(6);
			}
			info.setFirst_approve_id("");
			info.setFirst_dismissal("");
			info.setSecond_approve_id("");
			info.setSecond_dismissal("");
			infoServer.saveOrUpdate(info);
			
			List<Map<String, Object>> list = null;
			if("ContentRelease".equals(contentPortletKey)){
				list = infoServer.contentFind(date, title, publisher_id,approve_state,department);
			}
			if("SecondPartyContentRelease".equals(contentPortletKey)){
				list= infoServer.secondPartyContentFind(date, title, publisher_id,approve_state);
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
		    
		    renderRequest.setAttribute("infos", listResult);      //发送查询数据		
		    renderRequest.setAttribute("pageNo", pageNo); //发送当前页码
		    renderRequest.setAttribute("sum", totalPage);      //发送总页码
		    renderRequest.setAttribute("contentPortletKey", "ContentRelease");
		    renderRequest.setAttribute("date", date);
		    renderRequest.setAttribute("title", title);
		    renderRequest.setAttribute("publisher_id", publisher_id);
		    renderRequest.setAttribute("approve_state", approve_state);
			
			/*
			info.setApprove_state(5);
			info.setFirst_approve_id("");
			info.setSecond_approve_id("");
			
			//当前页
			int pageNo;
			int page = ParamUtil.getInteger(renderRequest, "pageNo");
			if(page==0){
				pageNo=1;//默认当前页为1
			}else{
				pageNo=page;
			}
			//根据sql获取结果集和分页
			String sql = "";
			if("ContentRelease".equals(contentPortletKey)){
				sql = "select * FROM hg_content_management_info where approve_state=0 ORDER BY to_top_time DESC,publish_time DESC";
			}
			if("SecondPartyContentRelease".equals(contentPortletKey)){
				sql = "SELECT * FROM hg_content_management_info where approve_state=1 and (first_approve_id is null or first_approve_id='') ORDER BY to_top_time DESC,publish_time DESC";
			}
			PostgresqlQueryResult<Map<String, Object>> postgresqlResults = infoServer.postGresqlFind(pageNo,pageSize,sql);
			List<Map<String, Object>> infos = postgresqlResults.getList();//获取集合
			int sum = postgresqlResults.getTotalPage();//获取总页码
						
			renderRequest.setAttribute("infos", infos);      //发送查询数据		
			renderRequest.setAttribute("pageNo", pageNo); //发送当前页码
			renderRequest.setAttribute("sum", sum);      //发送总页码
			renderRequest.setAttribute("pageSize", pageSize); //发送每页条数
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("ContentRelease".equals(contentPortletKey)){			
			return "/jsp/contentRelease/contentInfoList.jsp";
		}else{
			return "/jsp/contentRelease/secondPartyContentInfoList.jsp";
		}
		
	}
}