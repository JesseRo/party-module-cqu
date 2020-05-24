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

import java.util.ArrayList;
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

    public PageQueryResult<Map<String, Object>> findSecondaryPage(int page, int size, String orgId, String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_parent = ?";
        List<Object> params = new ArrayList<>();
        params.add(orgId);
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (completed) {
            sql += " and t.status = 1";
        }
        if (!StringUtils.isEmpty(startDate)) {
            sql += " and time > ?::DATE ";
            params.add(startDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            sql += " and time < ?::DATE ";
            params.add(endDate);
        }
        if (size <= 0) {
            size = 10;
        }
        sql += " order by t.id desc";
        try {
            return pageBySql(page, size, sql, params);
        } catch (Exception e) {
            return null;
        }
    }


    public List<Map<String, Object>> findSecondary(String orgId, String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_parent = ?";
        List<Object> params = new ArrayList<>();
        params.add(orgId);
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (completed) {
            sql += " and t.status = 1";
        }
        if (!StringUtils.isEmpty(startDate)) {
            sql += " and time > ?::DATE ";
            params.add(startDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            sql += " and time < ?::DATE ";
            params.add(endDate);
        }
        try {
            return jdbcTemplate.queryForList(sql, params.toArray(new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PageQueryResult<Map<String, Object>> findRootPage(int page, int size, String name,  boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id " +
                "where 1 = 1";
        List<Object> params = new ArrayList<>();
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (completed) {
            sql += " and t.status = 1";
        }
        if (!StringUtils.isEmpty(startDate)) {
            sql += " and time > ?::DATE ";
            params.add(startDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            sql += " and time < ?::DATE ";
            params.add(endDate);
        }
        if (size <= 0) {
            size = 10;
        }
        sql += " order by t.id desc";
        try {
            return pageBySql(page, size, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> findRoot(String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where 1 = 1";
        List<Object> params = new ArrayList<>();
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (completed) {
            sql += " and t.status = 1";
        }
        if (!StringUtils.isEmpty(startDate)) {
            sql += " and time > ?::DATE ";
            params.add(startDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            sql += " and time < ?::DATE ";
            params.add(endDate);
        }
        sql += " order by t.id desc";
        try {
            return jdbcTemplate.queryForList(sql, params.toArray(new Object[0]));
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

    public List<Map<String, Object>> findBrunch(String orgId, String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(orgId);
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (completed) {
            sql += " and t.status = 1";
        }
        if (!StringUtils.isEmpty(startDate)) {
            sql += " and time > ?::DATE ";
            params.add(startDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            sql += " and time < ?::DATE ";
            params.add(endDate);
        }
        sql += " order by t.id desc";
        try {
            return jdbcTemplate.queryForList(sql, params.toArray(new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PageQueryResult<Map<String, Object>> findBrunchPage(int page, int size, String orgId, String name,boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_name from hg_party_retention t " +
                "left join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(orgId);
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (completed) {
            sql += " and t.status = 1";
        }
        if (!StringUtils.isEmpty(startDate)) {
            sql += " and time > ?::DATE ";
            params.add(startDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            sql += " and time < ?::DATE ";
            params.add(endDate);
        }
        sql += " order by t.id desc";
        if (size <= 0) {
            size = 10;
        }
        try {
            return pageBySql(page, size, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PageQueryResult<Map<String, Object>> pageBySql(int pageNow, int pageSize, String sql, List<Object> objects) {
        if (pageNow <= 0) {
            pageNow = 0;
        } else {
            --pageNow;
        }

        List<Map<String, Object>> list = this.listBySql(pageNow, pageSize, sql, objects);
        int count = this.countBySql(sql, objects);
        return new PageQueryResult(list, count, pageNow, pageSize);
    }


    public int countBySql(String sql, List<Object> objects) {
        List<Map<String, Object>> maps = this.jdbcTemplate.queryForList(sql, objects.toArray(new Object[0]));
        return null == maps ? 0 : maps.size();
    }
    private List<Map<String, Object>> listBySql(int pageNo, int pageSize, String sql, List<Object> objects) {
        StringBuffer exeSql = new StringBuffer();
        exeSql.append(sql);
        exeSql.append(" LIMIT ? OFFSET ? ");
        if (objects != null && objects.size() != 0) {
            List list = new ArrayList();
            list.addAll(objects);
            list.add(pageSize);
            list.add(pageNo * pageSize);
            return this.jdbcTemplate.queryForList(exeSql.toString(), list.toArray(new Object[0]));
        } else {
            return this.jdbcTemplate.queryForList(exeSql.toString(), new Object[]{pageSize, pageNo * pageSize});
        }
    }
}
