package org.demo.nosql.redisdemo.command;

import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.operation.RedisOperation;
import org.demo.nosql.redisdemo.storage.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.demo.nosql.redisdemo.domain.RedisResponseCode.RESPONSE_ERROR;

public class SequentialOperationExecutor implements OperationExecutor {

    private static Logger logger = LoggerFactory.getLogger(SequentialOperationExecutor.class);

    private Store database;

    //One of the key benefits of Redis is that it guarantees atomic, ordered access to data.
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SequentialOperationExecutor(Store database) {
        this.database = database;
    }

    public RedisResponse execute(RedisRequest request) {
        RedisOperation operation = CommandStrategyFactory.getOperationByCommand(request.getCommand());
        if (operation != null) {
            logger.debug("Submitting the following request: {}", request);
            Future<RedisResponse> future = executorService.submit(() -> operation.execute(database, request));
            try {
                return future.get();
            } catch (InterruptedException e) {
                logger.error("Interrupted exception from command executor",e);
                Thread.currentThread()
                        .interrupt();
            } catch (ExecutionException e) {
                logger.error("Unexpected exception executing command ",e);
            }
        } else {
            logger.warn("Command Id: {} does not exist", request.getCommand());
        }
        return new RedisResponse(RESPONSE_ERROR);
    }
}
