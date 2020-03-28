package com.nmuzychuk.txdemo;

public class QueryHelper {
    static final String MESSAGE_COUNT_SQL = "SELECT COUNT(*) FROM MESSAGES";
    static final String INSERT_MESSAGE_SQL = "INSERT INTO MESSAGES VALUES(MESSAGE_SEQUENCE.NEXTVAL, CONCAT('message ', MESSAGE_SEQUENCE.CURRVAL))";
    static final String INSERT_MESSAGE_FAIL_SQL = "INSERT INTO MESSAGES VALUES(MESSAGE_SEQUENCE.NEXTVAL, FOOBAR)";
    static final String AUDIT_COUNT_SQL = "SELECT COUNT(*) FROM AUDIT";
    static final String INSERT_AUDIT_SQL = "INSERT INTO AUDIT VALUES(AUDIT_SEQUENCE.NEXTVAL, CONCAT('audit event ', AUDIT_SEQUENCE.CURRVAL))";
    static final String INSERT_AUDIT_FAIL_SQL = "INSERT INTO AUDIT VALUES(AUDIT_SEQUENCE.NEXTVAL, FOOBAR)";
}