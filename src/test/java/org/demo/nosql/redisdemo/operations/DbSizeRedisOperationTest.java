package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.DataValueType;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Test;

public class DbSizeRedisOperationTest extends BaseOperationTest {

    String key = "key";
    String expectedValue = "value";


    @Test
    public void whenDbSizeOK_ThenReturnTotalKeys() {

        insertData(key+1,expectedValue+1);
        insertData(key+2,expectedValue+2);
        insertData(key+3,expectedValue+3);

        RedisRequest request = CommandsHelper.dbsize();
        RedisResponse response = operationExecutor.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertEquals(DataValueType.STRING, dataValue.getType());
        Assert.assertEquals("3", dataValue.getValue());

    }

    private void insertData(String key, String value){
        RedisRequest request = CommandsHelper.set(key, value);
        RedisResponse response = operationExecutor.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertNull(dataValue);
    }
}
