package com.nmuzychuk.txdemo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbSetupRunner implements ApplicationRunner {

    private final JdbcTemplate messageJdbcTemplate;
    private final JdbcTemplate auditJdbcTemplate;

    public DbSetupRunner(JdbcTemplate messageJdbcTemplate, JdbcTemplate auditJdbcTemplate) {
        this.messageJdbcTemplate = messageJdbcTemplate;
        this.auditJdbcTemplate = auditJdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageJdbcTemplate.execute("CREATE TABLE MESSAGES (ID INT PRIMARY KEY, BODY VARCHAR(255))");
        messageJdbcTemplate.execute("CREATE SEQUENCE MESSAGE_SEQUENCE");

        auditJdbcTemplate.execute("CREATE TABLE IF NOT EXISTS AUDIT (ID INT PRIMARY KEY AUTO_INCREMENT, EVENT VARCHAR(255))");
    }
}
