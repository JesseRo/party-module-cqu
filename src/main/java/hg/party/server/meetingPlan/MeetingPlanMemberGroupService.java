package hg.party.server.meetingPlan;

import hg.party.dao.meetingPlan.MeetingPlanMemberGroupDao;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * 活动计划 党员分组
 */
@Component(immediate = true, service = MeetingPlanMemberGroupService.class)
public class MeetingPlanMemberGroupService {
    @Reference
    private MeetingPlanMemberGroupDao meetingPlanMemberGroupDao;
    public PostgresqlPageResult<Map<String, Object>> searchMemberGroupPage(int page, int size, String orgId, String keyword) {
        return meetingPlanMemberGroupDao.searchMemberGroupPage(page, size, orgId, keyword);
    }
}
