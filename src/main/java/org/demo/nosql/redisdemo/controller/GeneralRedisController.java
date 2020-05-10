package org.demo.nosql.redisdemo.controller;

import org.demo.nosql.redisdemo.service.GeneralCommandRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.ok;

@Api(value = "General Redis Operations")
@Controller
@RequestMapping(value = "redis/commands")
public class GeneralRedisController {

    @Autowired
    protected GeneralCommandRedisService service;


    @ApiOperation(value = "Get the DB size.")
    @GetMapping("dbsize")
    public ResponseEntity<Long> dbSize() {

        Optional<Long> optionalDBSize = service.dbSize();

        if (optionalDBSize.isPresent()) {
            return ok(optionalDBSize.get()); //Review redis protocol
        } else {
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }
}
