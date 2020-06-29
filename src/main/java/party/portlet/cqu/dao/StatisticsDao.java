package party.portlet.cqu.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import party.portlet.cqu.entity.Place;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> countAllMeeting() {
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
                "\t\tplan.task_status_org > '0' \n" +
                "\t\tAND ( MEMBER.member_is_leader = '是' AND MEMBER.member_is_leader IS NOT NULL ) \n" +
                "\t) \n" +
                "FROM\n" +
                "\thg_party_meeting_plan_info plan\n" +
                "\tLEFT JOIN hg_party_meeting_member_info M ON plan.meeting_id = M.meeting_id\n" +
                "\tLEFT JOIN hg_party_member MEMBER ON M.participant_id = MEMBER.member_identity \n" +
                "WHERE\n" +
                "\tplan.task_status_org > '0'";
        return jdbcTemplate.queryForMap(sql);
    }

    public Map<String, Object> countSecondaryMeeting(String orgId) {
        String sql = "SELECT COUNT\n" +
                "\t( plan.ID ) AS plan_count,\n" +
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
                "\ttask_status_org > '0' and MEMBER.member_is_leader = '是'\n" +
                "\tand (o.org_id = ? or o.org_parent = ?))\n" +
                "FROM\n" +
                "\thg_party_meeting_plan_info plan\n" +
                "\tLEFT JOIN hg_party_org o ON plan.organization_id = o.org_id \n" +
                "\tLEFT JOIN hg_party_meeting_member_info M ON plan.meeting_id = M.meeting_id\n" +
                "\tLEFT JOIN hg_party_member MEMBER ON M.participant_id = MEMBER.member_identity \n" +
                "WHERE\n" +
                "\ttask_status_org > '0'\tand (o.org_id = ? or o.org_parent = ?)\n" +
                "\t\n";
        return jdbcTemplate.queryForMap(sql, orgId, orgId, orgId, orgId);
    }

    public List<Map<String, Object>> countMember() {
        String sql = "select sec.org_name as name, count(m.id) as count from hg_party_member m left join hg_party_org brunch on m.member_org = brunch.org_id left join hg_party_org sec on brunch.org_parent = sec.org_id  where m.historic = false and brunch.historic = false group by sec.org_name";
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
}
