package net.protsenko.tasklist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfig {

    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder.build();
    }

}

