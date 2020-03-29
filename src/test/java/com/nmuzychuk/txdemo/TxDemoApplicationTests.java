package com.nmuzychuk.txdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;

import java.util.Collections;

import static com.nmuzychuk.txdemo.MessageServiceHelper.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TxDemoApplicationTests {

    @Autowired
    JdbcTemplate messageJdbcTemplate;

    @Autowired
    JdbcTemplate auditJdbcTemplate;

    @Autowired
    MessageService messageService;

    @Autowired
    JmsTemplate jmsTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void messageDataSourceQuery() {
        Integer beforeCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);

        messageJdbcTemplate.execute(INSERT_MESSAGE_SQL);

        Integer afterCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);

        assertNotNull(beforeCount);
        assertEquals(beforeCount + 1, afterCount);
    }

    @Test
    void auditDataSourceQuery() {
        Integer beforeCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);

        auditJdbcTemplate.execute(INSERT_AUDIT_SQL);

        Integer afterCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);

        assertNotNull(beforeCount);
        assertEquals(beforeCount + 1, afterCount);
    }

    @Test
    void messageServiceSaveMessage() {
        Integer beforeMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer beforeAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer beforeMessageQueueSize = messageQueueSize();

        messageService.saveMessage();

        Integer afterMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer afterAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer afterMessageQueueSize = messageQueueSize();

        assertNotNull(beforeMessageCount);
        assertNotNull(beforeAuditCount);
        assertEquals(beforeMessageCount + 1, afterMessageCount);
        assertEquals(beforeAuditCount + 1, afterAuditCount);
        assertEquals(beforeMessageQueueSize + 1, afterMessageQueueSize);
    }

    @Test
    void messageServiceSaveMessageFail() {
        Integer beforeMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer beforeAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer beforeMessageQueueSize = messageQueueSize();

        assertThrows(BadSqlGrammarException.class, () -> messageService.saveMessageFail());

        Integer afterMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer afterAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer afterMessageQueueSize = messageQueueSize();

        assertNotNull(beforeMessageCount);
        assertNotNull(beforeAuditCount);
        assertEquals(beforeMessageCount, afterMessageCount);
        assertEquals(beforeAuditCount, afterAuditCount);
        assertEquals(beforeMessageQueueSize, afterMessageQueueSize);
    }

    @Test
    void messageServiceSaveMessageAuditFail() {
        Integer beforeMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer beforeAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer beforeMessageQueueSize = messageQueueSize();

        assertThrows(BadSqlGrammarException.class, () -> messageService.saveMessageAuditFail());

        Integer afterMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer afterAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer afterMessageQueueSize = messageQueueSize();

        assertNotNull(beforeMessageCount);
        assertNotNull(beforeAuditCount);
        assertEquals(beforeMessageCount, afterMessageCount);
        assertEquals(beforeAuditCount, afterAuditCount);
        assertEquals(beforeMessageQueueSize, afterMessageQueueSize);
    }

    @Test
    void messageServiceSaveMessageAuditFailAfterJms() {
        Integer beforeMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer beforeAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer beforeMessageQueueSize = messageQueueSize();

        assertThrows(BadSqlGrammarException.class, () -> messageService.saveMessageAuditFailAfterJms());

        Integer afterMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer afterAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer afterMessageQueueSize = messageQueueSize();

        assertNotNull(beforeMessageCount);
        assertNotNull(beforeAuditCount);
        assertEquals(beforeMessageCount, afterMessageCount);
        assertEquals(beforeAuditCount, afterAuditCount);
        assertEquals(beforeMessageQueueSize, afterMessageQueueSize);
    }

    @Test
    void messageServiceSaveMessageJmsFail() {
        Integer beforeMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer beforeAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer beforeMessageQueueSize = messageQueueSize();

        assertThrows(NullPointerException.class, () -> messageService.saveMessageJmsFail());

        Integer afterMessageCount = messageJdbcTemplate.queryForObject(MESSAGE_COUNT_SQL, Integer.class);
        Integer afterAuditCount = auditJdbcTemplate.queryForObject(AUDIT_COUNT_SQL, Integer.class);
        Integer afterMessageQueueSize = messageQueueSize();

        assertNotNull(beforeMessageCount);
        assertNotNull(beforeAuditCount);
        assertEquals(beforeMessageCount, afterMessageCount);
        assertEquals(beforeAuditCount, afterAuditCount);
        assertEquals(beforeMessageQueueSize, afterMessageQueueSize);
    }

    private Integer messageQueueSize() {
        return jmsTemplate.browse("messages", (
                (session, browser) -> Collections.list(browser.getEnumeration()).size())
        );
    }

}
