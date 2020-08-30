package party.portlet.cqu.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import hg.party.entity.party.MeetingPlan;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;
import party.log.Log;
import party.portlet.cqu.entity.CheckPerson;

import java.util.List;
import java.util.Map;

@Component(immediate = true,service = CheckPersonDao.class)
public class CheckPersonDao extends PostgresqlDaoImpl<CheckPerson> {

    public PostgresqlQueryResult<Map<String, Object>> searchPage(int page, int pageSize, String search) {
        page = Math.max(page, 0);
        pageSize = pageSize <= 0 ? 10 : pageSize;
        try {
            if (!StringUtils.isEmpty(search)){
                String sql = "select c.*, member.member_name,member.job_number member_code, brunch.org_name, sec.org_name as sec_name  " +
                        "from hg_party_check_person c " +
                        "left join hg_party_member member on member.member_identity = c.user_id " +
                        "left join hg_party_org brunch on brunch.org_id = member.member_org " +
                        "left join hg_party_org sec on sec.org_id = brunch.org_parent " +
                        " where campus = ? " +
                        "order by campus asc, id desc";
                return postGresqlFindBySql(page, pageSize, sql, search);
            }else {
                String sql = "select c.*, member.member_name,member.job_number member_code, brunch.org_name, sec.org_name as sec_name" +
                        " from hg_party_check_person c " +
                        "left join hg_party_member member on member.member_identity = c.user_id " +
                        "left join hg_party_org brunch on brunch.org_id = member.member_org " +
                        "left join hg_party_org sec on sec.org_id = brunch.org_parent " +
                        "order by campus asc, id desc";
                return postGresqlFindBySql(page, pageSize, sql);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> getCampusPerson(String campus) {
        String sql = "select o.org_name, c.user_id, m.member_name from hg_party_check_person c " +
                "inner join hg_party_member m on c.user_id = m.member_identity and m.historic = false " +
                "inner join hg_party_org o on m.member_org = o.org_id where campus = ?";
        return jdbcTemplate.queryForList(sql,  campus);
    }

    public CheckPerson getByUser(String check_person) {
        String sql = "select * from hg_party_check_person where user_id = ?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(CheckPerson.class), check_person);
    }


    public void addCount(String check_person) {
        String sql = "update hg_party_check_person set count = count + 1 where user_id = ?";
        jdbcTemplate.update(sql, check_person);
    }
}
