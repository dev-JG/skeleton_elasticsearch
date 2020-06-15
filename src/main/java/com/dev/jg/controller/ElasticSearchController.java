package com.dev.jg.controller;

import com.dev.jg.model.BaseDocument;
import com.dev.jg.model.ElasticSearchResponse;
import com.dev.jg.service.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/es")
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
            @RequestParam(defaultValue = "kibana_sample_data_ecommerce") String index){
        return elasticSearchService.exists(index);
    }

    @GetMapping("flush")
    public FlushResponse flush(
            @RequestParam(defaultValue = "kibana_sample_data_ecommerce") String index){
        return elasticSearchService.flush(index);
    }

    @GetMapping("flushAll")
    public FlushResponse flushAll(){
        return elasticSearchService.flushAll();
    }

    @GetMapping("search")
    public ElasticSearchResponse search(){
        return elasticSearchService.search();
    }

    @GetMapping("searchGetScroll")
    public ElasticSearchResponse searchGetScroll(){
        return elasticSearchService.searchGetScroll();
    }

    @GetMapping("searchScroll")
    public ElasticSearchResponse searchScroll(
            @RequestParam(defaultValue = "") String scrollId){
        return elasticSearchService.searchScroll(scrollId);
    }

    @GetMapping("scrollClear")
    public ClearScrollResponse scrollClear(
            @RequestParam(defaultValue = "") String scrollId){
        return elasticSearchService.scrollClear(scrollId);
    }

    @GetMapping("document")
    public BaseDocument getDocument(
            @RequestParam(defaultValue = "s20gj3IBUpV-rI_RkxWr") String docId){
        return elasticSearchService.getDocument(docId, BaseDocument.class);
    }
}
