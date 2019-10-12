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
 * 饼形报表二级
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.PartyEcharts,
		"mvc.command.name=/EchartDetailedPieCommand"
    },
    service = MVCRenderCommand.class
)
public class EchartDetailedPieCommand implements MVCRenderCommand {
	
	Logger logger = Logger.getLogger(EchartDetailedPieCommand.class);
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
			String paramsData = ParamUtil.getString(renderRequest, "paramsDataPie");//报表下标
			paramsData =	HtmlUtil.escape(paramsData);
//			String sql = "SELECT org_name AS keyname,COUNT(*) AS valname FROM hg_party_meeting_plan_info as plan LEFT JOIN hg_party_org as org "+
//						"ON plan.organization_id = org.org_id "+
//						"WHERE plan.meeting_type = '"+paramsData+"' "+
//						"GROUP BY org_name ";
			String sql = "SELECT member_party_committee AS keyname,count(*) AS valname FROM hg_party_meeting_plan_info AS plan LEFT OUTER JOIN "+
						"(select distinct member_org, member_party_committee,historic from hg_party_member) AS mem "+
						"ON plan.organization_id = mem.member_org "+
						"WHERE plan.meeting_type = ? "+
						"AND member_party_committee is not NULL "+
						"and mem.historic is false "+
						"GROUP BY member_party_committee ";
			//分页查询
			Map<String, Object> postgresqlResults = partyOrgServer.postGresqlFind(pageNo,pageSize,sql,paramsData);
			list = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
			totalPage = (int) postgresqlResults.get("totalPage");//获取总页码
			
			renderRequest.setAttribute("paramsData", paramsData);//报表下标
			renderRequest.setAttribute("list", list);
			renderRequest.setAttribute("pageNo", pageNo);
			renderRequest.setAttribute("totalPage", totalPage);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return "/jsp/echarts/listDatePie.jsp";
	}
}