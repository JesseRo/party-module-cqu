//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dt.springjdbc.dao.impl;

import com.dt.annotation.Column;
import com.dt.annotation.Table;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class PostgresqlDynamicDaoImpl<T> implements BaseDao<T> {
    public static final String SQL_INSERT = "insert";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_DELETE = "delete";
    protected JdbcTemplate jdbcTemplate;
    protected String tableName;
    private Class<T> entityClass;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public PostgresqlDynamicDaoImpl() {
        ParameterizedType type = (ParameterizedType)super.getClass().getGenericSuperclass();
        this.entityClass = (Class)type.getActualTypeArguments()[0];
        String name = this.getEntityTable(this.entityClass);
        this.tableName = name;
    }

    public String getEntityTable(Class<T> entityClass) {
        String ret = "";
        Table t = (Table)entityClass.getAnnotation(Table.class);
        if (t != null) {
            ret = t.name();
        } else {
            ret = entityClass.getSimpleName();
        }

        return ret == null ? "" : ret;
    }

    public String getEntityColumn(Field field) {
        String ret = "";
        Column t = (Column)field.getAnnotation(Column.class);
        if (t != null) {
            ret = t.sqlName();
        } else {
            ret = field.getName();
        }

        return ret == null ? "" : ret;
    }

    public String getEntityColumnSqlType(Field field) {
        String ret = "";
        Column t = (Column)field.getAnnotation(Column.class);
        if (t != null) {
            ret = t.sqlType();
        } else {
            ret = field.getType().toString();
            ret = ret.substring(ret.lastIndexOf(".") + 1, ret.length());
        }

        return ret == null ? "" : ret;
    }

    public int save(T entity) throws RuntimeException {
        final String sql = this.makeSql("insert", entity);
        final Object[] args = this.setArgs(entity, "insert");
        final int[] argTypes = this.setArgTypes(entity, "insert");

        for(int i = 1; i < args.length; ++i) {
        }

        PreparedStatementCreator t = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preState = con.prepareStatement(sql, 1);

                for(int i = 0; i < argTypes.length - 1; ++i) {
                    if (argTypes[i + 1] == 0) {
                        preState.setNull(i + 1, 0);
                    } else if (argTypes[i + 1] == 12) {
                        preState.setString(i + 1, (String)args[i + 1]);
                    } else if (argTypes[i + 1] == 3) {
                        preState.setDouble(i + 1, (Double)args[i + 1]);
                    } else if (argTypes[i + 1] == 4) {
                        preState.setInt(i + 1, (Integer)args[i + 1]);
                    } else if (argTypes[i + 1] == 91) {
                        Date d = (Date)args[i + 1];
                        java.sql.Date date = new java.sql.Date(d.getTime());
                        preState.setDate(i + 1, date);
                    } else if (argTypes[i + 1] == 93) {
                        preState.setTimestamp(i + 1, (Timestamp)args[i + 1]);
                    } else {
                        preState.setNull(i + 1, 0);
                    }
                }

                return preState;
            }
        };
        return this.jdbcTemplate.update(t);
    }

    public Integer insertForReturn(final String sql) {
        PreparedStatementCreator t = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preState = con.prepareStatement(sql, 1);
                return preState;
            }
        };
        KeyHolder key = new GeneratedKeyHolder();
        this.jdbcTemplate.update(t, key);
        return key.getKey().intValue();
    }

    public Integer saveForReturn(T entity) throws RuntimeException {
        final String sql = this.makeSql("insert", entity);
        final Object[] args = this.setArgs(entity, "insert");
        final int[] argTypes = this.setArgTypes(entity, "insert");
        PreparedStatementCreator t = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preState = con.prepareStatement(sql, 1);

                for(int i = 0; i < argTypes.length; ++i) {
                    if (argTypes[i] == 0) {
                        preState.setNull(i + 1, 0);
                    } else if (argTypes[i] == 12) {
                        preState.setString(i + 1, (String)args[i]);
                    } else if (argTypes[i] == 3) {
                        preState.setDouble(i + 1, (Double)args[i]);
                    } else if (argTypes[i] == 4) {
                        preState.setInt(i + 1, (Integer)args[i]);
                    } else if (argTypes[i] == 91) {
                        preState.setTimestamp(i + 1, (Timestamp)args[i]);
                    } else if (argTypes[i] == 93) {
                        preState.setTimestamp(i + 1, (Timestamp)args[i]);
                    } else {
                        preState.setNull(i + 1, 0);
                    }
                }

                return preState;
            }
        };
        KeyHolder key = new GeneratedKeyHolder();
        this.jdbcTemplate.update(t, key);
        return key.getKey().intValue();
    }

    public void saveEntityWithoutID(T entity) {
        String sql = this.makeInsertSqlWithoutID();
        Object[] args = this.setArgsWithoutID(entity);
        this.setArgTypes(entity, "insert");
        this.jdbcTemplate.update(sql.toString(), args);
    }

    private Object[] setArgsWithoutID(T entity) {
        Field[] fields = this.entityClass.getDeclaredFields();
        Object[] args = new Object[fields.length];
        List<Object> list = new ArrayList();

        for(int i = 1; args != null && i < args.length; ++i) {
            try {
                fields[i].setAccessible(true);
                String fieldName = fields[i].getName();
                if (!fieldName.equalsIgnoreCase("id")) {
                    list.add(fields[i].get(entity));
                }
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }

        return list.toArray();
    }

    private String makeInsertSqlWithoutID() {
        String seqName = this.tableName + "_id_seq";
        StringBuffer sql = new StringBuffer();
        Field[] fields = this.entityClass.getDeclaredFields();
        sql.append(" INSERT INTO " + this.tableName);
        sql.append("(");

        int i;
        String column;
        for(i = 0; fields != null && i < fields.length; ++i) {
            fields[i].setAccessible(true);
            column = this.getEntityColumn(fields[i]);
            sql.append(column).append(",");
        }

        sql = sql.deleteCharAt(sql.length() - 1);
        sql.append(") VALUES (nextval('" + seqName + "'),");

        for(i = 0; fields != null && i < fields.length; ++i) {
            fields[i].setAccessible(true);
            column = this.getEntityColumn(fields[i]);
            if (!column.equalsIgnoreCase("id")) {
                sql.append("?,");
            }
        }

        sql = sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        return sql.toString();
    }

    public int update(T entity) throws RuntimeException {
        String sql = this.makeSql("update", entity);
        Object[] args = this.setArgs(entity, "update");
        int[] argTypes = this.setArgTypes(entity, "update");
        return this.jdbcTemplate.update(sql, args, argTypes);
    }

    public int delete(T entity) throws RuntimeException {
        try {
            Field fieldId = this.entityClass.getDeclaredField("id");
            fieldId.setAccessible(true);
            Object v = fieldId.get(entity);
            if (v == null) {
                throw new RuntimeException("id值为null");
            } else {
                return this.delete((Serializable)((Integer)v));
            }
        } catch (NoSuchFieldException var4) {
            var4.printStackTrace();
            throw new RuntimeException(var4);
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
            throw new RuntimeException(var5);
        }
    }

    public int delete(Serializable id) throws RuntimeException {
        String sql = " DELETE FROM " + this.tableName + " WHERE id=?";
        return this.jdbcTemplate.update(sql, new Object[]{id});
    }

    public void deleteAll() throws RuntimeException {
        String sql = " TRUNCATE TABLE " + this.tableName;
        this.jdbcTemplate.execute(sql);
    }

    public int saveOrUpdate(T entity) throws RuntimeException {
        try {
            Field fieldId = this.entityClass.getDeclaredField("id");
            fieldId.setAccessible(true);
            Object v = fieldId.get(entity);
            Object obj = this.findById((Integer)v);
            return obj != null ? this.update(entity) : this.save(entity);
        } catch (NoSuchFieldException var5) {
            var5.printStackTrace();
            throw new RuntimeException(var5);
        } catch (SecurityException var6) {
            var6.printStackTrace();
            throw new RuntimeException(var6);
        } catch (IllegalArgumentException var7) {
            var7.printStackTrace();
            throw new RuntimeException(var7);
        } catch (IllegalAccessException var8) {
            var8.printStackTrace();
            throw new RuntimeException(var8);
        }
    }

    public int batchSave(List<T> list) throws RuntimeException {
        int total = 0;
        if (list != null) {
            for(int i = 0; i < list.size(); ++i) {
                int c = this.save(list.get(i));
                total += c;
            }
        }

        return total;
    }

    public int batchSaveOrUpdate(List<T> list) throws RuntimeException {
        int total = 0;
        if (list != null) {
            for(int i = 0; i < list.size(); ++i) {
                int c = this.saveOrUpdate(list.get(i));
                total += c;
            }
        }

        return total;
    }

    public int batchUpdate(List<T> list) throws RuntimeException {
        int total = 0;
        if (list != null) {
            for(int i = 0; i < list.size(); ++i) {
                int c = this.update(list.get(i));
                total += c;
            }
        }

        return total;
    }

    public int batchDelete(List<T> list) throws RuntimeException {
        int total = 0;
        if (list != null) {
            for(int i = 0; i < list.size(); ++i) {
                int c = this.delete(list.get(i));
                total += c;
            }
        }

        return total;
    }

    public T findById(Serializable id) throws RuntimeException {
        String sql = this.queryColunm() + " WHERE id=?";
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
        List<T> list = this.jdbcTemplate.query(sql, rowMapper, new Object[]{id});
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    public List<T> findAll() throws RuntimeException {
        String sql = this.queryColunm() + " ORDER BY id DESC";
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public List<T> findByProperty(String property_name, String property_value) throws RuntimeException {
        String sql = this.queryColunm() + " WHERE " + property_name + "='" + property_value + "' ORDER BY id DESC";
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public List<T> findByPropertyLike(String property_name, String property_value) throws RuntimeException {
        String sql = this.queryColunm() + " WHERE " + property_name + " LIKE '%" + property_value + "%' ORDER BY id DESC";
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public List<T> findByPropertiesOr(String[] props, String[] values) throws RuntimeException {
        String sql = this.queryColunm() + " WHERE ";

        for(int i = 0; i < props.length; ++i) {
            sql = sql + " OR " + props[i] + "='" + values[i] + "' ";
        }

        sql = sql + " ORDER BY id DESC";
        sql = sql.replaceFirst(" OR ", " ");
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public List<T> findByPropertiesAnd(String[] props, String[] values) throws RuntimeException {
        String sql = this.queryColunm() + " WHERE ";

        for(int i = 0; i < props.length; ++i) {
            sql = sql + " AND " + props[i] + "='" + values[i] + "' ";
        }

        sql = sql + " ORDER BY id DESC";
        sql = sql.replaceFirst(" AND ", " ");
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public QueryResult<T> findByPage(int pageNo, int pageSize) throws RuntimeException {
        List<T> list = this.find(pageNo, pageSize, (Map)null, (LinkedHashMap)null);
        int totalRow = this.count((Map)null);
        return new QueryResult(list, totalRow);
    }

    public QueryResult<T> findByPage(int pageNo, int pageSize, int type) throws RuntimeException {
        List<T> list = this.find(pageNo, pageSize, (Map)null, (LinkedHashMap)null, type);
        int totalRow = this.count((Map)null);
        return new QueryResult(list, totalRow);
    }

    public QueryResult<T> findByPage(int pageNo, int pageSize, Map<String, String> where) throws RuntimeException {
        List<T> list = this.find(pageNo, pageSize, where, (LinkedHashMap)null);
        int totalRow = this.count(where);
        return new QueryResult(list, totalRow);
    }

    public QueryResult<T> findByPage(int pageNo, int pageSize, Map<String, String> where, int type) throws RuntimeException {
        List<T> list = this.find(pageNo, pageSize, where, (LinkedHashMap)null, type);
        int totalRow = this.count(where);
        return new QueryResult(list, totalRow);
    }

    public QueryResult<T> findByPage(int pageNo, int pageSize, LinkedHashMap<String, String> orderby) throws RuntimeException {
        List<T> list = this.find(pageNo, pageSize, (Map)null, orderby);
        int totalRow = this.count((Map)null);
        return new QueryResult(list, totalRow);
    }

    public QueryResult<T> findByPage(int pageNo, int pageSize, Map<String, String> where, LinkedHashMap<String, String> orderby) throws RuntimeException {
        List<T> list = this.find(pageNo, pageSize, where, orderby);
        int totalRow = this.count(where);
        return new QueryResult(list, totalRow);
    }

    public QueryResult<T> findByPage(int pageNo, int pageSize, Map<String, String> where, LinkedHashMap<String, String> orderby, int type) throws RuntimeException {
        List<T> list = this.find(pageNo, pageSize, where, orderby, type);
        int totalRow = this.count(where);
        return new QueryResult(list, totalRow);
    }

    private String makeSql(String sqlFlag, T entity) {
        StringBuffer sql = new StringBuffer();

        try {
            Field[] fields = this.entityClass.getDeclaredFields();
            String sqlType;
            if (sqlFlag.equals("insert")) {
                sql.append(" INSERT INTO " + this.tableName);
                sql.append("(");
                int forNum = 1;

                int i;
                for(i = forNum; fields != null && i < fields.length; ++i) {
                    sqlType = this.getEntityColumn(fields[i]);
                    fields[i].setAccessible(true);
                    sql.append(sqlType).append(",");
                }

                sql = sql.deleteCharAt(sql.length() - 1);
                sql.append(") VALUES (");

                for(i = forNum; fields != null && i < fields.length; ++i) {
                    sqlType = this.getEntityColumnSqlType(fields[i]);
                    if ("jsonb".equals(sqlType)) {
                        sql.append("?::jsonb,");
                    } else if ("json".equals(sqlType)) {
                        sql.append("?::json,");
                    } else {
                        sql.append("?,");
                    }
                }

                sql = sql.deleteCharAt(sql.length() - 1);
                sql.append(")");
            } else if (sqlFlag.equals("update")) {
                sql.append(" UPDATE " + this.tableName + " SET ");

                for(int i = 0; fields != null && i < fields.length; ++i) {
                    fields[i].setAccessible(true);
                    String column = this.getEntityColumn(fields[i]);
                    if (!column.equals("id")) {
                        sqlType = this.getEntityColumnSqlType(fields[i]);
                        if ("jsonb".equals(sqlType)) {
                            sql.append(column).append("=").append("?::jsonb,");
                        } else if ("json".equals(sqlType)) {
                            sql.append(column).append("=").append("?::jsonb,");
                        } else {
                            sql.append(column).append("=").append("?,");
                        }
                    }
                }

                sql = sql.deleteCharAt(sql.length() - 1);
                sql.append(" WHERE id=?");
            } else if (sqlFlag.equals("delete")) {
                sql.append(" DELETE FROM " + this.tableName + " WHERE id=?");
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return sql.toString();
    }

    private Object[] setArgs(T entity, String sqlFlag) {
        Field[] fields = this.entityClass.getDeclaredFields();
        Object[] args;
        int i;
        if (sqlFlag.equals("insert")) {
            args = new Object[fields.length];

            for(i = 0; args != null && i < args.length; ++i) {
                try {
                    fields[i].setAccessible(true);
                    args[i] = fields[i].get(entity);
                } catch (Exception var7) {
                    var7.printStackTrace();
                }
            }

            return args;
        } else if (!sqlFlag.equals("update")) {
            if (sqlFlag.equals("delete")) {
                args = new Object[1];
                fields[0].setAccessible(true);

                try {
                    args[0] = fields[0].get(entity);
                } catch (Exception var9) {
                    var9.printStackTrace();
                }

                return args;
            } else {
                return null;
            }
        } else {
            args = new Object[fields.length];

            for(i = 0; args != null && i < args.length; ++i) {
                try {
                    fields[i].setAccessible(true);
                    args[i] = fields[i].get(entity);
                } catch (Exception var8) {
                    var8.printStackTrace();
                }
            }

            Object[] _args = new Object[fields.length];
            System.arraycopy(args, 1, _args, 0, args.length - 1);
            _args[args.length - 1] = args[0];
            return _args;
        }
    }

    private int[] setArgTypes(T entity, String sqlFlag) {
        Field[] fields = this.entityClass.getDeclaredFields();
        int[] argTypes = new int[fields.length];
        int idIndex = 0;

        try {
            int i;
            for(i = 0; argTypes != null && i < argTypes.length; ++i) {
                fields[i].setAccessible(true);
                if (fields[i].getName().equalsIgnoreCase("id")) {
                    idIndex = i;
                }

                if (null != fields[i].get(entity)) {
                }

                if (fields[i].get(entity) == null) {
                    argTypes[i] = 0;
                } else if (fields[i].get(entity).getClass().getName().equals("java.lang.String")) {
                    argTypes[i] = 12;
                } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Double")) {
                    argTypes[i] = 3;
                } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Integer")) {
                    argTypes[i] = 4;
                } else if (fields[i].get(entity).getClass().getName().equals("java.util.Date")) {
                    argTypes[i] = 91;
                } else if (fields[i].get(entity).getClass().getName().equals("java.sql.Timestamp")) {
                    argTypes[i] = 93;
                }
            }

            if (sqlFlag.equals("update")) {
                for(i = idIndex; i < argTypes.length - 1; ++i) {
                    int temp = argTypes[i];
                    argTypes[i] = argTypes[i + 1];
                    argTypes[i + 1] = temp;
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return argTypes;
    }

    private List<T> find(int pageNo, int pageSize, Map<String, String> where, LinkedHashMap<String, String> orderby) {
        StringBuffer sql = new StringBuffer();
        sql.append(this.queryColunm());
        Iterator var7;
        Entry me;
        String columnName;
        String columnValue;
        if (where != null && where.size() > 0) {
            sql.append(" WHERE ");
            var7 = where.entrySet().iterator();

            while(var7.hasNext()) {
                me = (Entry)var7.next();
                columnName = (String)me.getKey();
                columnValue = (String)me.getValue();
                sql.append(columnName).append(" ").append(columnValue).append(" AND ");
            }

            int endIndex = sql.lastIndexOf("AND");
            if (endIndex > 0) {
                sql = new StringBuffer(sql.substring(0, endIndex));
            }
        }

        if (orderby != null && orderby.size() > 0) {
            sql.append(" ORDER BY ");
            var7 = orderby.entrySet().iterator();

            while(var7.hasNext()) {
                me = (Entry)var7.next();
                columnName = (String)me.getKey();
                columnValue = (String)me.getValue();
                sql.append(columnName).append(" ").append(columnValue).append(",");
            }

            sql = sql.deleteCharAt(sql.length() - 1);
        }

        sql.append(" LIMIT ? ,?");
        Object[] args = new Object[]{pageNo * pageSize, pageSize};
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
        return this.jdbcTemplate.query(sql.toString(), args, rowMapper);
    }

    private List<T> find(int pageNo, int pageSize, Map<String, String> where, LinkedHashMap<String, String> orderby, int type) {
        StringBuffer sql = new StringBuffer();
        Iterator var7;
        Entry me;
        String columnName;
        String columnValue;
        int endIndex;
        Object[] args;
        BeanPropertyRowMapper rowMapper;
        if (type == 0) {
            sql = new StringBuffer(" SELECT * FROM (SELECT t.*,ROWNUM rn FROM (SELECT * FROM " + this.tableName);
            if (where != null && where.size() > 0) {
                sql.append(" WHERE ");
                var7 = where.entrySet().iterator();

                while(var7.hasNext()) {
                    me = (Entry)var7.next();
                    columnName = (String)me.getKey();
                    columnValue = (String)me.getValue();
                    sql.append(columnName).append(" ").append(columnValue).append(" AND ");
                }

                endIndex = sql.lastIndexOf("AND");
                if (endIndex > 0) {
                    sql = new StringBuffer(sql.substring(0, endIndex));
                }
            }

            if (orderby != null && orderby.size() > 0) {
                sql.append(" ORDER BY ");
                var7 = orderby.entrySet().iterator();

                while(var7.hasNext()) {
                    me = (Entry)var7.next();
                    columnName = (String)me.getKey();
                    columnValue = (String)me.getValue();
                    sql.append(columnName).append(" ").append(columnValue).append(",");
                }

                sql = sql.deleteCharAt(sql.length() - 1);
            }

            sql.append(" ) t WHERE ROWNUM<=? ) WHERE rn>=? ");
            sql.append(" ) t WHERE ROWNUM<=? ) WHERE rn>=? ");
            args = new Object[]{pageNo * pageSize, (pageNo - 1) * pageSize + 1};
            rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
            return this.jdbcTemplate.query(sql.toString(), args, rowMapper);
        } else if (type == 1) {
            sql.append("SELECT * FROM " + this.tableName);
            if (where != null && where.size() > 0) {
                sql.append(" WHERE ");
                var7 = where.entrySet().iterator();

                while(var7.hasNext()) {
                    me = (Entry)var7.next();
                    columnName = (String)me.getKey();
                    columnValue = (String)me.getValue();
                    sql.append(columnName).append(" ").append(columnValue).append(" AND ");
                }

                endIndex = sql.lastIndexOf("AND");
                if (endIndex > 0) {
                    sql = new StringBuffer(sql.substring(0, endIndex));
                }
            }

            if (orderby != null && orderby.size() > 0) {
                sql.append(" ORDER BY ");
                var7 = orderby.entrySet().iterator();

                while(var7.hasNext()) {
                    me = (Entry)var7.next();
                    columnName = (String)me.getKey();
                    columnValue = (String)me.getValue();
                    sql.append(columnName).append(" ").append(columnValue).append(",");
                }

                sql = sql.deleteCharAt(sql.length() - 1);
            }

            sql.append(" LIMIT ? ,?");
            args = new Object[]{pageNo * pageSize, pageSize};
            rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
            return this.jdbcTemplate.query(sql.toString(), args, rowMapper);
        } else if (type != 2) {
            return null;
        } else {
            sql.append(this.queryColunm());
            if (where != null && where.size() > 0) {
                sql.append(" WHERE ");
                var7 = where.entrySet().iterator();

                while(var7.hasNext()) {
                    me = (Entry)var7.next();
                    columnName = (String)me.getKey();
                    columnValue = (String)me.getValue();
                    sql.append(columnName).append(" ").append(columnValue).append(" AND ");
                }

                endIndex = sql.lastIndexOf("AND");
                if (endIndex > 0) {
                    sql = new StringBuffer(sql.substring(0, endIndex));
                }
            }

            if (orderby != null && orderby.size() > 0) {
                sql.append(" ORDER BY ");
                var7 = orderby.entrySet().iterator();

                while(var7.hasNext()) {
                    me = (Entry)var7.next();
                    columnName = (String)me.getKey();
                    columnValue = (String)me.getValue();
                    sql.append(columnName).append(" ").append(columnValue).append(",");
                }

                sql = sql.deleteCharAt(sql.length() - 1);
            }

            sql.append(" LIMIT ? OFFSET ?");
            args = new Object[]{pageSize, pageNo * pageSize};
            rowMapper = BeanPropertyRowMapper.newInstance(this.entityClass);
            return this.jdbcTemplate.query(sql.toString(), args, rowMapper);
        }
    }

    public int count(Map<String, String> where) {
        StringBuffer sql = new StringBuffer(" SELECT COUNT(*) FROM " + this.tableName);
        if (where != null && where.size() > 0) {
            sql.append(" WHERE ");
            Iterator var3 = where.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, String> me = (Entry)var3.next();
                String columnName = (String)me.getKey();
                String columnValue = (String)me.getValue();
                sql.append(columnName).append(" ").append(columnValue).append(" AND ");
            }

            int endIndex = sql.lastIndexOf("AND");
            if (endIndex > 0) {
                sql = new StringBuffer(sql.substring(0, endIndex));
            }
        }

        return (Integer)this.jdbcTemplate.queryForObject(sql.toString(), Integer.class);
    }

    public String queryColunm() {
        StringBuffer sql = new StringBuffer("SELECT ");
        Field[] fields = this.entityClass.getDeclaredFields();

        for(int i = 0; i < fields.length; ++i) {
            Column column = (Column)fields[i].getAnnotation(Column.class);
            if (i != 0) {
                sql.append(",");
            }

            if (column != null) {
                sql.append(column.sqlName());
                sql.append(" AS ");
                sql.append(column.javaName());
            } else {
                sql.append(fields[i].getName());
            }
        }

        sql.append(" FROM " + this.tableName);
        return sql.toString();
    }

    public int addEntity(T t) {
        return this.save(t);
    }

    public int updateEntity(T t) {
        return this.update(t);
    }

    public int deleteEntity(Serializable id) {
        return this.delete(id);
    }

    public T getEntityById(Serializable id) {
        return this.findById(id);
    }

    public Collection<T> getAllEntitys() {
        return this.findAll();
    }

    private List<Map<String, Object>> postGresql_findBySql(int pageNo, int pageSize, String sql, Object... objects) {
        StringBuffer exeSql = new StringBuffer();
        exeSql.append(sql);
        exeSql.append(" LIMIT ? OFFSET ? ");
        if (objects != null && objects.length != 0) {
            List<Object> list = Arrays.stream(objects).collect(Collectors.toList());
            list.add(pageSize);
            list.add(pageNo * pageSize);
            return this.jdbcTemplate.queryForList(exeSql.toString(), list.toArray(new Object[0]));
        } else {
            return this.jdbcTemplate.queryForList(exeSql.toString(), new Object[]{pageSize, pageNo * pageSize});
        }
    }

    public int postGresql_countBySql(String sql, Object... objects) {
        sql = String.format("select count(*) from (%s) _t", sql);
        return this.jdbcTemplate.queryForObject(sql, Integer.class, objects);
    }

    public PostgresqlQueryResult<T> postGresqlFindByObj(int pageNow, int pageSize, Map<String, String> where, LinkedHashMap<String, String> orderby) {
        if (pageNow <= 0) {
            pageNow = 0;
        } else {
            --pageNow;
        }

        List<T> list = this.find(pageNow, pageSize, where, orderby, 2);
        int count = this.count(where);
        int totalPage = (int)Math.ceil(Double.parseDouble(String.valueOf(count)) / Double.parseDouble(String.valueOf(pageSize)));
        return new PostgresqlQueryResult(list, totalPage, pageNow);
    }

    public PostgresqlQueryResult<Map<String, Object>> postGresqlFindBySql(int pageNow, int pageSize, String sql, Object... object) {
        if (pageNow <= 0) {
            pageNow = 0;
        } else {
            --pageNow;
        }

        List<Map<String, Object>> list = this.postGresql_findBySql(pageNow, pageSize, sql, object);
        int count = this.postGresql_countBySql(sql, object);
        int totalPage = (int)Math.ceil(Double.parseDouble(String.valueOf(count)) / Double.parseDouble(String.valueOf(pageSize)));
        return new PostgresqlQueryResult(list, totalPage, pageNow);
    }
}
