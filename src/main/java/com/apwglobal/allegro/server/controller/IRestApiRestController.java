package com.apwglobal.allegro.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface IRestApiRestController extends JsonpControllerAdvice {

    @RequestMapping("/rest-api/status")
    @ResponseBody
    String status();

    @RequestMapping(value = "/rest-api/login")
    @ResponseBody
    String login(@RequestParam(value = "code", required = true) String code);

}
