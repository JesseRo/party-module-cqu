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

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.contentInfo.ContentInfoServer;
import hg.party.unity.ContentReviewStateEnum;
import hg.party.unity.ContentTypeConvert;
import party.constants.PartyPortletKeys;


/**
 * @author caoxm
 * 党支部发布新闻
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=党支部发文",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"portletSetupShowBorders=false",
		"javax.portlet.init-param.view-template=/jsp/contentRelease/contentInfoList.jsp",
		"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ContentReleasePortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(ContentReleasePortlet.class);
	@Reference
	private ContentInfoServer infoServer;
	private int pageSize = 8;//每页15条数据
	@Override
	public void doView(RenderRequest req, RenderResponse resp) throws PortletException {
		try {
			logger.info("ContentReleasePortlet doView.......");
			
			String sessionId = req.getRequestedSessionId();
			String user_name = (String) SessionManager.getAttribute(sessionId, "user_name");//昵称
			user_name = HtmlUtil.escape(user_name);
			String department = (String) SessionManager.getAttribute(sessionId, "department");//部门id
			department = HtmlUtil.escape(department);
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
			List<Map<String, Object>> list= infoServer.contentFind(date, title, publisher_id,approve_state,department);
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
			
		    List<Map<String, Object>> listResult = infoServer.contentFind(date, title, publisher_id ,approve_state, pageSize, (pageNo-1)*pageSize,department);
		    req.setAttribute("infos", listResult);      //发送查询数据		
			req.setAttribute("pageNo", pageNo); //发送当前页码
			req.setAttribute("sum", totalPage);      //发送总页码
			req.setAttribute("contentPortletKey", "ContentRelease");
			req.setAttribute("date", date);
			req.setAttribute("title", title);
			req.setAttribute("publisher_id", publisher_id);
			req.setAttribute("approve_state", approve_state);
			
			List<ContentTypeConvert> states = ContentReviewStateEnum.getConvertList();//审核状态
			req.setAttribute("states", states);
			
			super.doView(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}