package party.portlet.cqu.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import hg.party.entity.party.MeetingStatistics;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import party.portlet.cqu.entity.Place;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(immediate = true,service = StatisticsDao.class)
public class StatisticsDao extends PostgresqlDaoImpl<Place> {

    public List<Map<String, Object>> countOrg() {
        String sql = "select org_type, count(*) as count from hg_party_org " +
                "where org_type != 'organization' and historic = false group by org_type";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> countOrgByDescType() {
        String sql = "select desc_type, count(*) as count from hg_party_org " +
                "where org_type != 'organization' and historic = false group by desc_type";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> countMemberByType() {
        String sql = "SELECT member_type, count(id) as c FROM hg_party_member where historic = false GROUP BY member_type";
        return jdbcTemplate.queryForList(sql);
    }

    public List<MeetingStatistics> secondaryMeetingStatistics() {
        String sql = "SELECT p.org_name_short as org_name, count(p.id) as branch_count FROM \"hg_party_org\" o " +
                "INNER JOIN hg_party_org p on o.org_parent = p.org_id " +
                "where o.historic = false and p.historic = false and p.org_type != 'organization' " +
                "GROUP BY p.org_name_short";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MeetingStatistics.class));
    }

    public List<Map<String, Object>> recentMeetings() {
        String sql = "SELECT o.org_name, p.submit_time, p.start_time from hg_party_meeting_plan_info p\n" +
                "LEFT JOIN hg_party_org o on p.organization_id = o.org_id\n" +
                "where task_status > '0' ORDER BY p.id asc LIMIT 20 OFFSET 0 ";
        return jdbcTemplate.queryForList(sql);
    }


    public Map<String, Object> countAllMeeting(String start, String end) {
        String sql = "SELECT COUNT\n" +
                "\t( DISTINCT plan.ID ) AS plan_count,\n" +
                "\tCOUNT ( DISTINCT plan.organization_id ) AS branch_count,\n" +
                "\tCOUNT ( DISTINCT M.participant_id ) AS member_count,\n" +
                "\t(\n" +
                "\tSELECT COUNT\n" +
                "\t\t( DISTINCT M.participant_id ) AS leader_count \n" +
                "\tFROM\n" +
                "\t\thg_party_meeting_plan_info plan\n" +
                "\t\tLEFT JOIN hg_party_meeting_member_info M ON plan.meeting_id = M.meeting_id\n" +
                "\t\tLEFT JOIN hg_party_member MEMBER ON M.participant_id = MEMBER.member_identity \n" +
                "\tWHERE\n" +
                "\t\tplan.task_status > '0' \n" +
                "\t\tAND ( MEMBER.member_is_leader = '是' AND MEMBER.member_is_leader IS NOT NULL ) \n" +
                "\t\tand plan.start_time >= ?::date and plan.start_time <= ?::date\n" +
                "\t) \n" +
                "FROM\n" +
                "\thg_party_meeting_plan_info plan\n" +
                "\tLEFT JOIN hg_party_meeting_member_info M ON plan.meeting_id = M.meeting_id\n" +
                "\tLEFT JOIN hg_party_member MEMBER ON M.participant_id = MEMBER.member_identity \n" +
                "WHERE\n" +
                "\tplan.task_status > '0'" +
                "\tand plan.start_time >= ?::date and plan.start_time <= ?::date";
        return jdbcTemplate.queryForMap(sql, start, end, start, end);
    }

    public Map<String, Object> countSecondaryMeeting(String orgId, String start, String end) {
        String sql = "SELECT COUNT\n" +
                "\t(distinct plan.ID ) AS plan_count,\n" +
                "\tCOUNT ( DISTINCT plan.organization_id ) AS branch_count,\n" +
                "\tCOUNT ( DISTINCT M.participant_id ) AS member_count,\n" +
                "\t(\tSELECT \n" +
                "\tCOUNT ( DISTINCT M.participant_id ) AS leader_count\n" +
                "FROM\n" +
                "\thg_party_meeting_plan_info plan\n" +
                "\tLEFT JOIN hg_party_org o ON plan.organization_id = o.org_id \n" +
                "\tLEFT JOIN hg_party_meeting_member_info M ON plan.meeting_id = M.meeting_id\n" +
                "\tLEFT JOIN hg_party_member MEMBER ON M.participant_id = MEMBER.member_identity \n" +
                "WHERE\n" +
                "\ttask_status > '0' and MEMBER.member_is_leader = '是'\n" +
                "\tand (o.org_id = ? or o.org_parent = ?)\n" +
                "\t\tand plan.start_time >= ?::date and plan.start_time <= ?::date)\n" +
                "FROM\n" +
                "\thg_party_meeting_plan_info plan\n" +
                "\tLEFT JOIN hg_party_org o ON plan.organization_id = o.org_id \n" +
                "\tLEFT JOIN hg_party_meeting_member_info M ON plan.meeting_id = M.meeting_id\n" +
                "\tLEFT JOIN hg_party_member MEMBER ON M.participant_id = MEMBER.member_identity \n" +
                "WHERE\n" +
                "\ttask_status > '0'\tand (o.org_id = ? or o.org_parent = ?)\n" +
                "\t\tand plan.start_time >= ?::date and plan.start_time <= ?::date\n" +
                "\t\n";
        return jdbcTemplate.queryForMap(sql, orgId, orgId, start, end, orgId, orgId, start, end);
    }

    public List<Map<String, Object>> countMember() {
        String sql = "select sec.org_name_short as name, count(m.id) as count from hg_party_member m left join hg_party_org brunch on m.member_org = brunch.org_id left join hg_party_org sec on brunch.org_parent = sec.org_id  where m.historic = false and brunch.historic = false group by sec.org_name_short";
        return  jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> countWeekLog() {
        String date = LocalDate.now().minusDays(6).toString();
        String sql = "SELECT date( visit_time),count(*) FROM hg_party_visit_count" +
                " where visit_time between '" + date + "' and CURRENT_TIMESTAMP and type = '登陆系统' GROUP BY date( visit_time)";
        return jdbcTemplate.queryForList(sql);
    }

    public Long countAllLog() {
        String sql = "SELECT count(*) FROM hg_party_visit_count  where type = '登陆系统'";
        return jdbcTemplate.queryForObject(sql, Long.class);
}

    public List<Map<String, Object>> countMeeting() {
        String sql = "SELECT count(id),campus from hg_party_meeting_plan_info WHERE campus " +
                "in ('A区', 'B区', 'C区', '虎溪校区') and task_status > '0' GROUP BY campus ORDER BY campus ";
        return jdbcTemplate.queryForList(sql);
    }
}
