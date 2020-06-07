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
