package hg.party.server.memberMeeting;
/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月4日下午2:29:54<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */

import hg.party.dao.memberMeeting.MemberMeetingDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.MeetingStatistics;
import hg.party.server.organization.OrgService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;
import party.portlet.cqu.dao.StatisticsDao;
import party.portlet.transport.entity.PageQueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(immediate = true, service = MemberMeetingServer.class)
public class MemberMeetingServer {

    @Reference
    private MemberMeetingDao memberMeetingDao;

    @Reference
    private StatisticsDao statisticsDao;

    @Reference
    private OrgService orgService;

    public void findByMeetingIdAndUserId(String userId, String meetingId) {
        memberMeetingDao.findByMeetingIdAndUserId(userId, meetingId);
    }

    public PageQueryResult<MeetingStatistics> meetingStatisticsPage(int page, int size, int id, String name) {
        Organization organization = orgService.findOrgById(id);
        PageQueryResult<Map<String, Object>> result = memberMeetingDao.orgIdsPage(page, size, id, name);
        List<String> orgIds = result.getList().stream().map(p -> String.valueOf(p.get("org_id"))).collect(Collectors.toList());
        List<MeetingStatistics> statisticsResult;
        List<Map<String, Object>> joinStatisticsResult;
        if (organization.getOrg_type().equals(PartyOrgAdminTypeEnum.ORGANIZATION.getType())) {
            statisticsResult = memberMeetingDao.secondaryMeetingStatistics(orgIds);
            joinStatisticsResult = memberMeetingDao.secondaryMeetingJoinStatistics(orgIds);
        } else {
            statisticsResult = memberMeetingDao.branchMeetingStatistics(orgIds);
            joinStatisticsResult = memberMeetingDao.branchMeetingJoinStatistics(orgIds);
        }
        Map<String, MeetingStatistics> meetingStatisticsMap = statisticsResult.stream()
                .collect(Collectors.toMap(MeetingStatistics::getOrg_id, p -> p));
        Map<String, List<Map<String, Object>>> meetingJoinStatisticsMap = joinStatisticsResult.stream()
                .collect(Collectors.groupingBy(p -> String.valueOf(p.get("org_id"))));
        List<MeetingStatistics> statisticsList = new ArrayList<>();
        for (Map<String, Object> org : result.getList()) {
            String orgId = String.valueOf(org.get("org_id"));
            MeetingStatistics statistics = meetingStatisticsMap.get(orgId);
            if (statistics == null) {
                statistics = new MeetingStatistics();
                statistics.setId((int) org.get("id"));
                statistics.setOrg_id(orgId);
                statistics.setOrg_name(String.valueOf(org.get("org_name")));
                statistics.setOrg_secretary(String.valueOf(org.get("org_secretary")));
                statistics.setOrg_type(String.valueOf(org.get("org_type")));
                statistics.setPlan_count(0);
                statistics.setBranch_count(0);
            }
            List<Map<String, Object>> joinMaps = meetingJoinStatisticsMap.get(orgId);
            if (joinMaps != null) {
                for (Map<String, Object> map : joinMaps) {
                    if ((boolean) map.get("leader")) {
                        statistics.setLeader_count((long) map.getOrDefault("count", 0));
                    } else {
						statistics.setMember_count((long) map.getOrDefault("count", 0));
					}
                }
            }

            if (organization.getOrg_id().equals(orgId)) {
                Map<String, Object> map = null;
                if (organization.getOrg_type().equals(PartyOrgAdminTypeEnum.ORGANIZATION.getType())) {
                    map = statisticsDao.countAllMeeting();
                } else if (organization.getOrg_type().equals(PartyOrgAdminTypeEnum.SECONDARY.getType())) {
                    map = statisticsDao.countSecondaryMeeting(orgId);
                }
                if (map != null) {
					statistics.setBranch_count((long) map.get("branch_count"));
					statistics.setPlan_count((long) map.get("plan_count"));
					statistics.setLeader_count((long) map.get("leader_count"));
					statistics.setMember_count((long) map.get("member_count"));
				}
            }
            statisticsList.add(statistics);
        }

		return new PageQueryResult<>(statisticsList, result.getCount(), result.getPageNow(), result.getPageSize());
    }
}
