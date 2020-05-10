package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.command.Commands;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.List;

import static org.demo.nosql.redisdemo.domain.RedisResponseCode.RESPONSE_OK;

public class SetRedisOperation extends AbstractRedisOperation {

    private static final int TWO_PARAMS_EXPECTED = 2;

    public SetRedisOperation() {
        super(Commands.SET_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params != null && params.size() == TWO_PARAMS_EXPECTED;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        db.put(DataKey.createDataKey(params.get(0)), DataValue.stringDataValue(params.get(1)));
        return new RedisResponse(RESPONSE_OK);
    }

}
