package com.dev.jg.service;

import com.dev.jg.model.ElasticSearchResponse;

public interface ElasticSearchService {

    boolean create();

    boolean exists(String index);

    ElasticSearchResponse search();
}
