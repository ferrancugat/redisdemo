package org.demo.nosql.redisdemo.service;

import org.demo.nosql.redisdemo.domain.RedisRequest;

import java.util.Arrays;

import static org.demo.nosql.redisdemo.command.Commands.SET_COMMAND;

public class CrudCommandRedisServiceImpl extends BaseRedisService implements CrudCommandRedisService {

    @Override
    public void set(String key, String value) {
        RedisRequest request = new RedisRequest(SET_COMMAND, Arrays.asList(key, value));
        processRequest(request);
    }
}
