package com.dev.jg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.elasticsearch.search.aggregations.Aggregations;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ElasticSearchResponse {

    private List<Product> searchHits;

    private String scrollId;

    private int totalShards;

    private int successfulShards;

    private int skippedShards;

    private Aggregations aggregations;

    @Setter
    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        private String index;
        private String type;
        private String id;
        private long score;
        private Source sourceAsMap;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Source {
        private String currency;
        private String customerFirstName;
        private String customerLastName;
        private String customerFullName;
        private String customerGender;
        private String customerId;
    }
}
