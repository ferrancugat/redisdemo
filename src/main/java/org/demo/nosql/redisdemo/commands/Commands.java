package org.demo.nosql.redisdemo.commands;

import org.demo.nosql.redisdemo.operations.DbSizeRedisOperation;
import org.demo.nosql.redisdemo.operations.DeleteRedisOperation;
import org.demo.nosql.redisdemo.operations.GetRedisOperation;
import org.demo.nosql.redisdemo.operations.IncrRedisOperation;
import org.demo.nosql.redisdemo.operations.RedisOperation;
import org.demo.nosql.redisdemo.operations.SetExRedisOperation;
import org.demo.nosql.redisdemo.operations.SetRedisOperation;
import org.demo.nosql.redisdemo.operations.ZaddRedisOperation;
import org.demo.nosql.redisdemo.operations.ZcardRedisOperation;
import org.demo.nosql.redisdemo.operations.ZrangeRedisOperation;
import org.demo.nosql.redisdemo.operations.ZrankRedisOperation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Commands {

    public static final String GET_COMMAND = "GET";
    public static final String SET_COMMAND = "SET";
    public static final String SETEX_COMMAND = "SETEX";
    public static final String DEL_COMMAND = "DEL";
    public static final String DBSIZE_COMMAND = "DBSIZE";
    public static final String INCR_COMMAND = "INCR";
    public static final String ZADD_COMMAND = "ZADD";
    public static final String ZCARD_COMMAND = "ZCARD";
    public static final String ZRANK_COMMAND = "ZRANK";
    public static final String ZRANGE_COMMAND = "ZRANGE";
    private static final Map<String, RedisOperation> COMMANDS_SUITE = new ConcurrentHashMap<>();

    static {
        COMMANDS_SUITE.put(GET_COMMAND, new GetRedisOperation());
        COMMANDS_SUITE.put(SET_COMMAND, new SetRedisOperation());
        COMMANDS_SUITE.put(SETEX_COMMAND, new SetExRedisOperation());
        COMMANDS_SUITE.put(DEL_COMMAND, new DeleteRedisOperation());
        COMMANDS_SUITE.put(DBSIZE_COMMAND, new DbSizeRedisOperation());
        COMMANDS_SUITE.put(INCR_COMMAND, new IncrRedisOperation());
        COMMANDS_SUITE.put(ZADD_COMMAND, new ZaddRedisOperation());
        COMMANDS_SUITE.put(ZCARD_COMMAND, new ZcardRedisOperation());
        COMMANDS_SUITE.put(ZRANK_COMMAND, new ZrankRedisOperation());
        COMMANDS_SUITE.put(ZRANGE_COMMAND, new ZrangeRedisOperation());
    }

    private Commands() {
    }

    public static final RedisOperation getOperationByCommand(String command) {
        return COMMANDS_SUITE.get(command.toUpperCase());
    }

}
