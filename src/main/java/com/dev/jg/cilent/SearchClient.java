package com.dev.jg.cilent;

import com.dev.jg.model.ElasticSearchResponse;
import com.dev.jg.util.Util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@AllArgsConstructor
public class SearchClient {

    private final ObjectMapper objectMapper;
    private final RestHighLevelClient client;

    public ElasticSearchResponse search(SearchRequest searchRequest) {
        try {
            log.info("searchRequest: {}", searchRequest);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            ElasticSearchResponse elasticSearchResponse = makeResponse(searchResponse);
            log.info("elasticSearchResponse: {}", elasticSearchResponse);

            return elasticSearchResponse;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public ElasticSearchResponse searchScroll(SearchScrollRequest scrollRequest) {
        try {
            log.info("scrollRequest: {}", scrollRequest);
            SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);

            ElasticSearchResponse elasticSearchResponse = makeResponse(searchResponse);
            log.info("elasticSearchResponse: {}", elasticSearchResponse);

            return elasticSearchResponse;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public ClearScrollResponse scrollClear(String scrollId) {
        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId(scrollId);
        log.info("scrollId: {}", scrollId);

        try {
            ClearScrollResponse response = client.clearScroll(request, RequestOptions.DEFAULT);
            return response;
        } catch (Exception e) {
            log.info("scrollClear: {}", e.getMessage());
            return null;
        }
    }

    public ElasticSearchResponse makeResponse(SearchResponse searchResponse) {

        return ElasticSearchResponse.builder()
                .scrollId(searchResponse.getScrollId())
                .searchHitSize(searchResponse.getHits().getHits().length)
                .searchHits(searchResponse.getHits())
                .totalHits(searchResponse.getHits().totalHits)
                .aggregations(searchResponse.getAggregations())
                .totalShards(searchResponse.getTotalShards())
                .successfulShards(searchResponse.getSuccessfulShards())
                .skippedShards(searchResponse.getSkippedShards())
                .build();
    }
}
