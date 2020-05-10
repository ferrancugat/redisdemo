package org.demo.nosql.redisdemo.controller;

import org.demo.nosql.redisdemo.service.CrudCommandRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api(value = "Crud Redis Commands")
@Controller
@RequestMapping(value = "redis/commands")
public class CrudRedisController {


    @Autowired
    protected CrudCommandRedisService service;

    @ApiOperation(value = "Update/create a redis entry.")
    @PutMapping("set")
    public ResponseEntity<String> setCommand(@ApiParam("Redis key") @RequestParam("key") String key,
                                             @ApiParam("Value of the key") @RequestParam("value") String value) {
        service.set(key,value);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
