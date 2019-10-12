package party.portlet.detail;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.entity.pageView.Hg_Pageviews_Info;
import hg.party.server.contentInfo.ContentInfoServer;
import hg.party.server.pageView.PageViewsService;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月19日下午3:43:50<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=详细页面",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/detail/view.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.Details,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class DetailsPortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(DetailsPortlet.class);
	@Reference
	private ContentInfoServer  contentInfoServer;
	@Reference
	private PageViewsService pageViewsServer;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse){
		try{
			logger.info("DetailsPortlet doView......");
			HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
			
			
			//从请求消息头获取资源id：/picTxtdetailpage?resources_id=
			String resources_id = PortalUtil.getOriginalServletRequest(request).getParameter("resources_id");	
			resources_id = HtmlUtil.escape(resources_id);
			if(resources_id == null || resources_id ==""){
				//从portlet:actionURL/renderURL/resourceURL获取资源id
				resources_id = ParamUtil.getString(renderRequest, "resources_id");
				resources_id = HtmlUtil.escape(resources_id);
			}
			if(resources_id == null || resources_id ==""){
				//从session获取资源id
				resources_id = (String)SessionManager.getAttribute("resources_id", "resources_id");
			}
			//把资源id放入session
			renderRequest.setAttribute("resources_id", resources_id);
			SessionManager.setAttribute("resources_id", "resources_id", resources_id);
			
			Map<String,Object> map=goToDetailsPage(resources_id);  
			renderRequest.setAttribute("map", map);  
			List<Map<String, Object>> atts = contentInfoServer.findAttachmentByResourcesId(resources_id);
			renderRequest.setAttribute("atts", atts);
			
			HttpServletRequest req = PortalUtil.getHttpServletRequest(renderRequest); 
			String resourcesId = PortalUtil.getOriginalServletRequest(req).getParameter("resources_id");
			
			if(resourcesId!=null){
				addPageViews(req);
			}
			
			super.doView(renderRequest, renderResponse);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Map<String,Object> goToDetailsPage(String resourcesId){//进入详细页面
//		logger.info("20170921//4444//资源id："+resourcesId);
		Map<String,Object> map=contentInfoServer.findContentAndAttachmentByResourcesId(resourcesId).get(0);
		return map;
	}
	
	public void addPageViews(HttpServletRequest req){//获取访问信息，添加到数据库
		try{
			String sessionID=req.getRequestedSessionId();
			String resourcesId = PortalUtil.getOriginalServletRequest(req).getParameter("resources_id");
			String ip=getIRealIPAddr(req);
//			String visitUserId = (String )SessionManager.getAttribute("user_id", "user_id");
//			if(null == visitUserId){
//				visitUserId = (String) SessionManager.getAttribute(sessionID+"ses", "user_name");
//			}
			String visitUserId = (String) SessionManager.getAttribute(sessionID+"ses", "user_name");
			if(null == visitUserId){
				visitUserId = "匿名用户";
			}
			if(resourcesId==null){
				return;
			}else{
				Hg_Pageviews_Info pageViews = new Hg_Pageviews_Info();
				pageViews.setResources_id(resourcesId);
				pageViews.setVisit_user_id(visitUserId);
				pageViews.setVisit_time(new Timestamp(new Date().getTime()));//设置系统当前时间
				pageViews.setVisit_ip(ip);
				pageViews.setRemark("备注");
				pageViewsServer.addEntity(pageViews);
//				logger.info(new Timestamp(new Date().getTime()));
				logger.info("添加数据成功");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	

	public String getIRealIPAddr(HttpServletRequest request) { //获取用户真实IP  
		String ip = request.getHeader("x-forwarded-for");  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr(); 
		}
		return ip;
		}
}
