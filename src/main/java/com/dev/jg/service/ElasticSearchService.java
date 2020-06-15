package com.dev.jg.service;

import com.dev.jg.model.BaseDocument;
import com.dev.jg.model.ElasticSearchResponse;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.search.ClearScrollResponse;

public interface ElasticSearchService {

    boolean create();

    boolean exists(String index);

    FlushResponse flush(String index);

    FlushResponse flushAll();

    ElasticSearchResponse search();

    ElasticSearchResponse searchGetScroll();

    ElasticSearchResponse searchScroll(String scrollId);

    ClearScrollResponse scrollClear(String scrollId);

    BaseDocument getDocument(String docId, Class returnType);
}
