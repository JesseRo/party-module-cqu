package party.portlet.cqu;

import java.io.IOException;
import java.util.Collections;
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
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.party.PartyMeetingNoteServer;
import hg.party.server.party.PartyMeetingPlanInfoService;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.entity.PageQueryResult;

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
			"javax.portlet.display-name=指派录入(组织部)",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/party/PartyApprovalOrgPlan.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PartyEntryOrg,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartyEntryOrgPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(PartyEntryOrgPortlet.class);
	@Reference
	private RetentionDao retentionDao;

	private int pageSize = 8;//每页条数
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
//		String informId = ParamUtil.getString(renderRequest, "informId");//通知id
		
		String sessionID=renderRequest.getRequestedSessionId();
		String user_id = (String)SessionManager.getAttribute(sessionID, "userName");//用户id

		int pageNo = 0;
		int page = ParamUtil.getInteger(renderRequest, "pageNo"); //获取当前页
		if(page==0){
			pageNo = 1;//默认当前页为1
		}

		String sql = "SELECT\n" +
				"  contact.member_name as contact_name, place.campus, place.place as place_name,\n" +
				"\tplan.meeting_id AS meeting,\n" +
				"\tplan.start_time AS start_p,* \n" +
				"FROM\n" +
				"\t( hg_party_meeting_plan_info AS plan LEFT JOIN hg_party_meeting_notes_info AS notes ON plan.meeting_id = notes.meeting_id )\n" +
				"\tLEFT JOIN hg_party_org AS org ON plan.organization_id = org.org_id \n" +
				"\tLEFT JOIN hg_party_member AS contact ON plan.contact = contact.member_identity \n" +
				"\tleft join hg_party_place place on place.id = plan.place \n" +
				"WHERE\n" +
				"\tplan.check_person = ?\n" +
				"\tAND org.historic IS FALSE \n" +
				"\tAND ( plan.task_status = '5' OR plan.task_status = '7' ) \n" +
				"ORDER BY\n" +
				"\tplan.ID DESC";
		PageQueryResult<Map<String, Object>> result = retentionDao.pageBySql(pageNo, pageSize, sql, Collections.singletonList(user_id));



		renderRequest.setAttribute("list", result.getList());
		renderRequest.setAttribute("pageNo", result.getPageNow());
		renderRequest.setAttribute("totalPage", result.getTotalPage());
		
		super.doView(renderRequest, renderResponse);
	}

}

