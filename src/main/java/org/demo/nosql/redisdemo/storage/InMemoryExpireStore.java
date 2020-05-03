package org.demo.nosql.redisdemo.storage;

import org.demo.nosql.redisdemo.domain.DataKey;
import org.demo.nosql.redisdemo.domain.DataValue;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Current implementation only removes expired keys when calling the get
 */
public class InMemoryExpireStore implements Store {

    private static final int FIVE_MINUTES_IN_MILLISECONDS = 5 * 60 * 1000;
    private Map<DataKey, DataValue> keyValuesMap = new ConcurrentHashMap<>();
    private Map<DataKey, Long> expireAtMap = new ConcurrentHashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private int checkExpiryInMillis = FIVE_MINUTES_IN_MILLISECONDS;
    private Thread worker;
    private AtomicBoolean runningFlag = new AtomicBoolean(false);


    public InMemoryExpireStore() {
        this(FIVE_MINUTES_IN_MILLISECONDS);
    }

    public InMemoryExpireStore(int checkExpiryInMillis) {
        this.checkExpiryInMillis = checkExpiryInMillis;
        worker = new CleanerCheck();
        runningFlag.set(true);
        worker.start();
    }

    @Override
    public void stop() {
        runningFlag.set(false);
        worker.interrupt();
    }

    @Override
    public int size() {
        lock.readLock()
                .lock();
        try {
            return keyValuesMap.size();
        } finally {
            lock.readLock()
                    .unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        lock.readLock()
                .lock();
        try {
            return keyValuesMap.isEmpty();
        } finally {
            lock.readLock()
                    .unlock();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        lock.readLock()
                .lock();
        try {
            return keyValuesMap.containsKey(key);
        } finally {
            lock.readLock()
                    .unlock();
        }
    }

    @Override
    public boolean containsValue(Object value) {
        lock.readLock()
                .lock();
        try {
            return keyValuesMap.containsValue(value);
        } finally {
            lock.readLock()
                    .unlock();
        }
    }

    /**
     * A key is passively expired simply when some client tries to access it, and the key is found to be timed out.
     */
    @Override
    public DataValue get(Object key) {
        lock.writeLock()
                .lock();
        try {
            if (expireAtMap.containsKey(key)) {
                boolean isExpired = isExpiredKey(key);
                if (isExpired) {
                    remove(key);
                    return null;
                }
            }
            return keyValuesMap.get(key);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    private boolean isExpiredKey(Object key) {
        Long expireAt = expireAtMap.get(key);
        return expireAt < System.currentTimeMillis();
    }

    @Override
    public DataValue put(DataKey key, DataValue value) {
        lock.writeLock()
                .lock();
        try {
            if (key.getDateExpirationInMillis() != null) {
                expireAtMap.put(key, key.getDateExpirationInMillis());
            }
            return keyValuesMap.put(key, value);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public DataValue remove(Object key) {
        lock.writeLock()
                .lock();
        try {
            expireAtMap.remove(key);
            return keyValuesMap.remove(key);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public void putAll(Map<? extends DataKey, ? extends DataValue> m) {
        lock.writeLock()
                .lock();
        try {
            for (Map.Entry<? extends DataKey, ? extends DataValue> entry : m.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public void clear() {
        lock.writeLock()
                .lock();
        try {
            expireAtMap.clear();
            keyValuesMap.clear();
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public Set<DataKey> keySet() {
        lock.readLock()
                .lock();
        try {
            return keyValuesMap.keySet();
        } finally {
            lock.readLock()
                    .unlock();
        }
    }

    @Override
    public Collection<DataValue> values() {
        lock.readLock()
                .lock();
        try {
            return keyValuesMap.values();
        } finally {
            lock.readLock()
                    .unlock();
        }
    }

    @Override
    public Set<Entry<DataKey, DataValue>> entrySet() {
        lock.readLock()
                .lock();
        try {
            return keyValuesMap.entrySet();
        } finally {
            lock.readLock()
                    .unlock();
        }
    }

    @Override
    public DataValue getOrDefault(Object key, DataValue defaultValue) {
        lock.writeLock()
                .lock();
        try {
            DataValue value = get(key);
            return value != null ? value : defaultValue;
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public void forEach(BiConsumer<? super DataKey, ? super DataValue> action) {
        lock.writeLock()
                .lock();
        try {
            keyValuesMap.forEach(action);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public void replaceAll(BiFunction<? super DataKey, ? super DataValue, ? extends DataValue> function) {
        lock.writeLock()
                .lock();
        try {
            keyValuesMap.replaceAll(function);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public DataValue putIfAbsent(DataKey key, DataValue value) {
        lock.writeLock()
                .lock();
        try {
            DataValue v = get(key);
            if (v == null) {
                v = put(key, value);
            }
            return v;
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public boolean remove(Object key, Object value) {
        lock.writeLock()
                .lock();
        try {
            boolean isRemoved = keyValuesMap.remove(key, value);
            if (isRemoved) {
                expireAtMap.remove(key);
            }
            return isRemoved;
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public boolean replace(DataKey key, DataValue oldValue, DataValue newValue) {
        lock.writeLock()
                .lock();
        try {
            return keyValuesMap.replace(key, oldValue, newValue);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public DataValue replace(DataKey key, DataValue value) {
        lock.writeLock()
                .lock();
        try {
            return put(key, value);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public DataValue computeIfAbsent(DataKey key, Function<? super DataKey, ? extends DataValue> mappingFunction) {
        lock.writeLock()
                .lock();
        try {
            DataValue value = get(key);
            if (value == null) {
                value = mappingFunction.apply(key);
                put(key, value);
            }
            return value;
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public DataValue computeIfPresent(DataKey key,
                                      BiFunction<? super DataKey, ? super DataValue, ? extends DataValue> remappingFunction) {
        lock.writeLock()
                .lock();
        try {
            return keyValuesMap.computeIfPresent(key, remappingFunction);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public DataValue compute(DataKey key,
                             BiFunction<? super DataKey, ? super DataValue, ? extends DataValue> remappingFunction) {
        lock.writeLock()
                .lock();
        try {
            return keyValuesMap.compute(key, remappingFunction);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    @Override
    public DataValue merge(DataKey key, DataValue value,
                           BiFunction<? super DataValue, ? super DataValue, ? extends DataValue> remappingFunction) {
        lock.writeLock()
                .lock();
        try {
            return keyValuesMap.merge(key, value, remappingFunction);
        } finally {
            lock.writeLock()
                    .unlock();
        }
    }

    /**
     * Specifically this is what Redis does 10 times per second:
     * <p>
     * Test 20 random keys from the set of keys with an associated expire. Delete all the keys found expired. If more
     * than 25% of keys were expired, start again from step 1.
     * </p>
     * Current simplified implemenation checks all entries to be evaluated
     */
    private class CleanerCheck extends Thread {
        @Override
        public void run() {
            System.out.println("Starting clean thread of DB");
            while (runningFlag.get()) {
                try {
                    cleanMap();
                    Thread.sleep(checkExpiryInMillis);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted clean thread from DB");
                } catch (Exception e) {
                    System.out.println("Exception cleaning up expired keys");
                }
            }
            System.out.println("Stopped clean thread ");
        }

        private void cleanMap() {
            for (DataKey key : expireAtMap.keySet()) {
                if (key.isExpired()) {
                    remove(key);
                    System.out.println("Removing key : " + key);
                }
            }
        }
    }
}
