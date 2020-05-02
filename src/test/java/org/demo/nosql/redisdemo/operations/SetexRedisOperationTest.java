package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.CommandsHelper;
import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SetexRedisOperationTest extends BaseOperationTest {

    private static final long FIVE_MINUTES_IN_SECONDS = 5 * 60;
    String key = "key";
    String expectedValue = "value";


    @Before
    @Override
    public void setup() {
        super.setup();
        this.store.put(DataKey.createDataKey(key), DataValue.stringType(expectedValue));
    }

    @Test
    public void whenSetexWithLateExpiredOK_ThenDataInDB() {
        long expiredAt = getCurrentTimeSeconds() + FIVE_MINUTES_IN_SECONDS;
        RedisRequest request = CommandsHelper.setex(key, expectedValue, expiredAt);
        RedisResponse response = operationExecutor.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertNull(dataValue);

        DataValue dbValue = store.get(DataKey.createDataKey(key));
        Assert.assertNotNull(dbValue);
        Assert.assertEquals(expectedValue, dbValue.getValue());
    }

    @Test
    public void whenSetexWithCloseExpiredOK_ThenDataNotInDB() {
        long alreadyExpiredAt = getCurrentTimeSeconds() - FIVE_MINUTES_IN_SECONDS;
        RedisRequest request = CommandsHelper.setex(key, expectedValue, alreadyExpiredAt);
        RedisResponse response = operationExecutor.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(RedisResponse.RESPONSE_OK, response.getCode());
        DataValue dataValue = response.getValue();
        Assert.assertNull(dataValue);

        DataValue dbValue = store.get(DataKey.createDataKey(key));
        Assert.assertNull(dbValue);
    }

    private long getCurrentTimeSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}
