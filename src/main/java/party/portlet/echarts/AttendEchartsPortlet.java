package party.portlet.echarts;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.entity.party.BaseStatistics;
import hg.party.server.party.PartyOrgServer;
import hg.util.result.ResultUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
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
			"javax.portlet.display-name=出勤率统计",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/echarts/attendEcharts.jsp",
			"javax.portlet.name=" + PartyPortletKeys.AttendEcharts,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class AttendEchartsPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(AttendEchartsPortlet.class);
	@Reference
	private PartyOrgServer partyOrgServer;
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		int year = ParamUtil.getInteger(renderRequest, "year");
		int month = ParamUtil.getInteger(renderRequest, "month");
		List<BaseStatistics> collegeActivitiesStatisticsList = partyOrgServer.collegeActivitiesStatistics(year,month);
		renderRequest.setAttribute("collegeActivitiesStatisticsList",JSON.toJSONString(collegeActivitiesStatisticsList));
		super.doView(renderRequest, renderResponse);
	}

}

