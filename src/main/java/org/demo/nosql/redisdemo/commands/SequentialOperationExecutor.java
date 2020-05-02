package org.demo.nosql.redisdemo.commands;

import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.demo.nosql.redisdemo.operations.RedisOperation;
import org.demo.nosql.redisdemo.storage.Store;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SequentialOperationExecutor implements OperationExecutor {

    private Store database;

    //One of the key benefits of Redis is that it guarantees atomic, ordered access to data.
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SequentialOperationExecutor(Store database) {
        this.database = database;
    }

    public RedisResponse execute(RedisRequest request) {
        RedisOperation operation = Commands.getOperationByCommand(request.getCommand());
        if (operation != null) {
            Future<RedisResponse> future = executorService.submit(() -> operation.execute(database, request));
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Interrupted exception from command executor");
            }
        } else {
            System.out.println("Command Id: " + request.getCommand() + " does not exist");
        }
        return new RedisResponse(RedisResponse.RESPONSE_ERROR);
    }
}
