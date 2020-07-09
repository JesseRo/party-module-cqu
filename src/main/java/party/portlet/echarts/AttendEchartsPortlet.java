package party.portlet.echarts;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.BaseStatistics;
import hg.party.server.party.PartyOrgServer;
import hg.util.ConstantsKey;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
                "javax.portlet.display-name=学院党组织活动统计",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/echarts/attendEcharts.jsp",
                "javax.portlet.name=" + PartyPortletKeys.AttendEcharts,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class AttendEchartsPortlet extends MVCPortlet {
    Logger logger = Logger.getLogger(AttendEchartsPortlet.class);
    @Reference
    private PartyOrgServer partyOrgServer;
    @Reference
    private OrgDao orgDao;

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        logger.info("year=" + year + "  moth=" + month);
        calendar.set(year - 1, month, 1);
        Timestamp startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
        calendar.set(year, month + 1, 1);//选择的截止月份计算在内
        Timestamp endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
        String orgId = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
        Organization organization = orgDao.findByOrgId(orgId);
        String orgType = organization.getOrg_type();
        List<BaseStatistics> collegeActivitiesStatisticsList;
        if (orgType.equals(ConstantsKey.ORG_TYPE_SECONDARY)) {
            collegeActivitiesStatisticsList = partyOrgServer.searchActivitiesStatistics(startTime, endTime, orgId);
        } else {
            collegeActivitiesStatisticsList = partyOrgServer.searchActivitiesStatistics(startTime, endTime);
        }

        renderRequest.setAttribute("collegeActivitiesStatisticsList", JSON.toJSONString(collegeActivitiesStatisticsList));
        //页面起止日期格式：2020-01 - 2020-01
        String dateStr = new StringBuffer().append(year - 1).append("-").append(month).append(" - ").append(year).append("-").append(month + 1).toString();
        renderRequest.setAttribute("dateStr", dateStr);
        super.doView(renderRequest, renderResponse);
    }

}

