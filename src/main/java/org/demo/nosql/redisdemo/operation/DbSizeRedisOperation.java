package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.command.Commands;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.List;

import static org.demo.nosql.redisdemo.domain.RedisResponseCode.RESPONSE_OK;


public class DbSizeRedisOperation extends AbstractRedisOperation {

    private static final int ZER0_PARAM_EXPECTED = 0;

    public DbSizeRedisOperation() {
        super(Commands.DBSIZE_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params == null || params.size() == ZER0_PARAM_EXPECTED;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        int size = db.size();
        return new RedisResponse(RESPONSE_OK, DataValue.stringDataValue(size));
    }
}
