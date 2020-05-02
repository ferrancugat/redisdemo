package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.commands.OperationExecutor;
import org.demo.nosql.redisdemo.commands.SequentialOperationExecutor;
import org.demo.nosql.redisdemo.storage.InMemoryExpireStore;
import org.demo.nosql.redisdemo.storage.Store;
import org.junit.After;
import org.junit.Before;

public abstract class BaseOperationTest {

    protected OperationExecutor operationExecutor;
    protected Store store;

    @Before
    public void setup() {
        store = new InMemoryExpireStore();
        operationExecutor = new SequentialOperationExecutor(store);
    }

    @After
    public void afterTests() {
        store.stop();
    }

}
