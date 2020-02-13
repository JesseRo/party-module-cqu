package party.portlet.party;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
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

import hg.party.server.party.PartyMeetingPlanInfo;
import hg.party.server.party.PartyMemberServer;
import hg.party.server.toDoList.EvaluationServer;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月2日下午6:14:43<br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=组织部查看活动进度(查看二级党委)",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/party/PartySecondaryMeetingPlan.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PartySecondary,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartySecondaryPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(PartySecondaryPortlet.class);
	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	@Reference
	private PartyMemberServer partyMemberServer;
	@Reference
	private EvaluationServer evaluationServer;
	
	private int pageSize = 8;//每页条数
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		String search = ParamUtil.getString(renderRequest, "search");
		search = HtmlUtil.escape(search);



		//获取当前页
		List<Map<String, Object>> list = null;
		int pageNo = 0;
		int page = ParamUtil.getInteger(renderRequest, "pageNo"); //获取当前页
		int totalPage = ParamUtil.getInteger(renderRequest, "total_page_");//总页码
		if(page==0){
			pageNo = 1;//默认当前页为1
		}else if(page > totalPage){
			pageNo = totalPage;
		}else{
			pageNo = page;
		}

//				String sql ="select tt.*, us.user_name as check_person_us  from "+
//							"(SELECT note.attachment AS attachment_n,gr.inform_id AS informid,plan.meeting_id as meeting,plan.id AS plan_id,info.meeting_theme as theme_,org.org_id as org_id_u, "+
//							"info.start_time as start_,info.end_time as end_,plan.start_time as start_p,plan.end_time as end_p,orgs.org_name AS org_names,org.org_name AS org_namez,* "+
//							"FROM (((((hg_party_inform_group_info AS gr "+
//							"Inner JOIN hg_party_org_inform_info AS info ON "+
//							"info.inform_id = gr.inform_id) "+
//							"INNER JOIN hg_party_meeting_plan_info AS plan ON gr.inform_id = plan.inform_id AND gr.pub_org_id = plan.organization_id) "+
//							"LEFT JOIN hg_party_meeting_notes_info AS note ON plan.meeting_id = note.meeting_id) "+
//							"LEFT JOIN hg_party_org AS org ON org.org_id = gr.pub_org_id) "+
//							"LEFT JOIN hg_users_info AS usr ON usr.user_id = plan.auditor) "+
//							"LEFT JOIN hg_party_org AS orgs ON info.org_type = orgs.org_id "+
//							"where gr.inform_id = ? "+
//							"and org.org_name LIKE ? "+
//							"and org.historic is false "+
//							"and orgs.historic is false "+
//							"ORDER BY read_status, plan.start_time DESC ) as tt "+
//							"LEFT OUTER JOIN hg_users_info as us "+
//				            "on tt.check_person_org=us.user_id ";

        String sql = "SELECT\n" +
                "\tnote.attachment AS attachment_n,\n" +
                "\tplan.meeting_id AS meeting,\n" +
                "\tplan.ID AS plan_id,\n" +
                "\torg.org_id AS org_id_u,\n" +
                "\tplan.start_time AS start_p,\n" +
                "\tplan.end_time AS end_p,\n" +
                "\torg.org_name AS org_namez, place.place as placeName, * \n" +
                "FROM\n" +
                "\thg_party_meeting_plan_info AS plan \n" +
                "\tLEFT JOIN hg_party_meeting_notes_info AS note ON plan.meeting_id = note.meeting_id \n" +
                "\tLEFT JOIN hg_party_org AS org ON org.org_id = plan.organization_id \n" +
				"\tLEFT JOIN hg_users_info as users on users.user_id = plan.check_person \n" +
				"left join hg_party_place place on place.id = plan.place " +
				"\tWHERE\n" +
//						"\t\torg.org_type = 'secondary' \n" +
                "\t\t org.historic IS FALSE \n" +
                "\t\tAND (\n" +
                "\t\tplan.task_status = '5' \n" +
                "\t\tOR plan.task_status = '6' \n" +
                "\t\tOR plan.task_status = '7' \n" +
                ")\n";
		String _search = "%" + search + "%";
        if (!StringUtils.isEmpty(search)){
            sql = sql + " and (plan.meeting_theme like ? or org.org_name like ?)";
        }
        sql = sql + "ORDER BY\n" +
                "\t\tplan.task_status asc";
		Map<String, Object> postgresqlResults;
		if (!StringUtils.isEmpty(search)) {
			postgresqlResults = partyMeetingPlanInfo.postGresqlFind(pageNo, pageSize, sql, _search, _search);
		}else{
			postgresqlResults = partyMeetingPlanInfo.postGresqlFind(pageNo, pageSize, sql);
		}
        list = (List<Map<String, Object>>) postgresqlResults.get("list");//获取集合

        totalPage = (int) postgresqlResults.get("totalPage");//获取总页码

		renderRequest.setAttribute("list", list);
		renderRequest.setAttribute("pageNo", pageNo);
		renderRequest.setAttribute("totalPage", totalPage);
		renderRequest.setAttribute("search", search);
		
		super.doView(renderRequest, renderResponse);
	}

}

