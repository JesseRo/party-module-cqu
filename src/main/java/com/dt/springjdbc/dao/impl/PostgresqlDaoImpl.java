package com.dt.springjdbc.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class PostgresqlDaoImpl<T> extends PostgresqlDynamicDaoImpl<T> {
    public PostgresqlDaoImpl() {
        Properties properties = this.getResourceProperties();
        DataSource ds = PooledDataSourceProvider.getDataSource(properties);
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(ds);
    }

    public Properties getResourceProperties() {
        Properties pps = new Properties();

        try {
            pps.load(super.getClass().getResourceAsStream("/druid.properties"));
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return pps;
    }
}
