package org.demo.nosql.redisdemo.domain;

public class RedisResponse {

    public static final String RESPONSE_OK = "OK";
    public static final String RESPONSE_ERROR = "ERROR";

    private final String code;
    private final DataValue value;

    public RedisResponse(String code) {
        this(code, null);
    }

    public RedisResponse(String code, DataValue value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public DataValue getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "RedisResponse{" + "code='" + code + '\'' + ", value=" + value + '}';
    }
}
