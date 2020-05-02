package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ZrangeOperationTest extends BaseOperationTest {

    private final List<String> EXPECTED_RANKING = Arrays.asList("user7","user8");

    @Test
    public void whenZrangeInsertsOK_usersSortedByScoring(){

        RedisRequest zaddRequest = CommandsHelper.zadd("key","71.4","user1","51.5","user2");
        operationExecutor.execute(zaddRequest);
        zaddRequest = CommandsHelper.zadd("key","1","user7","39","user8","99.5","user10");
        operationExecutor.execute(zaddRequest);
        RedisRequest zrange = CommandsHelper.zrange("key",0,1);
        RedisResponse response =operationExecutor.execute(zrange);
        validateZrangeResponse(response, EXPECTED_RANKING);
    }

    private void validateZrangeResponse(RedisResponse response, List<String> ranking) {
        Assert.assertNotNull(response);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertEquals(DataValueType.LIST, dataValue.getType());
        Assert.assertTrue(ranking.equals(dataValue.getValue()));
    }


}
