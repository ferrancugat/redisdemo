package org.demo.nosql.redisdemo.commands;

import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;

public interface OperationExecutor {

    RedisResponse execute(RedisRequest request);
}
