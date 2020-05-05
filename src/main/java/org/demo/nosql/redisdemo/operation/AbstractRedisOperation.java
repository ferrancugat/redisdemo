package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.List;

public abstract class AbstractRedisOperation implements RedisOperation {

    protected final String command;

    public AbstractRedisOperation(String name) {
        this.command = name;
    }

    @Override
    public RedisResponse execute(Store db, RedisRequest request) {
        boolean isValid = validate(request);
        if (!isValid) {
            return new RedisResponse(RedisResponse.RESPONSE_ERROR);
        }
        return this.execute(db, request.getParams());
    }

    protected boolean validate(RedisRequest request) {
        boolean isValid = command.equalsIgnoreCase(request.getCommand());
        if (isValid) {
            isValid = validateParams(request.getParams());
        }
        return isValid;
    }

    protected abstract boolean validateParams(List<String> params);

    protected abstract RedisResponse execute(Store db, List<String> params);
}
