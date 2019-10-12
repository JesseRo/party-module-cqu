package party.portlet.contentRelease;

import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.contentInfo.ContentInfoServer;
import hg.party.unity.ContentReviewStateEnum;
import hg.party.unity.ContentTypeConvert;
import party.constants.PartyPortletKeys;


/**
 * 二级党组织发文
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=二级党组织发文",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"portletSetupShowBorders=false",
		"javax.portlet.init-param.view-template=/jsp/contentRelease/secondPartyContentInfoList.jsp",
		"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SecondPartyContentReleasePortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(SecondPartyContentReleasePortlet.class);
	@Reference
	private ContentInfoServer infoServer;
	private int pageSize = 8;//每页15条数据
	@Override
	public void doView(RenderRequest req, RenderResponse resp) throws PortletException {
		try {
			logger.info("SecondPartyContentReleasePortlet doView.......");
			
			String sessionId = req.getRequestedSessionId();
			String user_name = (String) SessionManager.getAttribute(sessionId, "user_name");//昵称
			String user_id = (String) SessionManager.getAttribute(sessionId, "userName");//id
			
			req.setAttribute("user_name", user_name);
			String date = ParamUtil.getString(req, "date");//发布日期
			date = HtmlUtil.escape(date);
			String title = ParamUtil.getString(req, "title");//新闻标题
			title = HtmlUtil.escape(title);
			String publisher_id = ParamUtil.getString(req, "publisher_id");//发布人
			publisher_id = HtmlUtil.escape(publisher_id);
			String approve_state = ParamUtil.getString(req, "approve_state");//审核状态
			approve_state = HtmlUtil.escape(approve_state);
			int pageNo = ParamUtil.getInteger(req, "pageNo");
			
			List<Map<String, Object>> list= infoServer.secondPartyContentFind(date, title, publisher_id,approve_state);
			//获取当前页
			
			 int totalCount=list.size();
		     int totalPage=totalCount/pageSize;
		     if (totalCount%pageSize!=0) {
		    	 totalPage=totalPage+1;
			}
		    if(pageNo==0){
				pageNo = 1;//默认当前页为1
			}else if(pageNo>totalPage){
				pageNo = totalPage;
			}
			
		    List<Map<String, Object>> listResult = infoServer.secondPartyContentFind(date, title, publisher_id ,approve_state, pageSize, (pageNo-1)*pageSize);
		    req.setAttribute("infos", listResult);      //发送查询数据		
			req.setAttribute("pageNo", pageNo); //发送当前页码
			req.setAttribute("sum", totalPage);      //发送总页码
			req.setAttribute("contentPortletKey", "SecondPartyContentRelease");
			req.setAttribute("date", date);
			req.setAttribute("title", title);
			req.setAttribute("publisher_id", publisher_id);
			req.setAttribute("approve_state", approve_state);
			
			/*
			String sql = "select * from hg_content_management_info where approve_state<>0 and (first_approve_id='' or first_approve_id is null) ORDER BY to_top_time DESC,publish_time DESC";
			String title=ParamUtil.getString(req, "title");
			if(null != title && !"".equals(title)){
				req.setAttribute("title", title);
				sql = "SELECT * FROM Hg_Content_Management_Info WHERE content_title LIKE '%"+title+"%' and (approve_state<>0 and (first_approve_id='' or first_approve_id is null)) ORDER BY to_top_time DESC,publish_time DESC";
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
			List<Map<String, Object>> infos = postgresqlResults.getList();//获取集合
			int sum = postgresqlResults.getTotalPage();//获取总页码
		    
			req.setAttribute("infos", infos);      //发送查询数据		
			req.setAttribute("pageNo", pageNo); //发送当前页码
			req.setAttribute("sum", sum);      //发送总页码
			req.setAttribute("pageSize", pageSize); //发送每页条数
			req.setAttribute("contentPortletKey", "SecondPartyContentRelease");
			*/
			
			List<ContentTypeConvert> states = ContentReviewStateEnum.getConvertList();//审核状态
			req.setAttribute("states", states);
			
			super.doView(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}