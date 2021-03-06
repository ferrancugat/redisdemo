package org.demo.nosql.redisdemo.operation;

import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.storage.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.demo.nosql.redisdemo.domain.RedisResponseCode.RESPONSE_VALIDATION;

public abstract class AbstractRedisOperation implements RedisOperation {

    protected final String command;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractRedisOperation(String name) {
        this.command = name;
    }

    @Override
    public RedisResponse execute(Store db, RedisRequest request) {
        logger.debug("Operation of {} requested by: {}", command, request);
        boolean isValid = validate(request);
        if (!isValid) {
            logger.error("Validation error with request: {}", request);
            return new RedisResponse(RESPONSE_VALIDATION);
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
