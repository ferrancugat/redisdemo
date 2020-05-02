package org.demo.nosql.redisdemo;

import org.demo.nosql.redisdemo.commands.Commands;
import org.demo.nosql.redisdemo.domain.RedisRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandsHelper {

    public static RedisRequest get(String key) {
        RedisRequest request = new RedisRequest(Commands.GET_COMMAND, Arrays.asList(key));
        return request;
    }

    public static RedisRequest set(String key, String expectedValue) {
        RedisRequest request = new RedisRequest(Commands.SET_COMMAND, Arrays.asList(key, expectedValue));
        return request;
    }

    public static RedisRequest setex(String key, String expectedValue, long expiredAt) {
        RedisRequest request = new RedisRequest(Commands.SETEX_COMMAND, Arrays.asList(key, expiredAt + "", expectedValue));
        return request;
    }

    public static RedisRequest delete(String key) {
        RedisRequest request = new RedisRequest(Commands.DEL_COMMAND, Arrays.asList(key));
        return request;
    }

    public static RedisRequest dbsize() {
        RedisRequest request = new RedisRequest(Commands.DBSIZE_COMMAND,null);
        return request;
    }

    public static RedisRequest incr(String key) {
        RedisRequest request = new RedisRequest(Commands.INCR_COMMAND,Arrays.asList(key));
        return request;
    }

    public static RedisRequest zadd(String key, String ... userScores) {
        List<String> params = (new ArrayList(Arrays.asList(key)));
        params.addAll(Arrays.asList(userScores));
        RedisRequest request = new RedisRequest(Commands.ZADD_COMMAND, params);
        return request;
    }

    public static RedisRequest zcard(String key) {
        RedisRequest request = new RedisRequest(Commands.ZCARD_COMMAND,Arrays.asList(key));
        return request;
    }

    public static RedisRequest zrank(String key) {
        RedisRequest request = new RedisRequest(Commands.ZRANK_COMMAND,Arrays.asList(key));
        return request;
    }

    public static RedisRequest zrange(String key, int start, int stop) {
        List<String> params = new ArrayList<>();
        params.add(key);
        params.add(start+"");
        params.add(stop+"");
        RedisRequest request = new RedisRequest(Commands.ZRANGE_COMMAND, params);
        return request;
    }
}
