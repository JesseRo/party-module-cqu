package party.log;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import party.portlet.report.entity.Report;

import java.util.List;
import java.util.Map;

@Component(immediate = true,service = LogDao.class)
public class LogDao extends PostgresqlDaoImpl<Log> {

    public PostgresqlQueryResult<Map<String, Object>> findPage(int page, int size) {
        if (size <= 0){
            size = 10;
        }
        String sql = "select * from hg_party_visit_count order by visit_time desc";
        return postGresqlFindBySql(page, size, sql);
    }

    public PostgresqlQueryResult<Map<String, Object>> searchPage(int page, int size, String search) {
        if (size <= 0){
            size = 10;
        }
        search = "%" + search + "%";
        String sql = "select * from hg_party_visit_count  where user_id like ? or hg_party_visit_count.type like ? or remark like ? or ip like ? order by time desc";
        try {
            return postGresqlFindBySql(page, size, sql, search, search, search, search);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
