package party.portlet.downlist;

import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.dwonlistserver.DownListServer;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=下拉列表选项",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/downlist/view.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/downlist/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.DownList,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class DownListPortlet extends MVCPortlet {
	Logger logger = Logger.getLogger(DownListPortlet.class);
	@Reference
	private DownListServer listServer;
	private int pageSize=10;//每页8条数据
	public void doView(RenderRequest req, RenderResponse res){
		   String sessionId=req.getRequestedSessionId();
		   String orgId =	SessionManager.getAttribute(sessionId, "department").toString();
		   String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
		try {
			logger.info("AssetManagement doView........");
			//当前页
			int pageNo;
			int page = ParamUtil.getInteger(req, "pageNo");
			int totalPage = ParamUtil.getInteger(req, "total_page_");//总页码
			if(page==0){
				pageNo=1;//默认当前页为1
			}else if(page > totalPage){
				pageNo = totalPage;
			}else{
				pageNo=page;
			}
			//根据sql获取结果集和分页
//			String sql = "SELECT * FROM hg_value_attribute_info ORDER BY id desc";
			String sql = "SELECT * FROM hg_value_attribute_info "+
						"WHERE resources_type !='taskStatus' "+
						"ORDER BY id desc ";	
			//根据搜索内容查询
			logger.info("sou suo doView........");
			String title=ParamUtil.getString(req, "title");
			title =	HtmlUtil.escape(title);
			logger.info(title);
			Map<String, Object> postgresqlResults = listServer.postGresqlFind(pageNo,pageSize,sql);
			if(title != null && !"".equals(title)){
				sql = "SELECT * FROM hg_value_attribute_info where resources_type !='taskStatus' and resources_type LIKE ? OR resources_value LIKE ? OR resources_key LIKE ? ORDER BY id desc";
				postgresqlResults = listServer.postGresqlFind(pageNo,pageSize,sql,"%"+title+"%","%"+title+"%","%"+title+"%");
			}
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
			int sum = (int) postgresqlResults.get("totalPage");//获取总页码
						
			req.setAttribute("list", list);      //发送查询数据		
			req.setAttribute("pageNo", pageNo); //发送当前页码
			req.setAttribute("sum", sum);      //发送总页码
			req.setAttribute("pageSize", pageSize); //发送每页条数
            req.setAttribute("userId", userId);
			super.doView(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


