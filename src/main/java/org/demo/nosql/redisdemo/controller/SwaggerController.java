package org.demo.nosql.redisdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
public class SwaggerController {

    @ApiIgnore
    @GetMapping({"/"})
    public String apiDocumentation() {
        return "redirect:/swagger-ui.html";
    }
}
