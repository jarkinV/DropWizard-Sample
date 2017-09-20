package com.softserve;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.softserve")
@PropertySource("file:config.properties")
public class MainSpringConfiguration {

    @Value("${database.driver}")
    private String databaseDriver;
    @Value("${database.password}")
    private String databasePassword;
    @Value("${database.password}")
    private String databaseUrl;
    @Value("${database.password}")
    private String databaseUsername;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseDriver);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);
        return dataSource;
    }
    
    
}
