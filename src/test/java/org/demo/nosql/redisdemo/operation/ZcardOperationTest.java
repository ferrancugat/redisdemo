package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Test;

public class ZcardOperationTest extends BaseOperationTest {

    public static final String EXPECTED_CARDINALITY = "5";

    @Test
    public void whenZcardInsertsOK_usersSortedByScoring() {

        RedisRequest zaddRequest = CommandsHelper.zadd("key", "71.4", "user1", "51.5", "user2");
        operationExecutor.execute(zaddRequest);
        zaddRequest = CommandsHelper.zadd("key", "1", "user7", "39", "user8", "99.5", "user10");
        operationExecutor.execute(zaddRequest);
        RedisRequest zcard = CommandsHelper.zcard("key");
        RedisResponse response = operationExecutor.execute(zcard);
        validateZcardResponse(response, EXPECTED_CARDINALITY);
    }

    private void validateZcardResponse(RedisResponse response, String expectedCardinality) {
        Assert.assertNotNull(response);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertEquals(DataValueType.STRING, dataValue.getType());
        Assert.assertEquals(expectedCardinality, dataValue.getValue());
    }


}
