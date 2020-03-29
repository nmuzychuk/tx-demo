package com.nmuzychuk.txdemo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.nmuzychuk.txdemo.MessageServiceHelper.*;

@Service
@Transactional
public class MessageService {

    private final JdbcTemplate messageJdbcTemplate;
    private final JdbcTemplate auditJdbcTemplate;
    private final JmsTemplate jmsTemplate;

    public MessageService(JdbcTemplate messageJdbcTemplate, JdbcTemplate auditJdbcTemplate, JmsTemplate jmsTemplate) {
        this.messageJdbcTemplate = messageJdbcTemplate;
        this.auditJdbcTemplate = auditJdbcTemplate;
        this.jmsTemplate = jmsTemplate;
    }

    public void saveMessage() {
        messageJdbcTemplate.execute(INSERT_MESSAGE_SQL);
        auditJdbcTemplate.execute(INSERT_AUDIT_SQL);
        jmsTemplate.convertAndSend(MESSAGE_QUEUE_NAME, MESSAGE_BODY);
    }

    public void saveMessageFail() {
        messageJdbcTemplate.execute(INSERT_MESSAGE_FAIL_SQL);
        auditJdbcTemplate.execute(INSERT_AUDIT_SQL);
        jmsTemplate.convertAndSend(MESSAGE_QUEUE_NAME, MESSAGE_BODY);
    }

    public void saveMessageAuditFail() {
        messageJdbcTemplate.execute(INSERT_MESSAGE_SQL);
        auditJdbcTemplate.execute(INSERT_AUDIT_FAIL_SQL);
        jmsTemplate.convertAndSend(MESSAGE_QUEUE_NAME, MESSAGE_BODY);
    }

    public void saveMessageAuditFailAfterJms() {
        messageJdbcTemplate.execute(INSERT_MESSAGE_SQL);
        jmsTemplate.convertAndSend(MESSAGE_QUEUE_NAME, MESSAGE_BODY);
        auditJdbcTemplate.execute(INSERT_AUDIT_FAIL_SQL);
    }

    public void saveMessageJmsFail() {
        messageJdbcTemplate.execute(INSERT_MESSAGE_SQL);
        auditJdbcTemplate.execute(INSERT_AUDIT_SQL);
        jmsTemplate.convertAndSend(MESSAGE_QUEUE_NAME, MESSAGE_BODY, null);
    }

}
