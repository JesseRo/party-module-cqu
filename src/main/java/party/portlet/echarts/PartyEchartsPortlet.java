package party.portlet.echarts;
import java.io.IOException;
import java.util.*;


import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import dt.session.SessionManager;
import hg.party.entity.party.BaseStatistics;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import hg.party.server.party.PartyOrgServer;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;
import hg.party.entity.party.UserStatistics;

/**
 * 文件名称： 报表<br>
 * 创建人 　： Yu Jiang Xia<br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=定制报表",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/echarts/partyEcharts.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PartyEcharts,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartyEchartsPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(PartyEchartsPortlet.class);
	@Reference
	private PartyOrgServer partyOrgServer;
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String sessionId=renderRequest.getRequestedSessionId();
		Object roleObject =	SessionManager.getAttribute(sessionId, "role");
		String role = roleObject == null ?"":roleObject.toString();
		renderRequest.setAttribute("role", role);
		logger.info("role:"+role);
		if(PartyOrgAdminTypeEnum.ORGANIZATION.getRole().equals(role)){
			renderOrgAdmin(renderRequest);
		}
		super.doView(renderRequest, renderResponse);
	}


	/**
	 * 三、校党委组织部管理员角色
	 * 【1】全校二级党组织个数；
	 * 【2】全校党支部个数；
	 * 【3】全校党员个数；
	 * 【4】二级党组织组织生活开展次数；（柱状图或折线图）
	 * 【5】组织生活类型分布；（饼图）-------------------全校范围统计-----------------
	 * 【6】党员性别分布；（饼图）-------------------全校范围统计-----------------
	 */
	private void renderOrgAdmin(RenderRequest renderRequest){
		int orgNumber = Integer.parseInt(String.valueOf(partyOrgServer.orgNumber().get(0).get("count")));//二级党组织个数
		int branchNumber = Integer.parseInt(String.valueOf(partyOrgServer.branchNumber().get(0).get("count")));//党支部个数
		UserStatistics userStatistics = partyOrgServer.userStatistics();//党员统计
		//学院活动总统计
		List<BaseStatistics> activitiesStatisticsList = partyOrgServer.activitiesStatistics(0,0);
		//活动类型总统计
		List<BaseStatistics> activitiesTypeStatisticList = partyOrgServer.activitiesTypeStatistic(0,0);
		try {
			renderRequest.setAttribute("orgNumber", orgNumber);
			renderRequest.setAttribute("branchNumber", branchNumber);
			renderRequest.setAttribute("activitiesTypeStatisticList",JSON.toJSONString(activitiesTypeStatisticList));
			renderRequest.setAttribute("activitiesStatisticsList",JSON.toJSONString(activitiesStatisticsList));
			renderRequest.setAttribute("userStatistics", userStatistics);//党员统计
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

