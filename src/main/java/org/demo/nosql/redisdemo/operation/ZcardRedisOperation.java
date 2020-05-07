package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.collection.ScoringSortedSet;
import org.demo.nosql.redisdemo.command.Commands;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.List;

import static org.demo.nosql.redisdemo.domain.DataValueType.SORTEDSET;
import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;

public class ZcardRedisOperation extends AbstractRedisOperation {

    private static final int ONE_PARAMS_EXPECTED = 1;

    public ZcardRedisOperation() {
        super(Commands.ZCARD_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params != null && params.size() == ONE_PARAMS_EXPECTED;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        DataKey key = DataKey.createDataKey(params.get(0));
        DataValue dbValue = db.get(key);
        if (SORTEDSET.equals(dbValue.getType())) {
            ScoringSortedSet dbSortedSet = dbValue.getValue();
            return new RedisResponse(RESPONSE_OK, DataValue.stringDataValue(dbSortedSet.size()));
        } else {
            logger.warn("Value of key:{} is of type:{}", key, dbValue.getType());
        }
        return new RedisResponse(RedisResponse.RESPONSE_ERROR);
    }

}
