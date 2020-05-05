package org.demo.nosql.redisdemo.controller;

import org.demo.nosql.redisdemo.command.OperationExecutor;
import org.demo.nosql.redisdemo.domain.RedisRequest;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static org.demo.nosql.redisdemo.command.Commands.DBSIZE_COMMAND;
import static org.demo.nosql.redisdemo.command.Commands.SET_COMMAND;
import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Api(value = "Redis Operations")
@Controller
@RequestMapping(value = "redis/commands")
public class RedisOperationController {

    @Autowired
    protected OperationExecutor executor;

    @ApiOperation(value = "Update/create a redis entruy.")
    @PutMapping("set")
    public ResponseEntity<String> setCommand(@ApiParam("Redis key") @RequestParam("key") String key,
                                             @ApiParam("Value of the key") @RequestParam("value") String value) {
        RedisRequest request = new RedisRequest(SET_COMMAND, Arrays.asList(key, value));
        RedisResponse response = executor.execute(request);
        if (RESPONSE_OK.equals(response.getCode())) {
            return ok(null); //Review redis protocol
        } else {
            return status(BAD_REQUEST).body(null);
        }
    }

    @ApiOperation(value = "Get the DB size.")
    @GetMapping("dbsize")
    public ResponseEntity<Long> setCommand() {
        RedisRequest request = new RedisRequest(DBSIZE_COMMAND, Collections.emptyList());
        RedisResponse response = executor.execute(request);
        if (RESPONSE_OK.equals(response.getCode())) {
            return ok(response.getValue()
                    .getValue()); //Review redis protocol
        } else {
            return status(BAD_REQUEST).body(null);
        }
    }
}
