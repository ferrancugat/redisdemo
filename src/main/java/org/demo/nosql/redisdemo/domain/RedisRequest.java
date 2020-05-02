package org.demo.nosql.redisdemo.domain;

import java.util.List;

public class RedisRequest {

    private final String command;
    private final List<String> params;

    public RedisRequest(String command, List<String> params) {
        this.command = command;
        this.params = params;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getParams() {
        return params;
    }
}
