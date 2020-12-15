package com.dt.springjdbc.dao.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hg.party.command.common.FileDownCommand;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.Properties;


public class PooledDataSourceProvider {
    Logger logger = Logger.getLogger(FileDownCommand.class);
    private static volatile DataSource ds = null;

    public PooledDataSourceProvider() {
    }

    public static DataSource getDataSource(Properties properties) {
        if (ds == null) {
            try {
                synchronized (PooledDataSourceProvider.class) {
                    if (ds == null) {
                        ds = new HikariDataSource(new HikariConfig(properties));
                    }
                }
            } catch (Exception var7) {
                var7.printStackTrace();
                System.exit(0);
            }
        }

        return ds;
    }
}
