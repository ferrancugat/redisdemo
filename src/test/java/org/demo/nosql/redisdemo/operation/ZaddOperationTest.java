package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.collection.ScoringSortedSet;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Test;

import javafx.util.Pair;

import static org.demo.nosql.redisdemo.domain.RedisResponseCode.RESPONSE_OK;

public class ZaddOperationTest extends BaseOperationTest {

    @Test
    public void whenZaddOK_returnsInsertedScoreUsers() {

        RedisRequest zaddRequest = CommandsHelper.zadd("key", "1.4", "user1", "1.5", "user2");
        RedisResponse response = operationExecutor.execute(zaddRequest);
        validateZaddResponse(response, "2");

        zaddRequest = CommandsHelper.zadd("key", "1.8", "user5", "1.9", "user7", "2.9", "user9");
        response = operationExecutor.execute(zaddRequest);
        validateZaddResponse(response, "3");

    }

    @Test
    public void whenZaddUpdatingSameUser_returns0() {

        RedisRequest zaddRequest = CommandsHelper.zadd("key", "1.4", "user1", "1.5", "user2");
        RedisResponse response = operationExecutor.execute(zaddRequest);
        validateZaddResponse(response, "2");

        zaddRequest = CommandsHelper.zadd("key", "1.4", "user1", "1.5", "user2");
        response = operationExecutor.execute(zaddRequest);
        validateZaddResponse(response, "0");

    }

    private void validateZaddResponse(RedisResponse response, String expectedValue) {
        Assert.assertNotNull(response);
        Assert.assertEquals(RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertEquals(DataValueType.STRING, dataValue.getType());
        Assert.assertEquals(expectedValue, dataValue.getValue());
    }

    @Test
    public void whenZaddInsertsOK_usersSortedByScoring() {

        RedisRequest zaddRequest = CommandsHelper.zadd("key", "71.4", "user1", "51.5", "user2");
        operationExecutor.execute(zaddRequest);
        zaddRequest = CommandsHelper.zadd("key", "1", "user7", "39", "user8", "99.5", "user10");
        operationExecutor.execute(zaddRequest);
        RedisRequest get = CommandsHelper.get("key");
        RedisResponse response = operationExecutor.execute(get);
        validateZaddTypeFromGet(response, 5);
    }

    private void validateZaddTypeFromGet(RedisResponse response, int expectedSize) {
        Assert.assertNotNull(response);
        Assert.assertEquals(RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertEquals(DataValueType.SORTEDSET, dataValue.getType());
        ScoringSortedSet sortedSet = dataValue.getValue();
        Assert.assertEquals(expectedSize, sortedSet.size());
        validateDataIsSorted(sortedSet);


    }

    private void validateDataIsSorted(ScoringSortedSet sortedSet) {
        Double previousScore = 0D;
        for (Pair<Double, String> scoreUser : sortedSet) {
            Assert.assertTrue(scoreUser.getKey() >= previousScore);
            previousScore = scoreUser.getKey();
        }
    }

    @Test
    public void whenZaddUpdatesScoringUser_userIsReordered() {

        RedisRequest zaddRequest = CommandsHelper.zadd("key", "71.4", "user1", "51.5", "user2");
        operationExecutor.execute(zaddRequest);
        zaddRequest = CommandsHelper.zadd("key", "1", "user7", "39", "user8", "99.5", "user10");
        operationExecutor.execute(zaddRequest);
        zaddRequest = CommandsHelper.zadd("key", "1000", "user7");
        operationExecutor.execute(zaddRequest);
        RedisRequest get = CommandsHelper.get("key");
        RedisResponse response = operationExecutor.execute(get);
        validateZaddTypeFromGet(response, 5);
        validateUserIsLast(response, "user7");

    }

    private void validateUserIsLast(RedisResponse response, String user7) {
        ScoringSortedSet sortedSet = response.getValue()
                .getValue();
        Assert.assertEquals(user7, sortedSet.last()
                .getValue());
    }


}
