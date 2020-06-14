package com.dev.jg.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static String mapToJson(Object o) {
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
