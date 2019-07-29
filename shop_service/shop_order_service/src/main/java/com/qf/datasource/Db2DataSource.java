package com.qf.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Created by  .Life on 2019/07/27/0027 10:54
 */
@Component
@ConfigurationProperties(prefix = "spring.orderdb2.datasource")
public class Db2DataSource extends BaseDataSource {
}
