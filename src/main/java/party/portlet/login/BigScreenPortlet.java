package party.portlet.login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.party.MeetingStatistics;
import hg.util.ConstantsKey;
import hg.util.PropertiesUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.cqu.dao.StatisticsDao;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=大屏幕",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/login/screen.jsp",
//                "javax.portlet.init-param.edit-template=/jsp/login/edit.jsp",
                "javax.portlet.name=" + PartyPortletKeys.BigScreenPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class BigScreenPortlet extends MVCPortlet {
    @Reference
    private StatisticsDao dao;

    private Gson gson = new GsonBuilder().setDateFormat("MM-dd HH:mm").create();

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String sessionId = renderRequest.getRequestedSessionId();
        String role = (String) SessionManager.getAttribute(sessionId, "role");
        String url;
        if (ConstantsKey.SECOND_PARTY.equals(role)) {
            url = "/backlogtwo";
        } else if (ConstantsKey.ORG_PARTY.equals(role)) {
            url = "/statisticalreport";
        } else if (ConstantsKey.BRANCH_PARTY.equals(role)) {
            url = "/task";
        } else {
            url = "/home";
        }

        List<Map<String, Object>> orgCounts = dao.countOrg();
        for (Map<String, Object> orgCount : orgCounts) {
            if (orgCount.get("org_type").equals(ConstantsKey.ORG_TYPE_BRANCH)) {
                renderRequest.setAttribute("brunchCount", orgCount.get("count"));
            } else if (orgCount.get("org_type").equals(ConstantsKey.ORG_TYPE_SECONDARY)) {
                renderRequest.setAttribute("secondaryCount", orgCount.get("count"));
            }
        }

        List<Map<String, Object>> memberCounts = dao.countMember();
        List<MeetingStatistics> statisticsResult = dao.secondaryMeetingStatistics();
        Map<String, Long> meetingStatisticsMap = statisticsResult.stream()
                .collect(Collectors.toMap(MeetingStatistics::getOrg_name, MeetingStatistics::getBranch_count));

        int allMemberCount = 0;
        List<String> secondaryNames = new ArrayList<>();
        List<Long> secondaryMemberCounts = new ArrayList<>();

        List<List<String>> secNameGroup = new ArrayList<>();
        List<List<Long>> secCountGroup = new ArrayList<>();
        List<List<Long>> secMeetingCountGroup = new ArrayList<>();
        List<String> secNames = new ArrayList<>();
        List<Long> secCounts = new ArrayList<>();
        List<Long> secMeetingCounts = new ArrayList<>();
        for (int i = 0; i < memberCounts.size(); i++) {
            if (i % 12 == 0) {
                secNames = new ArrayList<>();
                secCounts = new ArrayList<>();
                secCountGroup.add(secCounts);
                secNameGroup.add(secNames);
                secMeetingCountGroup.add(secMeetingCounts);
            }
            Map<String, Object> memberCount = memberCounts.get(i);
            String orgName = (String) memberCount.get("name");
            allMemberCount += (Long) memberCount.get("count");
            secondaryNames.add(orgName);
            secondaryMemberCounts.add((Long) memberCount.get("count"));
            secNames.add(orgName);
            secCounts.add((Long) memberCount.get("count"));
            secMeetingCounts.add(meetingStatisticsMap.getOrDefault(orgName, 0L));
        }
        renderRequest.setAttribute("memberGroups", secNameGroup);
        renderRequest.setAttribute("secNameGroup", gson.toJson(secNameGroup));
        renderRequest.setAttribute("secCountGroup", gson.toJson(secCountGroup));
        renderRequest.setAttribute("secMeetingCountGroup", gson.toJson(secMeetingCountGroup));
        renderRequest.setAttribute("maxSecCount", secondaryMemberCounts.stream().mapToLong(p -> p).max().getAsLong());
        renderRequest.setAttribute("allMemberCount", allMemberCount);



        List<Map<String, Object>> meetingCounts = dao.countMeeting();
        Map<String, Long> campusMeetingCounts = new HashMap<>();
        Map<String, String> campusMeetingPercentage = new HashMap<>();
        long total = 0L;
        for (Map<String, Object> memberCount : meetingCounts) {
            long count = (Long) memberCount.get("count");
            total += count;
            campusMeetingCounts.put((String) memberCount.get("campus"), count);
        }

        for (Map.Entry<String, Long> c : campusMeetingCounts.entrySet()) {
            campusMeetingPercentage.put(c.getKey(), String.format("%.1f", (((float)c.getValue()) / total) * 100));
        }
        renderRequest.setAttribute("campusMeetingCounts", gson.toJson(campusMeetingCounts));
        renderRequest.setAttribute("campusMeetingPercentage", gson.toJson(campusMeetingPercentage));

        List<Map<String, Object>> meetings = dao.recentMeetings();
        renderRequest.setAttribute("meetings", gson.toJson(meetings));



        String []weekDays = new String[]{"", "星期一", "星期二", "星期三", "星期四", "星期五","星期六", "星期日"};
        List<Map<String, Object>> logCounts = dao.countWeekLog();
        Long allLog = dao.countAllLog();
        List<String> weekdays = new ArrayList<>();
        List<Long> weekdayCounts = new ArrayList<>();
        outerLoop:
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            String dateOfWeek = weekDays[date.getDayOfWeek().getValue()];
            weekdays.add(dateOfWeek);
            for (Map<String, Object> count : logCounts) {
                if (count.get("date").toString().equals(date.toString())) {
                    weekdayCounts.add((Long) count.get("count"));
                    continue outerLoop;
                }
            }
            weekdayCounts.add(0L);
        }
        String percents = "+0%";
        if (weekdayCounts.get(5) != 0) {
            float a = ((float) (weekdayCounts.get(6) - weekdayCounts.get(5))) / weekdayCounts.get(5);
            percents = String.format("%+.1f", a * 100) + "%";
        }
        renderRequest.setAttribute("percents", percents);
        renderRequest.setAttribute("weekdays", gson.toJson(weekdays));
        renderRequest.setAttribute("weekdayCounts", gson.toJson(weekdayCounts));
        renderRequest.setAttribute("totalVisit", allLog);
        renderRequest.setAttribute("totalVisit", allLog);
        renderRequest.setAttribute("turnTo", url);
        super.doView(renderRequest, renderResponse);
    }
}
