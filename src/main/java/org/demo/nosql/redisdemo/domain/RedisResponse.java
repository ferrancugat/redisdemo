package org.demo.nosql.redisdemo.domain;

public class RedisResponse {

    private final RedisResponseCode code;
    private final DataValue value;

    public RedisResponse(RedisResponseCode code) {
        this(code, null);
    }

    public RedisResponse(RedisResponseCode code, DataValue value) {
        this.code = code;
        this.value = value;
    }

    public RedisResponseCode getCode() {
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
