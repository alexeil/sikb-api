package com.boschat.sikb.servlet;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Provider
@Produces({ APPLICATION_JSON })
public class JacksonJsonProvider extends JacksonJaxbJsonProvider {

    private static ObjectMapper mapper;

    public JacksonJsonProvider() {
        // override Object mapper to handle Java 8 DateTime
        ObjectMapper objectMapper = createObjectMapper();
        setMapper(objectMapper);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        return mapper
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            .registerModule(new JavaTimeModule());

    }

    public static ObjectMapper getMapper() {
        if (null == mapper) {
            mapper = JacksonJsonProvider.createObjectMapper();
        }
        return mapper;
    }

}
