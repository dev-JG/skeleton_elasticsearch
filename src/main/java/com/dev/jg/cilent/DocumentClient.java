package com.dev.jg.cilent;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class DocumentClient {

    private final RestHighLevelClient client;


}
