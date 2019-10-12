package hg.party.command.echarts;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.party.PartyOrgServer;
import party.constants.PartyPortletKeys;
/**
 * 访问量报表二级
 * 
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.PartyEchartsVisit,
		"mvc.command.name=/EchartDetailedVisitCommand"
    },
    service = MVCRenderCommand.class
)
public class EchartDetailedVisitCommand implements MVCRenderCommand {
	
	Logger logger = Logger.getLogger(EchartDetailedVisitCommand.class);
	@Reference
	private PartyOrgServer partyOrgServer;
	
	private int pageSize = 8;//每页条数
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		//分页
		List<Map<String, Object>> list = null;
		int totalPage = 0;
		int pageNo = 0;
		int page = ParamUtil.getInteger(renderRequest, "pageNo");//当前页
		totalPage = ParamUtil.getInteger(renderRequest, "total_page_");//总页码
		if(page==0){
			pageNo = 1;//默认当前页为1
		}else if(page > totalPage){
			pageNo = totalPage;
		}else{
			pageNo = page;
		}
		
		try {
			String paramsData = ParamUtil.getString(renderRequest, "paramsData");//报表下标
			paramsData = "中共西南大学"+paramsData;
			paramsData =	HtmlUtil.escape(paramsData);
//			String sql = "SELECT user_role AS keyname,count(*) AS valname FROM "+
//						"(SELECT * FROM hg_party_visit_count as vis "+
//						"LEFT OUTER JOIN hg_users_info as us "+
//						"ON vis.user_id=us.user_id "+
//						"LEFT OUTER JOIN hg_party_member AS mem "+
//						"ON us.user_id=mem.member_identity "+
//						"WHERE mem.member_party_committee='"+paramsData+"') as a "+
//						"GROUP BY user_role ";
			
			String sql = "SELECT org_name as keyname,count(*) as valname FROM "+
					"(SELECT * FROM hg_party_visit_count as vis  "+
					"LEFT OUTER JOIN hg_users_info as us  "+
					"ON vis.user_id=us.user_id  "+
					"LEFT OUTER JOIN hg_party_member AS mem  "+
					"ON us.user_id=mem.member_identity  "+
					"LEFT JOIN hg_party_org as org "+
					"ON org.org_id=us.user_department_id "+
					"WHERE mem.member_party_committee= ? and mem.historic is false and org.historic is false) as a "+
					"GROUP BY org_name ";
			//分页查询
			Map<String, Object> postgresqlResults = partyOrgServer.postGresqlFind(pageNo,pageSize,sql,paramsData);
			list = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
			totalPage = (int) postgresqlResults.get("totalPage");//获取总页码
			
			paramsData = paramsData.substring(6);
			renderRequest.setAttribute("paramsData", paramsData);//报表下标
			renderRequest.setAttribute("list", list);
			renderRequest.setAttribute("pageNo", pageNo);
			renderRequest.setAttribute("totalPage", totalPage);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return "/jsp/echarts/listDateVisit.jsp";
	}
}