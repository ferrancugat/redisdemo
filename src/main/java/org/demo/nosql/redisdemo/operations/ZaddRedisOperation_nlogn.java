package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.commands.Commands;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.demo.nosql.redisdemo.domain.DataValueType.SORTEDSET;
import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;

@Deprecated
public class ZaddRedisOperation_nlogn extends AbstractRedisOperation {

    private static final int TWO_PARAMS_EXPECTED = 2;

    public ZaddRedisOperation_nlogn() {
        super(Commands.ZADD_COMMAND);
    }

    @Override
    protected boolean validateParams(List<String> params) {
        return params != null && params.size() >= TWO_PARAMS_EXPECTED;
    }

    @Override
    protected RedisResponse execute(Store db, List<String> params) {
        try {
            DataKey key = DataKey.createDataKey(params.get(0));
            DataValue initial = db.getOrDefault(key, DataValue.emptyLinkedMap());
            if (SORTEDSET.equals(initial.getType())) {
                Map<String, Double> map = initial.getValue();
                int newRecords = mergeScores(params, map);

                Map<String, Double> sortedMap = sortMapByScore(map);
                db.put(key, DataValue.sortedMap(sortedMap));

                return new RedisResponse(RESPONSE_OK, DataValue.stringType(newRecords));

            }
        } catch (NumberFormatException e) {
            System.out.println(" Error formatting Score to Double");
        }
        return new RedisResponse(RedisResponse.RESPONSE_ERROR);
    }

    private Map<String, Double> sortMapByScore(Map<String, Double> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()) // O (n *logn)  < --- PROBLEM
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    private int mergeScores(List<String> params, Map<String, Double> map) {
        int newEntries = 0;
        for (int index = 1; index < params.size(); index += 2) {
            String scoreInput = params.get(index);
            String valueInput = params.get(index + 1);

            Double s = Double.parseDouble(scoreInput);

            Double prevScore = map.put(valueInput, s);
            if (prevScore == null) {
                newEntries++;
            }
        }
        return newEntries;
    }

}
