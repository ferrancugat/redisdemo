package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.collection.ScoringSortedSet;
import org.demo.nosql.redisdemo.command.Commands;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.LinkedList;
import java.util.List;

import static org.demo.nosql.redisdemo.domain.DataValueType.SORTEDSET;
import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;

public class ZrankRedisOperation extends AbstractRedisOperation {

    private static final int ONE_PARAMS_EXPECTED = 1;

    public ZrankRedisOperation() {
        super(Commands.ZRANK_COMMAND);
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
            List<String> rankUsers = getRankingUsers(dbSortedSet);
            return new RedisResponse(RESPONSE_OK, new DataValue(DataValueType.LIST, rankUsers));
        }
        return new RedisResponse(RedisResponse.RESPONSE_ERROR);
    }

    private List<String> getRankingUsers(ScoringSortedSet dbSortedSet) {
        List<String> rankingUsers = new LinkedList<>();
        dbSortedSet.forEach(m -> rankingUsers.add(m.getValue()));
        return rankingUsers;
    }

}
