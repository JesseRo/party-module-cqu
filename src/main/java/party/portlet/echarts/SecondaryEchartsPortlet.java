package party.portlet.echarts;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.entity.party.BaseStatistics;
import hg.party.entity.party.OrgAdmin;
import hg.party.entity.party.UserStatistics;
import hg.party.server.organization.OrgAdminService;
import hg.party.server.party.PartyOrgServer;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件名称： 报表<br>
 * 创建人 　： Yu Jiang Xia<br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=二级党委统计",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/echarts/secondaryEcharts.jsp",
			"javax.portlet.name=" + PartyPortletKeys.SecondaryEcharts,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class SecondaryEchartsPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(PartyEchartsPortlet.class);
	@Reference
	private PartyOrgServer partyOrgServer;
	@Reference
	private OrgAdminService orgAdminService;

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		String sessionId=renderRequest.getRequestedSessionId();
		Object roleObject =	SessionManager.getAttribute(sessionId, "role");
		String role = roleObject == null ?"":roleObject.toString();
		renderRequest.setAttribute("role", role);
		if(PartyOrgAdminTypeEnum.SECONDARY.getRole().equals(role)){
			renderSecondaryAdmin(sessionId,renderRequest);
		}
		super.doView(renderRequest, renderResponse);
	}

	/**
	 * 二、二级党组织管理员角色
	 * 【1】党支部个数；
	 * 【2】党员个数；
	 * 【3】支部组织生活开展次数；（柱状图或折线图）
	 * 【4】组织生活类型分布；（饼图）-------------------当前二级党组织范围统计-----------------
	 * 【5】党员性别分布；（饼图）-------------------当前二级党组织范围统计-----------------
	 */
	private void renderSecondaryAdmin(String sessionId,RenderRequest renderRequest){
		String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
		UserStatistics userStatistics = new UserStatistics();//党员统计
		int branchNumber = 0; //当支部总数
		//支部组织生活
		List<BaseStatistics> activitiesStatisticsList = new ArrayList<>();
		//活动类型总统计
		List<BaseStatistics> activitiesTypeStatisticList = new ArrayList<>();
		if(!StringUtils.isEmpty(userId)){
			OrgAdmin orgAdmin = orgAdminService.findOrgAdmin(userId, PartyOrgAdminTypeEnum.SECONDARY);
			if(orgAdmin != null && orgAdmin.getOrgId() != null){
				userStatistics = partyOrgServer.userSecondaryStatistics(orgAdmin.getOrgId());
				branchNumber = partyOrgServer.branchNumber(orgAdmin.getOrgId());
				activitiesStatisticsList = partyOrgServer.activitiesStatistics(0,0,orgAdmin.getOrgId());
				activitiesTypeStatisticList = partyOrgServer.activitiesTypeStatistic(0,0,orgAdmin.getOrgId());
			}
		}
		try {
			renderRequest.setAttribute("branchNumber", branchNumber);
			renderRequest.setAttribute("activitiesTypeStatisticList",JSON.toJSONString(activitiesTypeStatisticList));
			renderRequest.setAttribute("activitiesStatisticsList",JSON.toJSONString(activitiesStatisticsList));
			renderRequest.setAttribute("userStatistics", userStatistics);//党员统计
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}



