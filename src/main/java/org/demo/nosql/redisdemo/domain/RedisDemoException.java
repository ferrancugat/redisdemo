package org.demo.nosql.redisdemo.domain;


public class RedisDemoException extends RuntimeException {

    protected RedisResponseCode errorCode;

    public RedisDemoException(final RedisResponseCode errorCode) {
        this.errorCode = errorCode;
    }

    public RedisDemoException(final Throwable thr, final RedisResponseCode errorCode) {
        super(thr);
        this.errorCode = errorCode;
    }

    public RedisResponseCode getErrorCode() {
        return this.errorCode;
    }
}
