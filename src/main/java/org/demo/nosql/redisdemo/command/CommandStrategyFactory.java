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

import static org.demo.nosql.redisdemo.command.Commands.DBSIZE_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.DEL_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.GET_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.INCR_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.SETEX_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.SET_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.ZADD_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.ZCARD_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.ZRANGE_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.ZRANK_COMMAND;

/**
 * Command Strategy Factory returns an Operation by code.
 */
public class CommandStrategyFactory {

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

    private CommandStrategyFactory(){

    }

    public static final RedisOperation getOperationByCommand(String command) {
        logger.debug("Operation : {} requested", command);
        return COMMANDS_SUITE.get(command.toUpperCase());
    }

}
