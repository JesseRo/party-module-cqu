package party.portlet.transport.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;
import party.portlet.report.entity.Report;
import party.portlet.transport.entity.Transport;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component(immediate = true, service = TransportDao.class)
public class TransportDao extends PostgresqlDaoImpl<Transport> {

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

    public PostgresqlQueryResult<Map<String, Object>> findSecondaryPage(int page, int size, String orgId, List<String> type, String name) {
        String sql = "select t.*, o.org_fax, o.org_contactor_phone, o.org_address, extract(year from age(cast(m.member_birthday as date))) as age" +
                " from hg_party_transport t " +
                " left join hg_party_member m on m.member_identity = t.user_id and m.historic = false" +
                " left join hg_party_org o on o.org_id = m.member_org" +
                " where ((o.org_parent = ? and t.type in ('2', '3')) or t.approved_list::jsonb @> '\"" + orgId + "\"'::jsonb" +
                " or t.current_approve_org = ?)";
        if (!StringUtils.isEmpty(name)){
            sql += " and t.user_name like ?";
        }
        if (type != null && type.size() > 0) {
            sql += " and t.type in ('" + String.join("','", type) + "')";
        }
        sql += " order by t.status asc";
        if (size <= 0) {
            size = 10;
        }
        try {
            if (!StringUtils.isEmpty(name)){
                return postGresqlFindBySql(page, size, sql, orgId, orgId, name);
            }
            return postGresqlFindBySql(page, size, sql, orgId, orgId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public PostgresqlQueryResult<Map<String, Object>> findRootPage(int page, int size, List<String> type, String name) {
        String sql = "select t.*, o.org_fax, o.org_contactor_phone, o.org_address, extract(year from age(cast(m.member_birthday as date))) as age" +
                " from hg_party_transport t " +
                " left join hg_party_member m on m.member_identity = t.user_id and m.historic = false" +
                " left join hg_party_org o on o.org_id = m.member_org" +
                " where t.type in ('2', '3')";
        if (!StringUtils.isEmpty(name)){
            sql += " and t.user_name like ?";
        }
        if (type != null && type.size() > 0) {
            sql += " and t.type in ('" + String.join("','", type) + "')";
        }
        sql += " order by t.status asc";
        if (size <= 0) {
            size = 10;
        }
        try {
            if (!StringUtils.isEmpty(name)){
                return postGresqlFindBySql(page, size, sql, name);
            }
            return postGresqlFindBySql(page, size, sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PostgresqlQueryResult<Map<String, Object>> findBranchPage(int page, int size, String orgId, List<String> type, String name) {
        String sql = "select t.*, o.org_fax, o.org_contactor_phone, o.org_address, extract(year from age(cast(m.member_birthday as date))) as age" +
                " from hg_party_transport t " +
                " left join hg_party_member m on t.user_id = m.member_identity and m.historic = false" +
                " left join hg_party_org o on o.org_id = m.member_org" +
                " where (m.member_org = ? or t.approved_list::jsonb @> '\"" + orgId + "\"'::jsonb " +
                " or t.current_approve_org = ?)";
        if (!StringUtils.isEmpty(name)){
            sql += " and t.user_name like ?";
        }
        if (type != null && type.size() > 0) {
            sql += " and t.type in ('" + String.join("','", type) + "')";
        }
        sql += " order by t.status asc";
        if (size <= 0) {
            size = 10;
        }
        try {
            if (!StringUtils.isEmpty(name)){
                return postGresqlFindBySql(page, size, sql, orgId, orgId, name);
            }
            return postGresqlFindBySql(page, size, sql, orgId, orgId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
