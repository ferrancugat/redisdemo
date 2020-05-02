package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Test;

public class DeleteRedisOperationTest extends BaseOperationTest {

    String key = "key";
    String expectedValue = "value";


    @Test
    public void whenDeleteRightKey_ThenGetReturnNull() {

        RedisRequest insert = CommandsHelper.set(key, expectedValue);
        operationExecutor.execute(insert);

        RedisRequest delete = CommandsHelper.delete(key);
        RedisResponse response = operationExecutor.execute(delete);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        Assert.assertNull(response.getValue());

        RedisRequest get = CommandsHelper.get(key);
        response = operationExecutor.execute(get);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        Assert.assertNull(response.getValue());

    }

    @Test
    public void whenDeleteWrongKey_ThenGetReturnValue() {

        RedisRequest insert = CommandsHelper.set(key, expectedValue);
        operationExecutor.execute(insert);

        String wrongkey = "wrongkey";
        RedisRequest delete = CommandsHelper.delete(wrongkey);
        RedisResponse response = operationExecutor.execute(delete);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        Assert.assertNull(response.getValue());

        RedisRequest get = CommandsHelper.get(key);
        response = operationExecutor.execute(get);
        DataValue dataValue = response.getValue();
        Assert.assertEquals(DataValueType.STRING, dataValue.getType());
        Assert.assertEquals(expectedValue, dataValue.getValue());

    }
}
