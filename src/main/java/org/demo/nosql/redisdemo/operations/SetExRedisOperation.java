package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.List;

import static org.demo.nosql.redisdemo.commands.Commands.SETEX_COMMAND;
import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;
import static org.demo.nosql.redisdemo.utils.TimeUtils.convertToMilliseconds;

public class SetExRedisOperation extends AbstractRedisOperation {

    private static final int THREE_PARAMS_EXPECTED = 3;

    public SetExRedisOperation() {
        super(SETEX_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params != null && params.size() == THREE_PARAMS_EXPECTED;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        Long timeToExpire = convertToMilliseconds(params.get(1));
        db.put(DataKey.createDataKey(params.get(0), timeToExpire), DataValue.stringType(params.get(2)));
        return new RedisResponse(RESPONSE_OK);
    }


}
