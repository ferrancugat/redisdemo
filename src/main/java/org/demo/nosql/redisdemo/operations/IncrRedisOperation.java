package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.List;

import static org.demo.nosql.redisdemo.commands.Commands.INCR_COMMAND;
import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;
import static org.demo.nosql.redisdemo.utils.NumericalUtils.isValidLong;

public class IncrRedisOperation extends AbstractRedisOperation {

    private static final int ONE_PARAMS_EXPECTED = 1;

    public IncrRedisOperation() {
        super(INCR_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params != null && params.size() == ONE_PARAMS_EXPECTED;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        DataKey key = new DataKey(params.get(0));
        DataValue value = db.get(key);
        if ((value != null) && !isValidLong(value)) {
            return new RedisResponse(RedisResponse.RESPONSE_ERROR);
        }
        DataValue newValue=increment(value);
        db.put(key,newValue);
        return new RedisResponse(RESPONSE_OK,newValue);
    }

    private DataValue increment(DataValue oldValue) {
        if (oldValue == null) {
            return DataValue.stringType("0");
        }
        Long previousCounter = Long.valueOf(oldValue.getValue());
        return DataValue.stringType(previousCounter + 1);
    }
}
