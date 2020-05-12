package party.portlet.transport.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;
import party.portlet.report.entity.Report;
import party.portlet.transport.entity.PageQueryResult;
import party.portlet.transport.entity.Retention;
import party.portlet.transport.entity.Transport;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(immediate = true, service = RetentionDao.class)
public class RetentionDao extends PostgresqlDaoImpl<Retention> {

    public Retention findById(String retentionId) {
        String sql = "select * from hg_party_retention where retention_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Retention.class), retentionId);
        } catch (Exception e) {
            return null;
        }
    }

    public PageQueryResult<Map<String, Object>> findSecondaryPage(int page, int size, String orgId, String name) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_parent = ? order by t.status asc";
        if (!StringUtils.isEmpty(name)) {
            sql += " and t.user_name like ?";
        }
        if (size <= 0) {
            size = 10;
        }
        try {
            if (!StringUtils.isEmpty(name)) {
                return pageBySql(page, size, sql, orgId, name);
            }
            return pageBySql(page, size, sql, orgId);
        } catch (Exception e) {
            return null;
        }
    }


    public List<Map<String, Object>> findSecondary(String orgId, String name) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_parent = ? order by t.status asc";
        if (!StringUtils.isEmpty(name)) {
            sql += " and t.user_name like ?";
        }
        try {
            if (!StringUtils.isEmpty(name)) {
                return jdbcTemplate.queryForList(sql, orgId, name);
            }
            return jdbcTemplate.queryForList(sql, orgId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PageQueryResult<Map<String, Object>> findRootPage(int page, int size, String name) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " order by status asc";
        if (!StringUtils.isEmpty(name)) {
            sql += " and t.user_name like ?";
        }
        if (size <= 0) {
            size = 10;
        }
        try {
            if (!StringUtils.isEmpty(name)) {
                return pageBySql(page, size, sql, name);
            }
            return pageBySql(page, size, sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> findRoot(String name) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " order by status asc";
        if (!StringUtils.isEmpty(name)) {
            sql += " and t.user_name like ?";
        }
        try {
            if (!StringUtils.isEmpty(name)) {
                return jdbcTemplate.queryForList(sql, name);
            }
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Retention findByUser(String user) {
        try {
            String sql = "select * from hg_party_retention where user_id = ? and status < 5";
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Retention.class), user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> findBrunch(String orgId, String name) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_id = ? order by t.status asc";
        if (!StringUtils.isEmpty(name)) {
            sql += " and t.user_name like ?";
        }
        try {
            if (!StringUtils.isEmpty(name)) {
                return jdbcTemplate.queryForList(sql, orgId, name);
            }
            return jdbcTemplate.queryForList(sql, orgId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PageQueryResult<Map<String, Object>> findBrunchPage(int page, int size, String orgId, String name) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_id = ? order by t.status asc";
        if (!StringUtils.isEmpty(name)) {
            sql += " and t.user_name like ?";
        }
        try {
            if (!StringUtils.isEmpty(name)) {
                return pageBySql(page, size, sql, orgId, name);
            }
            return pageBySql(page, size, sql, orgId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PageQueryResult<Map<String, Object>> pageBySql(int pageNow, int pageSize, String sql, Object... object) {
        if (pageNow <= 0) {
            pageNow = 0;
        } else {
            --pageNow;
        }

        List<Map<String, Object>> list = this.listBySql(pageNow, pageSize, sql, object);
        int count = this.postGresql_countBySql(sql, object);
        return new PageQueryResult(list, count, pageNow, pageSize);
    }

    private List<Map<String, Object>> listBySql(int pageNo, int pageSize, String sql, Object... objects) {
        StringBuffer exeSql = new StringBuffer();
        exeSql.append(sql);
        exeSql.append(" LIMIT ? OFFSET ? ");
        if (objects != null && objects.length != 0) {
            List list = Arrays.stream(objects).collect(Collectors.toList());
            list.add(pageSize);
            list.add(pageNo * pageSize);
            return this.jdbcTemplate.queryForList(exeSql.toString(), list.toArray(new Object[0]));
        } else {
            return this.jdbcTemplate.queryForList(exeSql.toString(), new Object[]{pageSize, pageNo * pageSize});
        }
    }
}
