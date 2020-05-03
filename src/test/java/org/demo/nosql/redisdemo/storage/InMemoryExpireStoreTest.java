package org.demo.nosql.redisdemo.storage;

import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

public class InMemoryExpireStoreTest {

    public static final int EXPECTED_AFTER_REMOVING_EXPIRED = 0;
    private static final int ONE_SECOND_CLEANDATA_IN_MILLISECONDS = 1 * 1000;
    Random rand = new Random();

    @Test
    public void whenKeyExpires_thenKeysAreRemoved() throws InterruptedException {
        Store store = new InMemoryExpireStore(ONE_SECOND_CLEANDATA_IN_MILLISECONDS);
        int insertedExpiredKeys = 10;
        insertExpiredEntries(store, insertedExpiredKeys);
        Assert.assertEquals(insertedExpiredKeys, store.size());
        Thread.sleep(ONE_SECOND_CLEANDATA_IN_MILLISECONDS);
        Assert.assertEquals(EXPECTED_AFTER_REMOVING_EXPIRED, store.size());
        store.stop();
    }

    private void insertExpiredEntries(Store store, int totalEntries) {
        for (int i = 0; i < totalEntries; i++) {
            store.put(getRandomExpiredKey(), getRandomValue());
        }

    }

    private DataValue getRandomValue() {

        return DataValue.stringDataValue(rand.nextInt(1000));
    }

    private DataKey getRandomExpiredKey() {
        UUID uuid = UUID.randomUUID();
        long expiredTimeInMilliceonds = System.currentTimeMillis() - ONE_SECOND_CLEANDATA_IN_MILLISECONDS;
        return new DataKey(uuid.toString(), expiredTimeInMilliceonds);
    }
}
