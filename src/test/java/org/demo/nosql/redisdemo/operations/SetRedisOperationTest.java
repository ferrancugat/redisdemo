package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Test;

public class SetRedisOperationTest extends BaseOperationTest {

    String key = "key";
    String expectedValue = "value";


    @Test
    public void whenSetOK_ThenDataInDB() {

        RedisRequest request = CommandsHelper.set(key, expectedValue);
        RedisResponse response = operationExecutor.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertNull(dataValue);

        DataValue dbValue = store.get(DataKey.createDataKey(key));
        Assert.assertNotNull(dbValue);
        Assert.assertEquals(expectedValue, dbValue.getValue());
    }
}
