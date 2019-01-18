package com.boschat.sikb.servlet;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Provider
@Produces({ APPLICATION_JSON })
public class JacksonJsonProvider extends JacksonJaxbJsonProvider {

    private static final Logger LOGGER = LogManager.getLogger(InitServlet.class);

    private static ObjectMapper mapper;

    public JacksonJsonProvider() {

        // override Object mapper to handle Java 8 DateTime
        ObjectMapper objectMapper = createObjectMapper();

        setMapper(objectMapper);
    }

    /**
     * Create a new custom object mapper
     * - disabling FAIL_ON_UNKNOWN_PROPERTIES, WRITE_DATES_AS_TIMESTAMPS and ADJUST_DATES_TO_CONTEXT_TIME_ZONE
     * - adding jave 8 date time with RFC3339 date format
     *
     * @return
     */
    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        return mapper
                //.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                //.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
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

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
            InputStream entityStream) throws IOException {
        try {
            return super.readFrom(type, genericType, annotations, mediaType, httpHeaders, entityStream);
        } catch (Exception e) {
            // Log error when a (de)serialization is raised
          /*  LogMetadata log = new LogMetadata(JSON_ERROR, e);
            log.setTechnicalCodeHttp((short) 400);
            List<String> erableRequestId = httpHeaders.get(ERABLE_REQUEST_ID);
            log.setRequestId(CollectionUtils.isNotEmpty(erableRequestId) ? erableRequestId.get(0) : "UNKNOWN");
            if (CollectionUtils.isNotEmpty(erableRequestId)) {
                log.setRequestId(erableRequestId.get(0));
            } else {
                log.setRequestId("UNKNOWN");
                log.setProcessingMessage("Erable Request Id Not found " + String.join(", ", erableRequestId));
                LOGGER.warn(log);
            }
            LOGGER.error(log);*/
            throw e;
        }
    }
}
