package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.commands.Commands;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.List;

import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;

public class GetRedisOperation extends AbstractRedisOperation {

    private static final int ONE_PARAM_EXPECTED = 1;

    public GetRedisOperation() {
        super(Commands.GET_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params != null && params.size() == ONE_PARAM_EXPECTED;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        DataValue value = db.get(new DataKey(params.get(0)));
        return new RedisResponse(RESPONSE_OK, value);
    }
}
