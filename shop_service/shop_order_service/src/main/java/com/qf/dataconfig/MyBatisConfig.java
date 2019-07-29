package com.qf.dataconfig;

import com.qf.datasource.Db1DataSource;
import com.qf.datasource.Db2DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  .Life on 2019/07/27/0027 11:00
 */
@Configuration
public class MyBatisConfig {
    @Autowired
    private Db1DataSource db1DataSource;
    @Autowired
    private Db2DataSource db2DataSource;
    @Value("${mybatis-plus.mapper-locations}")
    private String mapperlocation;

    @Bean
    public DynamicDataSource getDataSource() {
        Map<Object, Object> map = new HashMap<>();
        map.put(db1DataSource.getKeyword(),db1DataSource.getDataSource());
        map.put(db2DataSource.getKeyword(),db2DataSource.getDataSource());

        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        dynamicDataSource.setDefaultTargetDataSource(db1DataSource.getDataSource());
        dynamicDataSource.setTargetDataSources(map);

        return dynamicDataSource;
    }

    /*
    * 配置SqlSessionFactoryBean
    * */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DynamicDataSource getDataSource){
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

            sqlSessionFactoryBean.setDataSource(getDataSource);
        try {
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperlocation));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sqlSessionFactoryBean;
    }
}
