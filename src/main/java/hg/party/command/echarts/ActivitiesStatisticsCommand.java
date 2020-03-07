package hg.party.command.echarts;


import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.entity.party.BaseStatistics;
import hg.party.server.party.PartyOrgServer;
import hg.util.result.ResultUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/**
 * 学院活动统计
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.AttendEcharts,
			"mvc.command.name=/hg/part/collegeActivitiesStatistics"
	    },
	    service = MVCResourceCommand.class
)
public class ActivitiesStatisticsCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(ActivitiesStatisticsCommand.class);
	@Reference
	private PartyOrgServer partyOrgServer;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String start = ParamUtil.getString(resourceRequest, "startTime");
		String end = ParamUtil.getString(resourceRequest, "endTime");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		try {
			Date startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			Timestamp startTime = Timestamp.valueOf(formatter.format(startDate));
			Timestamp endTime = Timestamp.valueOf(formatter.format(endDate));
			PrintWriter printWriter=resourceResponse.getWriter();
			logger.info("查询党支部活动日期统计");
			List<BaseStatistics> collegeActivitiesStatistics = partyOrgServer.searchActivitiesStatistics(startTime,endTime);
			printWriter.write(JSON.toJSONString(ResultUtil.success(collegeActivitiesStatistics)));

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}


}