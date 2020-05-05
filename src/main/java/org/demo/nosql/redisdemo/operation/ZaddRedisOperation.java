package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.collection.ScoringSortedSet;
import org.demo.nosql.redisdemo.command.Commands;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.List;

import javafx.util.Pair;

import static org.demo.nosql.redisdemo.domain.DataValueType.SORTEDSET;
import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;

public class ZaddRedisOperation extends AbstractRedisOperation {

    private static final int TWO_PARAMS_MINIMUM = 2;

    public ZaddRedisOperation() {
        super(Commands.ZADD_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params != null && params.size() >= TWO_PARAMS_MINIMUM;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        try {
            DataKey key = DataKey.createDataKey(params.get(0));
            DataValue dbValue = db.getOrDefault(key, DataValue.emptySortedSet());
            if (SORTEDSET.equals(dbValue.getType())) {
                ScoringSortedSet dbSortedSet = dbValue.getValue();
                int newRecords = mergeScores(params.subList(1, params.size()), dbSortedSet);
                db.put(key, dbValue);
                return new RedisResponse(RESPONSE_OK, DataValue.stringDataValue(newRecords));
            }
        } catch (NumberFormatException e) {
            System.out.println(" Error formatting Score to Double");
        }
        return new RedisResponse(RedisResponse.RESPONSE_ERROR);
    }


    private int mergeScores(List<String> params, ScoringSortedSet dbSortedSet) {
        int newEntries = 0;
        for (int index = 0; index < params.size(); index += 2) {
            String scoreInput = params.get(index);
            String userInput = params.get(index + 1);

            Double score = Double.parseDouble(scoreInput);

            if (dbSortedSet.contains(userInput)) {   //O(1) map
                dbSortedSet.remove(userInput);  //O(log n)
            } else {
                newEntries++;
            }
            dbSortedSet.add(new Pair(score, userInput));       //O(log n)
        }
        return newEntries;
    }

}
