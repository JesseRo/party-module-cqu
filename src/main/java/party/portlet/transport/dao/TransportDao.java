package party.portlet.transport.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import party.portlet.report.entity.Report;
import party.portlet.transport.entity.Transport;

import java.util.List;
import java.util.Map;

@Component(immediate = true,service = TransportDao.class)
public class TransportDao extends PostgresqlDaoImpl<Transport> {

    public Transport findById(String transportId) {
        String sql = "select * from hg_party_transport where transport_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Transport.class), transportId);
        } catch (Exception e) {
            return null;
        }
    }

    public PostgresqlQueryResult<Map<String, Object>> findSecondaryPage(int page, int size, String  orgId) {
        String sql = "select * from hg_party_transport t inner join hg_party_org o on t.org_id = o.org_id" +
                " where o.org_parent = ? and t.type = '0' order by t.status asc";
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
        String sql = "select * from hg_party_transport t t.type in ('1', '2', '3') order by t.status asc";
        if (size <= 0){
            size = 10;
        }
        try {
            return postGresqlFindBySql(page, size, sql);
        } catch (Exception e) {
            return null;
        }
    }
}
