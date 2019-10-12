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

import hg.party.server.contentInfo.ContentInfoServer;
import hg.party.unity.ContentReviewStateEnum;
import hg.party.unity.ContentTypeConvert;
import party.constants.PartyPortletKeys;


/**
 * zlm 2.18.1.17
 * 组织部审核
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=组织部审批新闻",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"portletSetupShowBorders=false",
		"javax.portlet.init-param.view-template=/jsp/contentRelease/contentReview.jsp",
		"javax.portlet.name=" + PartyPortletKeys.OrganizationReviewContent,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class OrganizationReviewContentPortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(OrganizationReviewContentPortlet.class);
	@Reference
	private ContentInfoServer infoServer;
	private int pageSize = 8;//每页15条数据
	@Override
	public void doView(RenderRequest req, RenderResponse resp) throws PortletException {
		try {
//			logger.info("OrganizationReviewContentPortlet doView.......");
			
			String already = ParamUtil.getString(req, "already");
			already =	HtmlUtil.escape(already);
			String sql = "";
			if("已审批".equals(already)){
				sql = "SELECT * FROM hg_content_management_info where approve_state=3 or approve_state=4 ORDER BY publish_time DESC";
			}else {
				sql = "SELECT * FROM hg_content_management_info where approve_state=1 ORDER BY publish_time DESC";
			}
			
			//当前页
			int pageNo;
			int page = ParamUtil.getInteger(req, "pageNo");
			int totalPage = ParamUtil.getInteger(req, "total_page_");//总页码
//			if(page==0){
//				pageNo=1;//默认当前页为1
//			}else{
//				pageNo=page;
//			}
			if(page==0){
				pageNo = 1;//默认当前页为1
			}else if(page > totalPage){
				pageNo = totalPage;
			}else{
				pageNo = page;
			}
			//根据sql获取结果集和分页
			Map<String, Object> postgresqlResults = infoServer.postGresqlFind(pageNo,pageSize,sql);
			List<Map<String, Object>> infos = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
			int sum = (int) postgresqlResults.get("totalPage");//获取总页码
						
			req.setAttribute("infos", infos);      //发送查询数据		
			req.setAttribute("pageNo", pageNo); //发送当前页码
			req.setAttribute("sum", sum);      //发送总页码
			req.setAttribute("pageSize", pageSize); //发送每页条数
			req.setAttribute("contentPortletKey", "OrganizationReviewContent");
			req.setAttribute("already", already);//待审批、已审批
			
			List<ContentTypeConvert> states = ContentReviewStateEnum.getConvertList();//审核状态
			req.setAttribute("states", states);
			super.doView(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}