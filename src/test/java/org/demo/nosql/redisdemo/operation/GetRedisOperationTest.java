package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetRedisOperationTest extends BaseOperationTest {

    String key = "key";
    String expectedValue = "value";


    @Before
    @Override
    public void setup() {
        super.setup();
        this.store.put(DataKey.createDataKey(key), DataValue.stringDataValue(expectedValue));
    }

    @Test
    public void whenKeyExists_ThenReturnData() {


        RedisRequest request = CommandsHelper.get(key);
        RedisResponse response = operationExecutor.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertEquals(DataValueType.STRING, dataValue.getType());
        Assert.assertEquals(expectedValue, dataValue.getValue());
    }

    @Test
    public void whenKeyDoesNotExists_ThenReturnNull() {

        String wrongKey = "wrongKey";
        RedisRequest request = CommandsHelper.get(wrongKey);
        RedisResponse response = operationExecutor.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertNull(dataValue);
    }
}
