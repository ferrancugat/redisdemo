package org.demo.nosql.redisdemo.domain;

public enum RedisResponseCode {
    RESPONSE_OK("OK"),
    RESPONSE_ERROR("ERROR"),
    RESPONSE_VALIDATION("VALIDATION_ERROR"),
    RESPONSE_UNSUPORTED_TYPE("UNSUPPORTED_TYPE");

    final private String value;

    private RedisResponseCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
