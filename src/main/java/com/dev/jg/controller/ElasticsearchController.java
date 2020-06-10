package com.dev.jg.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/dev")
public class ElasticsearchController {

    @GetMapping("")
    public String index(){
        return "index page";
    }

    @GetMapping("test")
    public void getUser(){
        log.info(">>>>>>> test");
    }
}
