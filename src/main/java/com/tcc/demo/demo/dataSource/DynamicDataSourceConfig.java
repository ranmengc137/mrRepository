package com.tcc.demo.demo.dataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:properties/jdbc.properties")
@MapperScan(basePackages = "com.tcc.demo.demo.mappers")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class DynamicDataSourceConfig {
    @Bean("account")
    @ConfigurationProperties(prefix = "spring.datasource.account")
    public DataSource accountDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("inventory")
    @ConfigurationProperties(prefix = "spring.datasource.inventory")
    public DataSource inventoryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("test")
    @ConfigurationProperties(prefix = "spring.datasource.test")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("account", accountDataSource());
        dataSourceMap.put("inventory", inventoryDataSource());
        dataSourceMap.put("test", testDataSource());
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(accountDataSource());
        return dynamicDataSource;
    }
}
