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
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.party.PartyOrgServer;
import party.constants.PartyPortletKeys;
/**
 * 出勤率详细
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.PartyEcharts,
		"mvc.command.name=/EchartDetailedBarCommand"
    },
    service = MVCRenderCommand.class
)
public class EchartDetailedBarCommand implements MVCRenderCommand {
	
	Logger logger = Logger.getLogger(EchartDetailedBarCommand.class);
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
			String keyname = ParamUtil.getString(renderRequest, "keyname");
			keyname = HtmlUtil.escape(keyname);
			String sql = "SELECT member_party_committee,attendance as valname,org_name AS keyname from hg_party_meeting_plan_info AS plan "+
					"LEFT JOIN (SELECT member_party_committee,member_org,count(*) from hg_party_member AS mem  WHERE historic is false "+
					"GROUP BY member_party_committee,member_org,historic) AS mber  "+
					"ON plan.organization_id=mber.member_org  "+
					"LEFT JOIN hg_party_meeting_notes_info as note  "+
					"ON plan.meeting_id=note.meeting_id  "+
					"LEFT JOIN hg_party_org AS org  "+
					"ON mber.member_org=org.org_id  "+
					"WHERE meeting_type='主题党日'  "+
					"and org.historic is false "+
					"AND mber.member_party_committee = ? "+
					"AND mber.member_party_committee is not null  "+
					"AND mber.member_party_committee <>''  "+
					"AND note.attendance  <>''  "+
					"AND note.attendance is not null  "+
					"AND plan.start_time  "+
					"between  "+
					"(CURRENT_DATE-31) and CURRENT_DATE ";
			//分页查询
			Map<String, Object> postgresqlResults = partyOrgServer.postGresqlFind(pageNo,pageSize,sql,keyname);
			list = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合
			totalPage = (int) postgresqlResults.get("totalPage");//获取总页码
			
			renderRequest.setAttribute("keyname", keyname);//学院名称
			renderRequest.setAttribute("list", list);
			renderRequest.setAttribute("pageNo", pageNo);
			renderRequest.setAttribute("totalPage", totalPage);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return "/jsp/echarts/listDateBar.jsp";
	}
}