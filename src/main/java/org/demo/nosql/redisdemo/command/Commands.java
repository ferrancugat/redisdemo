package org.demo.nosql.redisdemo.command;

import org.demo.nosql.redisdemo.operation.DbSizeRedisOperation;
import org.demo.nosql.redisdemo.operation.DeleteRedisOperation;
import org.demo.nosql.redisdemo.operation.GetRedisOperation;
import org.demo.nosql.redisdemo.operation.IncrRedisOperation;
import org.demo.nosql.redisdemo.operation.RedisOperation;
import org.demo.nosql.redisdemo.operation.SetExRedisOperation;
import org.demo.nosql.redisdemo.operation.SetRedisOperation;
import org.demo.nosql.redisdemo.operation.ZaddRedisOperation;
import org.demo.nosql.redisdemo.operation.ZcardRedisOperation;
import org.demo.nosql.redisdemo.operation.ZrangeRedisOperation;
import org.demo.nosql.redisdemo.operation.ZrankRedisOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger logger = LoggerFactory.getLogger(Commands.class);

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
        logger.debug("Operation : {} requested", command);
        return COMMANDS_SUITE.get(command.toUpperCase());
    }

}
