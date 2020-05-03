package org.demo.nosql.redisdemo.domain;

import org.demo.nosql.redisdemo.collections.ScoringSortedSet;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.demo.nosql.redisdemo.domain.DataValueType.SORTEDSET;
import static org.demo.nosql.redisdemo.domain.DataValueType.STRING;

public class DataValue {

    private final DataValueType type;
    private final Object value;

    public DataValue(DataValueType type, Object value) {
        this.type = type;
        this.value = value;
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

    public static DataValue emptySortedSet() {
        return new DataValue(SORTEDSET, new ScoringSortedSet());
    }

    //static method factory

    public static DataValue stringDataValue(String value) {
        return new DataValue(STRING, value);
    }

    public static DataValue stringDataValue(int value) {
        return stringDataValue(value + "");
    }

    public static DataValue stringDataValue(Long value) {
        return stringDataValue(value + "");
    }

    /**
     *
     * @deprecated
     */
    @Deprecated
    public static DataValue emptyLinkedMap() {
        return new DataValue(SORTEDSET, new LinkedHashMap<>());
    }

    /**
     *
     * @deprecated
     */
    @Deprecated
    public static DataValue sortedMap(Map<String, Double> sortedMap) {
        return new DataValue(SORTEDSET, sortedMap);

    }
}
