package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.command.Commands;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.List;

import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;

public class DeleteRedisOperation extends AbstractRedisOperation {

    private static final int ONE_PARAMS_EXPECTED = 1;

    public DeleteRedisOperation() {
        super(Commands.DEL_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params != null && params.size() == ONE_PARAMS_EXPECTED;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        db.remove(new DataKey(params.get(0)));
        return new RedisResponse(RESPONSE_OK);
    }

}
