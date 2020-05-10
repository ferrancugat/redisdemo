package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.demo.nosql.redisdemo.domain.DataValueType.LIST;
import static org.demo.nosql.redisdemo.domain.RedisResponseCode.RESPONSE_OK;

public class ZrankOperationTest extends BaseOperationTest {

    private final List<String> EXPECTED_RANKING = Arrays.asList("user7", "user8", "user2", "user1", "user10");

    @Test
    public void whenZrankInsertsOK_usersSortedByScoring() {

        RedisRequest zaddRequest = CommandsHelper.zadd("key", "71.4", "user1", "51.5", "user2");
        operationExecutor.execute(zaddRequest);
        zaddRequest = CommandsHelper.zadd("key", "1", "user7", "39", "user8", "99.5", "user10");
        operationExecutor.execute(zaddRequest);
        RedisRequest zrank = CommandsHelper.zrank("key");
        RedisResponse response = operationExecutor.execute(zrank);
        validateZrankResponse(response, EXPECTED_RANKING);
    }

    private void validateZrankResponse(RedisResponse response, List<String> ranking) {
        Assert.assertNotNull(response);
        Assert.assertEquals(RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertEquals(LIST, dataValue.getType());
        Assert.assertTrue(ranking.equals(dataValue.getValue()));
    }


}
