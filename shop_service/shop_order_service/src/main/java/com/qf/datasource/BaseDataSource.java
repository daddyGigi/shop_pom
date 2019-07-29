package com.qf.datasource;


import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;


import javax.sql.DataSource;

/**
 * Created by  .Life on 2019/07/27/0027 10:47
 */
@Data
public class BaseDataSource {

    protected String url;
    protected String username;
    protected String password;
    protected String driverClassName;
    protected String keyword;

    public DataSource getDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driverClassName);
        return hikariDataSource;
    }

}
