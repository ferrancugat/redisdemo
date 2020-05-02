package org.demo.nosql.redisdemo.domain;

import org.demo.nosql.redisdemo.collections.ScoringSortedSet;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DataValue {

    private final DataValueType type;
    private final Object value;

    public DataValue(DataValueType type, Object value) {
        this.type = type;
        this.value = value;
    }

    //static method factory
    public static DataValue stringType(String value) {
        return new DataValue(DataValueType.STRING, value);
    }

    public static DataValue stringType(int value) {
        return stringType(value + "");
    }

    public static DataValue stringType(Long value) {
        return stringType(value + "");
    }

    public static DataValue emptySortedSet() {
        return new DataValue(DataValueType.SORTEDSET, new ScoringSortedSet());
    }

    /**
     *
     * @deprecated
     */
    @Deprecated
    public static DataValue emptyLinkedMap() {
        return new DataValue(DataValueType.SORTEDSET, new LinkedHashMap<>());
    }

    /**
     *
     * @deprecated
     */
    @Deprecated
    public static DataValue sortedMap(Map<String, Double> sortedMap) {
        return new DataValue(DataValueType.SORTEDSET, sortedMap);

    }

    public DataValueType getType() {
        return type;
    }

    public <T> T getValue() {
        return (T) value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataValue)) return false;
        DataValue dataValue = (DataValue) o;
        return getType() == dataValue.getType() && Objects.equals(getValue(), dataValue.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getValue());
    }
}
