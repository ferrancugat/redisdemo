package org.demo.nosql.redisdemo.domain;

import java.util.Objects;

public class DataKey {

    private final String value;
    private final Long dateExpirationInMillis;

    public DataKey(String value) {
        this(value, null);
    }

    public DataKey(String value, Long dateExpirationInMillis) {
        this.value = value;
        this.dateExpirationInMillis = dateExpirationInMillis;
    }

    public static DataKey createDataKey(String value) {
        return new DataKey(value);
    }

    public static DataKey createDataKey(String value, Long timeToExpire) {
        return new DataKey(value, timeToExpire);
    }

    public String getValue() {
        return value;
    }

    public Long getDateExpirationInMillis() {
        return dateExpirationInMillis;
    }

    public boolean isExpired() {
        if (dateExpirationInMillis != null) {
            long now = System.currentTimeMillis();
            return now > dateExpirationInMillis;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataKey)) return false;
        DataKey dataKey = (DataKey) o;
        return Objects.equals(getValue(), dataKey.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
