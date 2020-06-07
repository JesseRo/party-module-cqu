package party.portlet.transport.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;
import party.portlet.transport.entity.PageQueryResult;
import party.portlet.transport.entity.Transport;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component(immediate = true, service = TransportDao.class)
public class TransportDao extends PostgresqlDaoImpl<Transport> {
    @Reference
    private RetentionDao retentionDao;

    public Transport findById(String transportId) {
        String sql = "select * from hg_party_transport where transport_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Transport.class), transportId);
        } catch (Exception e) {
            return null;
        }
    }

    public Transport findByUser(String userId) {
        String sql = "select * from hg_party_transport where user_id = ? and not (status = 5  or (status = 1 and type != '3'))";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Transport.class), userId);
        } catch (Exception e) {
            return null;
        }
    }

    public Long countByUser(String userId) {
        String sql = "select count (*) from hg_party_transport where user_id = ? and not (status = 5 or (status = 1 and type != '3') or status = 2)";
        return jdbcTemplate.queryForObject(sql, Long.class, userId);

    }

    public PageQueryResult<Map<String, Object>> findSecondaryPage(int page, int size, String orgId, List<String> type,
                                                                  String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_fax, o.org_contactor_phone, o.org_address, p.org_name as second_name," +
                " extract(year from age(cast(m.member_birthday as date))) as age, org.org_name as current_org_name " +
                " from hg_party_transport t " +
                " left join hg_party_member m on m.member_identity = t.user_id" +
                " left join hg_party_org o on o.org_id = m.member_org" +
                " left join hg_party_org p on p.org_id = o.org_parent" +
                " left join hg_party_org org on org.org_id = t.current_approve_org" +
                " where (" +
                "t.approved_list::jsonb @> '\"" + orgId + "\"'::jsonb" +
                " or t.current_approve_org = ?)";
        List<Object> params = new ArrayList<>();
        params.add(orgId);
        if (completed) {
            sql += " and (t.status = 4 or (t.status = 1 and t.type != '3'))";
        }
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (type != null && type.size() > 0) {
            sql += " and t.type in ('" + String.join("','", type) + "')";
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
            return retentionDao.pageBySql(page, size, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> findSecondary(String orgId, List<String> type, String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_fax, o.org_contactor_phone, o.org_address, p.org_name as second_name," +
                " extract(year from age(cast(m.member_birthday as date))) as age" +
                " from hg_party_transport t " +
                " left join hg_party_member m on m.member_identity = t.user_id" +
                " left join hg_party_org o on o.org_id = m.member_org" +
                " left join hg_party_org p on p.org_id = o.org_parent" +
                " where (" +
                "t.approved_list::jsonb @> '\"" + orgId + "\"'::jsonb" +
                " or t.current_approve_org = ?)";
        List<Object> params = new ArrayList<>();
        params.add(orgId);
        if (completed) {
            sql += " and (t.status = 4 or (t.status = 1 and t.type != '3'))";
        }
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (type != null && type.size() > 0) {
            sql += " and t.type in ('" + String.join("','", type) + "')";
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

    public PageQueryResult<Map<String, Object>> findRootPage(int page, int size, List<String> type, String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_fax, o.org_contactor_phone, o.org_address,p.org_name as second_name," +
                " extract(year from age(cast(m.member_birthday as date))) as age, org.org_name as current_org_name" +
                " from hg_party_transport t " +
                " left join hg_party_member m on m.member_identity = t.user_id" +
                " left join hg_party_org o on o.org_id = m.member_org" +
                " left join hg_party_org p on p.org_id = o.org_parent" +
                " left join hg_party_org org on org.org_id = t.current_approve_org" +
                " where t.type in ('2', '3')";
        List<Object> params = new ArrayList<>();
        if (completed) {
            sql += " and (t.status = 4 or (t.status = 1 and t.type != '3'))";
        }
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (type != null && type.size() > 0) {
            sql += " and t.type in ('" + String.join("','", type) + "')";
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
            return retentionDao.pageBySql(page, size, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> findRoot(List<String> type, String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_fax, o.org_contactor_phone, o.org_address,p.org_name as second_name," +
                " extract(year from age(cast(m.member_birthday as date))) as age" +
                " from hg_party_transport t " +
                " left join hg_party_member m on m.member_identity = t.user_id" +
                " left join hg_party_org o on o.org_id = m.member_org" +
                " left join hg_party_org p on p.org_id = o.org_parent" +
                " where t.type in ('2', '3')";
        List<Object> params = new ArrayList<>();
        if (completed) {
            sql += " and (t.status = 4 or (t.status = 1 and t.type != '3'))";
        }
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (type != null && type.size() > 0) {
            sql += " and t.type in ('" + String.join("','", type) + "')";
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

    public PageQueryResult<Map<String, Object>> findBranchPage(int page, int size, String orgId, List<String> type, String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_fax, o.org_contactor_phone, o.org_address,p.org_name as second_name," +
                " extract(year from age(cast(m.member_birthday as date))) as age, org.org_name as current_org_name " +
                " from hg_party_transport t " +
                " left join hg_party_member m on t.user_id = m.member_identity" +
                " left join hg_party_org o on o.org_id = m.member_org" +
                " left join hg_party_org p on p.org_id = o.org_parent" +
                " left join hg_party_org org on org.org_id = t.current_approve_org" +
                " where (" +
//                "m.member_org = ? or " +
                "t.approved_list::jsonb @> '\"" + orgId + "\"'::jsonb " +
                " or t.current_approve_org = ?)";
        List<Object> params = new ArrayList<>();
        params.add(orgId);
        if (completed) {
            sql += " and (t.status = 4 or (t.status = 1 and t.type != '3'))";
        }
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (type != null && type.size() > 0) {
            sql += " and t.type in ('" + String.join("','", type) + "')";
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
            return retentionDao.pageBySql(page, size, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> findBranch(String orgId, List<String> type, String name, boolean completed, String startDate, String endDate) {
        String sql = "select t.*, o.org_fax, o.org_contactor_phone, o.org_address,p.org_name as second_name," +
                " extract(year from age(cast(m.member_birthday as date))) as age" +
                " from hg_party_transport t " +
                " left join hg_party_member m on t.user_id = m.member_identity" +
                " left join hg_party_org o on o.org_id = m.member_org" +
                " left join hg_party_org p on p.org_id = o.org_parent" +
                " where (" +
//                "m.member_org = ? or " +
                "t.approved_list::jsonb @> '\"" + orgId + "\"'::jsonb " +
                " or t.current_approve_org = ?)";
        List<Object> params = new ArrayList<>();
        params.add(orgId);
        if (completed) {
            sql += " and (t.status = 4 or (t.status = 1 and t.type != '3'))";
        }
        if (!StringUtils.isEmpty(name)) {
            sql += " and (t.user_name like ? or o.org_name like ?)";
            params.add(name);
            params.add(name);
        }
        if (type != null && type.size() > 0) {
            sql += " and t.type in ('" + String.join("','", type) + "')";
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
}
