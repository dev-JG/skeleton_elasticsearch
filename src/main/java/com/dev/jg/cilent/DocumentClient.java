package com.dev.jg.cilent;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@AllArgsConstructor
public class DocumentClient {

    private final RestHighLevelClient client;

    public GetResponse getDocument(String docId, String index, String type) {
        GetRequest getRequest = new GetRequest(index, type, docId);

        try {
            return client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("getDocument: id={}", docId, e);
            return null;
        }
    }
}
