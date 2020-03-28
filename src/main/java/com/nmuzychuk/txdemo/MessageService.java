package com.nmuzychuk.txdemo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.nmuzychuk.txdemo.QueryHelper.*;

@Service
@Transactional
public class MessageService {

    private final JdbcTemplate messageJdbcTemplate;
    private final JdbcTemplate auditJdbcTemplate;

    public MessageService(JdbcTemplate messageJdbcTemplate, JdbcTemplate auditJdbcTemplate) {
        this.messageJdbcTemplate = messageJdbcTemplate;
        this.auditJdbcTemplate = auditJdbcTemplate;
    }

    public void saveMessage() {
        messageJdbcTemplate.execute(INSERT_MESSAGE_SQL);
        auditJdbcTemplate.execute(INSERT_AUDIT_SQL);
    }

    public void saveMessageFail() {
        messageJdbcTemplate.execute(INSERT_MESSAGE_FAIL_SQL);
        auditJdbcTemplate.execute(INSERT_AUDIT_SQL);
    }

    public void saveMessageAuditFail() {
        messageJdbcTemplate.execute(INSERT_MESSAGE_SQL);
        auditJdbcTemplate.execute(INSERT_AUDIT_FAIL_SQL);
    }

}
