package party.memberEdit;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;

import java.util.Map;

@Component(immediate = true,service = MemberEditDao.class)
public class MemberEditDao extends PostgresqlDaoImpl<MemberEdit> {
    public PostgresqlQueryResult<Map<String, Object>> findPage(int page, int size) {
        if (size <= 0){
            size = 10;
        }
        String sql = "select e.*, org.org_name from hg_party_member_edit e left join hg_party_org org on e.member_org = org.org_id" +
                " order by status asc, submit_time desc";
        return postGresqlFindBySql(page, size, sql);
    }

    public PostgresqlQueryResult<Map<String, Object>> searchPage(int page, int size, String search) {
        if (size <= 0){
            size = 10;
        }
        search = "%" + search + "%";
        String sql = "select * from hg_party_visit_count  where user_id like ? or type like ? or remark like ? or ip like ? order by time desc";
        return postGresqlFindBySql(page, size, sql, search, search, search, search);
    }
}
