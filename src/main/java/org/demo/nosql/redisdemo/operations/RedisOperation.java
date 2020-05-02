package org.demo.nosql.redisdemo.operations;

import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

public interface RedisOperation {

    RedisResponse execute(Store db, RedisRequest request);
}
