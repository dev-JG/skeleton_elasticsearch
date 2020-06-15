package com.dev.jg.service.imp;

import com.dev.jg.cilent.DocumentClient;
import com.dev.jg.cilent.IndexClient;
import com.dev.jg.cilent.SearchClient;
import com.dev.jg.model.BaseDocument;
import com.dev.jg.model.ElasticSearchResponse;
import com.dev.jg.service.ElasticSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

//https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high-create-index.html
@Log4j2
@Service
@AllArgsConstructor
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final String DEFALUT_INDEX = "kibana_sample_data_ecommerce";
    private final String DEFALUT_TYPE = "_doc";

    private final IndexClient indexClient;
    private final SearchClient searchClient;
    private final DocumentClient documentClient;

    private final ObjectMapper objectMapper;

    @Override
    public boolean create() {
        return indexClient.create();
    }

    @Override
    public boolean exists(String index) {
        return indexClient.exists(index);
    }

    @Override
    public FlushResponse flush(String index) {
        return indexClient.flush(index);
    }

    @Override
    public FlushResponse flushAll() {
        return indexClient.flushAll();
    }

    @Override
    public ElasticSearchResponse search() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(DEFALUT_INDEX);
        searchRequest.types(DEFALUT_TYPE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        return searchClient.search(searchRequest);
    }

    @Override
    public ElasticSearchResponse searchGetScroll() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(DEFALUT_INDEX);
        searchRequest.types(DEFALUT_TYPE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(5);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(5L));

        return searchClient.search(searchRequest);
    }

    @Override
    public ElasticSearchResponse searchScroll(String scrollId) {
        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(TimeValue.timeValueSeconds(30));

        return searchClient.searchScroll(scrollRequest);
    }

    @Override
    public ClearScrollResponse scrollClear(String scrollId) {

        return searchClient.scrollClear(scrollId);
    }

    @Override
    public BaseDocument getDocument(String docId, Class returnType) {
        GetResponse response = documentClient.getDocument(docId, DEFALUT_INDEX, DEFALUT_TYPE);

        if (response.isExists()) {
            return getResponseToDocument(response, returnType);
        }

        return null;
    }

    public BaseDocument getResponseToDocument(GetResponse response, Class returnType) {
        return jsonToDocument(response.getSourceAsString(), returnType);
    }

    private BaseDocument jsonToDocument(String source, Class returnType) {
        if (source == null) {
            throw new RuntimeException("source is empty.");
        }

        try {
            return (BaseDocument) objectMapper.readValue(source, returnType);
        } catch (IOException e) {
            throw new RuntimeException("object deserialize " + returnType.getName() + " is failed. source: " + source);
        }
    }
}
