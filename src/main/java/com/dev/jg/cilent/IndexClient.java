package com.dev.jg.cilent;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class IndexClient {

    private final RestHighLevelClient client;

    public boolean create() {
        //todo 기능구현해야함.

        return false;
    }

    public boolean exists(String index) {
        try {
            GetIndexRequest request = new GetIndexRequest();
            request.indices(index);
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            log.info(">>> find index: {}, exists: {}", index, exists);
            return exists;
        } catch (Exception e) {
            log.info("exists: {}", e.getMessage());
            return false;
        }
    }

    public FlushResponse flush(String index) {
        FlushRequest request = new FlushRequest(index);

        try {
            FlushResponse flushResponse = client.indices().flush(request, RequestOptions.DEFAULT);
            log.info("flush result: {}", flushResponse);
            return flushResponse;
        } catch (Exception e) {
            log.info("flush: {}", e.getMessage());
            return null;
        }
    }

    public FlushResponse flushAll() {
        FlushRequest requestAll = new FlushRequest();

        try {
            FlushResponse flushResponse = client.indices().flush(requestAll, RequestOptions.DEFAULT);
            log.info("flush all result: {}", flushResponse);
            return flushResponse;
        } catch (Exception e) {
            log.info("flush all: {}", e.getMessage());
            return null;
        }
    }
}
