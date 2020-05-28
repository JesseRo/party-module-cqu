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

    private List<T> postGresql_findBySql(int pageNo, int pageSize, String sql,Class<T> elementType, Object... objects) {
        StringBuffer exeSql = new StringBuffer();
        exeSql.append(sql);
        exeSql.append(" LIMIT ? OFFSET ? ");
        if (objects != null && objects.length != 0) {
            List<Object> list = (List)Arrays.stream(objects).collect(Collectors.toList());
            list.add(pageSize);
            list.add(pageNo * pageSize);
            return this.jdbcTemplate.queryForList(exeSql.toString(), elementType,list.toArray(new Object[0]));
        } else {
            return this.jdbcTemplate.queryForList(exeSql.toString(),elementType, new Object[]{pageSize, pageNo * pageSize});
        }
    }

    /**
     *
     * @param pageNow
     * @param pageSize
     * @param sql
     * @param object
     * @return 返回分页Map 封装
     */
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

    public PostgresqlPageResult<T> postGresqlFindPageBySql(int pageNow, int pageSize, String sql,Class<T> elementType, Object... object) {
        if (pageNow <= 0) {
            pageNow = 0;
        } else {
            --pageNow;
        }
        List<T> list = this.postGresql_findBySql(pageNow, pageSize, sql,elementType, object);
        int count = this.postGresql_countBySql(sql, object);
        return new PostgresqlPageResult(list, count, pageNow);
    }
}
