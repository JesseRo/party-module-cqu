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
 * zlm 2.18.1.17
 * 二级党组织审核(一审)
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=二级党组织审批新闻",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"portletSetupShowBorders=false",
		"javax.portlet.init-param.view-template=/jsp/contentRelease/sencondPartyContentReview.jsp",
		"javax.portlet.name=" + PartyPortletKeys.SecondPartyReviewContent,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SecondPartyReviewContentPortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(SecondPartyReviewContentPortlet.class);
	@Reference
	private ContentInfoServer infoServer;
	private int pageSize = 8;
	@Override
	public void doView(RenderRequest req, RenderResponse resp) throws PortletException {
		try {
			logger.info("SecondPartyReviewContentPortlet doView.......");
			//获取登录人id
			String sessionId = req.getRequestedSessionId();
//			String user_name = (String) SessionManager.getAttribute(sessionId, "user_name");//昵称
			String department = (String) SessionManager.getAttribute(sessionId, "department");//用户组织
			
			String already = ParamUtil.getString(req, "already");
			already =	HtmlUtil.escape(already);
			String sql = "";
			if("已审批".equals(already)){
//				sql = "select * from hg_content_management_info as cont join hg_party_org  as org "+
//					"on  cont.content_user_id = org.org_id "+
//					"where org.historic is false and org.org_parent=? "+
//					"and  cont.approve_state=1 or cont.approve_state=2 "+
//					"ORDER BY publish_time DESC ";
				sql = "select * from hg_content_management_info AS cont "+
					"LEFT JOIN hg_party_org AS org "+
					"ON cont.content_user_id=org.org_id "+
					"WHERE org.org_parent=? "+
					"and (cont.approve_state = 1 or cont.approve_state = 2 or cont.approve_state = 4) "+
					"AND org.historic is false "+
					"ORDER BY publish_time DESC ";
			}else {
				sql = "select * from hg_content_management_info AS cont "+
					"LEFT JOIN hg_party_org AS org "+
					"ON cont.content_user_id=org.org_id "+
					"WHERE org.org_parent=? "+
					"and cont.approve_state = 0 "+
					"AND org.historic is false "+
					"ORDER BY publish_time DESC ";	
			}
			
			//当前页
			int pageNo;
			int page = ParamUtil.getInteger(req, "pageNo");
			int totalPage = ParamUtil.getInteger(req, "total_page_");//总页码
			if(page==0){
				pageNo=1;//默认当前页为1
			}else if(page > totalPage){
				pageNo = totalPage;
			}else{
				pageNo = page;
			}
			//根据sql获取结果集和分页
			Map<String, Object> postgresqlResults = infoServer.postGresqlFind(pageNo,pageSize,sql,department);
			List<Map<String, Object>> infos = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
			int sum = (int) postgresqlResults.get("totalPage");//获取总页码
						
			req.setAttribute("infos", infos);      //发送查询数据		
			req.setAttribute("pageNo", pageNo); //发送当前页码
			req.setAttribute("sum", sum);      //发送总页码
			req.setAttribute("pageSize", pageSize); //发送每页条数
			req.setAttribute("contentPortletKey", "SecondPartyReviewContent");
			req.setAttribute("already", already);
			
			List<ContentTypeConvert> states = ContentReviewStateEnum.getConvertList();//审核状态
			req.setAttribute("states", states);
			
			super.doView(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}