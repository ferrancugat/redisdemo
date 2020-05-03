package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.collections.ScoringSortedSet;
import org.demo.nosql.redisdemo.commands.Commands;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;

import static org.demo.nosql.redisdemo.domain.DataValueType.SORTEDSET;
import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;

public class ZrangeRedisOperation extends AbstractRedisOperation {

    private static final int THREE_PARAMS_EXPECTED = 3;

    public ZrangeRedisOperation() {
        super(Commands.ZRANGE_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params != null && params.size() == THREE_PARAMS_EXPECTED;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        DataKey key = DataKey.createDataKey(params.get(0));
        DataValue dbValue = db.get(key);
        if (SORTEDSET.equals(dbValue.getType())) {
            ScoringSortedSet dbSortedSet = dbValue.getValue();

            int start = Integer.parseInt(params.get(1));
            int stop = Integer.parseInt(params.get(2));

            if (start >= 0 && stop > start && stop < dbSortedSet.size()) {
                List<String> rankUsers = getSubsetRankingUsers(dbSortedSet, start, stop);
                return new RedisResponse(RESPONSE_OK, new DataValue(DataValueType.LIST, rankUsers));
            } else {
                System.out.println("The range seems to be wrong");
            }
        }
        return new RedisResponse(RedisResponse.RESPONSE_ERROR);
    }

    private List<String> getSubsetRankingUsers(ScoringSortedSet dbSortedSet, int start, int stop) {
        List<String> rankingUsers = new LinkedList<>();
        int index = 0;
        for (Pair<Double, String> scoreUser : dbSortedSet) {
            if (start <= index && index <= stop) {
                rankingUsers.add(scoreUser.getValue());
            }
            index++;
        }
        return rankingUsers;
    }

}
