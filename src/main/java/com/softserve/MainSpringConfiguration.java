package com.softserve;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.softserve")
public class MainSpringConfiguration {
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_PASSWORD = "123456";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DATABASE_USERNAME = "root";
         
    @Bean
    public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(DATABASE_DRIVER);
            dataSource.setUrl(DATABASE_URL);
            dataSource.setUsername(DATABASE_USERNAME);
            dataSource.setPassword(DATABASE_PASSWORD);
            return dataSource;
    }
}
