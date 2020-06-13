package com.dev.jg.service.imp;

import com.dev.jg.model.ElasticSearchResponse;
import com.dev.jg.service.ElasticSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@AllArgsConstructor
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final String DEFALUT_INDEX = "kibana_sample_data_ecommerce";
    private final String DEFALUT_TYPE = "_doc";

    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    //https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high-create-index.html
    @Override
    public boolean create() {
        return false;
    }

    @Override
    public boolean exists(String index) {
        try {
            GetIndexRequest request = new GetIndexRequest();
            request.indices(index);
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            log.info(">>> find index: {}, exists: {}", index, exists);
            return exists;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    @Override
    public ElasticSearchResponse search() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(DEFALUT_INDEX);
        searchRequest.types(DEFALUT_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        try {
            log.info("searchResponse: {}", searchRequest);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            List<ElasticSearchResponse.Product> products = objectMapper.readValue(mapToJson(searchResponse.getHits().getHits())
                    , new TypeReference<List<ElasticSearchResponse.Product>>(){});
            log.info("products: {}", products);

            ElasticSearchResponse elasticSearchResponse = ElasticSearchResponse.builder()
                    .scrollId(searchResponse.getScrollId())
                    .aggregations(searchResponse.getAggregations())
                    .totalShards(searchResponse.getTotalShards())
                    .successfulShards(searchResponse.getSuccessfulShards())
                    .skippedShards(searchResponse.getSkippedShards())
                    .build();

            log.info("elasticSearchResponse: {}", elasticSearchResponse);

            return elasticSearchResponse;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public String mapToJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";

        try {
            json = mapper.writeValueAsString(o);
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
