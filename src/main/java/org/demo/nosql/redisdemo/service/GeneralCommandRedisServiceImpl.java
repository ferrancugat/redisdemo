package org.demo.nosql.redisdemo.service;

import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisRequest;

import java.util.Collections;
import java.util.Optional;

import static org.demo.nosql.redisdemo.command.Commands.DBSIZE_COMMAND;

public class GeneralCommandRedisServiceImpl extends BaseRedisService implements GeneralCommandRedisService {

    @Override
    public Optional<Long> dbSize() {
        RedisRequest request = new RedisRequest(DBSIZE_COMMAND, Collections.emptyList());
        DataValue result = processRequest(request);
        return Optional.of(result.getValue());
    }
}
