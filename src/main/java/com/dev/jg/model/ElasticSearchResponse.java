package com.dev.jg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;

@Data
@Builder
@AllArgsConstructor
public class ElasticSearchResponse {

    private String scrollId;

    private int totalShards;

    private int successfulShards;

    private int skippedShards;

    private Aggregations aggregations;

    private long totalHits;

    private long searchHitSize;

    private SearchHits searchHits;
}
