package com.dev.jg.controller;

import com.dev.jg.model.ElasticSearchResponse;
import com.dev.jg.service.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/dev")
public class ElasticSearchController {

    private final ElasticSearchService elasticSearchService;

    @GetMapping("")
    public String index(){
        return "index page";
    }

    @GetMapping("test")
    public void test(){
        log.info(">>>>>>> test");
    }

    @GetMapping("create")
    public boolean create(){
        return elasticSearchService.create();
    }

    @GetMapping("exists")
    public boolean exists(
            @RequestParam(defaultValue = "kibana_sample_data_ecommerce") String index
    ){
        return elasticSearchService.exists(index);
    }

    @GetMapping("search")
    public ElasticSearchResponse search(){
        return elasticSearchService.search();
    }
}
