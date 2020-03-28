package com.nmuzychuk.txdemo;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.message")
    public DataSource messageDataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean
    JdbcTemplate messageJdbcTemplate() {
        return new JdbcTemplate(messageDataSource());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.audit")
    public DataSource auditDataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean
    JdbcTemplate auditJdbcTemplate() {
        return new JdbcTemplate(auditDataSource());
    }

}
