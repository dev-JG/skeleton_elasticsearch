package com.dev.jg.service.imp;

import com.dev.jg.service.ElasticSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
@AllArgsConstructor
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final String TEST_INDEX = "kibana_sample_data_ecommerce";

    private final RestHighLevelClient client;

    //https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high-create-index.html
    @Override
    public boolean create() {
        CreateIndexRequest request = new CreateIndexRequest("users");

        request.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 2)
        );

        Map<String, Object> message = new HashMap<>();
        message.put("type", "text");

        Map<String, Object> properties = new HashMap<>();
        properties.put("userId", message);
        properties.put("name", message);

        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);

        request.mapping(mapToJson(mapping), XContentType.JSON);

        try {
            CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            //결과를 비동기 처리할 경우
//        CreateIndexResponse indexResponse = client.indices().createAsync(request, RequestOptions.DEFAULT, listener);
            log.info("response id: " + indexResponse.index());
        } catch (Exception e) {
            log.info("error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean exists() {
        GetIndexRequest request = new GetIndexRequest(TEST_INDEX);

        try {
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            log.info(">>> find index: {}, exists: {}", TEST_INDEX, exists);
            return exists;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    @Override
    public void search() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("customer_gender", "MALE"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(TEST_INDEX);
        searchRequest.types("_doc");
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info(">>> searchResponse: {}", searchResponse);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public String mapToJson(Map<String, Object> map) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";

        try {
            json = mapper.writeValueAsString(map);
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
