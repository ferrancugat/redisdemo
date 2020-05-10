package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Test;

import static org.demo.nosql.redisdemo.domain.RedisResponseCode.RESPONSE_OK;

public class IncrRedisOperationTest extends BaseOperationTest {

    String key = "key";
    String expectedValue = "value";


    @Test
    public void whenIncrOK_ThenCounterIncreases() {
        int counter = 0;
        executeIncrCommandAndValidate(counter);
        counter++;
        executeIncrCommandAndValidate(counter);
        counter++;
        executeIncrCommandAndValidate(counter);
        counter++;
        executeIncrCommandAndValidate(counter);
    }

    private void executeIncrCommandAndValidate(int counter) {
        RedisRequest request = CommandsHelper.incr(key);
        RedisResponse response = operationExecutor.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertEquals(DataValueType.STRING, dataValue.getType());
        Assert.assertEquals(counter + "", dataValue.getValue());
    }
}
