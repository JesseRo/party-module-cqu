package party.portlet.transport.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import party.portlet.report.entity.Report;
import party.portlet.transport.entity.Retention;
import party.portlet.transport.entity.Transport;

import java.util.Map;

@Component(immediate = true,service = RetentionDao.class)
public class RetentionDao extends PostgresqlDaoImpl<Retention> {

    public Retention findById(String retentionId) {
        String sql = "select * from hg_party_retention where retention_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Retention.class), retentionId);
        } catch (Exception e) {
            return null;
        }
    }

    public PostgresqlQueryResult<Map<String, Object>> findSecondaryPage(int page, int size, String orgId) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_parent = ? order by t.status asc";
        if (size <= 0){
            size = 10;
        }
        try {
            return postGresqlFindBySql(page, size, sql, orgId);
        } catch (Exception e) {
            return null;
        }
    }

    public PostgresqlQueryResult<Map<String, Object>> findRootPage(int page, int size) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " order by status asc";
        if (size <= 0){
            size = 10;
        }
        try {
            return postGresqlFindBySql(page, size, sql);
        } catch (Exception e) {
            return null;
        }
    }

    public Retention findByUser(String user) {
        try {
            String sql = "select * from hg_party_retention where user_id = ?";
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Retention.class), user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
  }

    public PostgresqlQueryResult<Map<String, Object>> findBrunchPage(int page, int size, String orgId) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_id = ? order by t.status asc";
        if (size <= 0){
            size = 10;
        }
        try {
            return postGresqlFindBySql(page, size, sql, orgId);
        } catch (Exception e) {
            return null;
        }
    }
}
