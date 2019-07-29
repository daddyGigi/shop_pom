package com.qf.dataconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by  .Life on 2019/07/27/0027 11:03
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        return threadLocal.get();
    }

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void set(String dataSourceKeyword){
        threadLocal.set(dataSourceKeyword);
    }
}
