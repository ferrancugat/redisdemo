package org.demo.nosql.redisdemo.service;

import org.demo.nosql.redisdemo.command.OperationExecutor;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisDemoException;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.springframework.beans.factory.annotation.Autowired;

import static org.demo.nosql.redisdemo.domain.RedisResponseCode.RESPONSE_OK;

public abstract class BaseRedisService {

    @Autowired
    private OperationExecutor executor;

    protected DataValue processRequest(RedisRequest redisRequest){
        RedisResponse response=  executor.execute(redisRequest);
        if (RESPONSE_OK != response.getCode()){
            throw new RedisDemoException(response.getCode());
        }
        return response.getValue();
    }

    public OperationExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(OperationExecutor executor) {
        this.executor = executor;
    }
}
