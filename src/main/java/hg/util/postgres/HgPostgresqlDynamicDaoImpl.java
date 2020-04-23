package hg.util.postgres;

import com.dt.springjdbc.dao.impl.PostgresqlDynamicDaoImpl;
import java.util.*;
import java.util.stream.Collectors;

public class HgPostgresqlDynamicDaoImpl<T> extends PostgresqlDynamicDaoImpl<T> {
    private List<Map<String, Object>> postGresql_findBySql(int pageNo, int pageSize, String sql, Object... objects) {
        StringBuffer exeSql = new StringBuffer();
        exeSql.append(sql);
        exeSql.append(" LIMIT ? OFFSET ? ");
        if (objects != null && objects.length != 0) {
            List<Object> list = (List)Arrays.stream(objects).collect(Collectors.toList());
            list.add(pageSize);
            list.add(pageNo * pageSize);
            return this.jdbcTemplate.queryForList(exeSql.toString(), list.toArray(new Object[0]));
        } else {
            return this.jdbcTemplate.queryForList(exeSql.toString(), new Object[]{pageSize, pageNo * pageSize});
        }
    }
    public PostgresqlPageResult<Map<String, Object>> postGresqlFindPageBySql(int pageNow, int pageSize, String sql, Object... object) {
        if (pageNow <= 0) {
            pageNow = 0;
        } else {
            --pageNow;
        }
        List<Map<String, Object>> list = this.postGresql_findBySql(pageNow, pageSize, sql, object);
        int count = this.postGresql_countBySql(sql, object);
        return new PostgresqlPageResult(list, count, pageNow);
    }
}
