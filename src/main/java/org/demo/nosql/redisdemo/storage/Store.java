package org.demo.nosql.redisdemo.storage;

import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;

import java.util.Map;

public interface Store extends Map<DataKey, DataValue> {

    void stop();
}
