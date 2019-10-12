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
 * 组织部发布新闻
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=组织部发文",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"portletSetupShowBorders=false",
		"javax.portlet.init-param.view-template=/jsp/contentRelease/OrganizationContentInfoList.jsp",
		"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class OrganizationContentReleasePortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(OrganizationContentReleasePortlet.class);
	@Reference
	private ContentInfoServer infoServer;
	private int pageSize = 8;//每页15条数据
	@Override
	public void doView(RenderRequest req, RenderResponse resp) throws PortletException {
		try {
			logger.info("OrganizationContentRelease doView.......");
			
			String sessionId = req.getRequestedSessionId();
			String user_name = (String) SessionManager.getAttribute(sessionId, "user_name");//昵称
			String user_id = (String) SessionManager.getAttribute(sessionId, "userName");//用户id
			req.setAttribute("user_name", user_name);
			String date = ParamUtil.getString(req, "date");//发布日期
			date = 	HtmlUtil.escape(date);
			String title = ParamUtil.getString(req, "title");//标题
			String publisher_id = ParamUtil.getString(req, "publisher_id");//发布人
			int pageNo = ParamUtil.getInteger(req, "pageNo");
			
			List<Map<String, Object>> list= infoServer.OrganizationContentFind(date, title, publisher_id);
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
			
		    List<Map<String, Object>> listResult = infoServer.OrganizationContentFind(date, title, publisher_id,  pageSize, (pageNo-1)*pageSize);
		    req.setAttribute("infos", listResult);      //发送查询数据		
			req.setAttribute("pageNo", pageNo); //发送当前页码
			req.setAttribute("sum", totalPage);      //发送总页码
			req.setAttribute("contentPortletKey", "OrganizationContentRelease");
			req.setAttribute("date", date);
			req.setAttribute("title", title);
			req.setAttribute("publisher_id", publisher_id);
			
			List<ContentTypeConvert> states = ContentReviewStateEnum.getConvertList();//审核状态
			req.setAttribute("states", states);
			
			super.doView(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}