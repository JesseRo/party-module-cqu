package party.portlet.unit;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import party.portlet.transport.entity.Retention;

import java.util.Map;

@Component(immediate = true,service = UnitDao.class)
public class UnitDao extends PostgresqlDaoImpl<Unit> {

    public PostgresqlQueryResult<Map<String, Object>> findPage(int page, int size) {
        String sql = "select u.*, m.user_name as update_member_name from hg_party_unit u inner join hg_users_info m on u.update_member_id = m.user_id order by id asc";
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
