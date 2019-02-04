package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.SeasonsApiService;
import com.boschat.sikb.api.impl.SeasonsApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class SeasonsApiServiceFactory {

    private final static SeasonsApiService service = new SeasonsApiServiceImpl();

    private SeasonsApiServiceFactory() {

    }

    public static SeasonsApiService getSeasonsApi() {
        return service;
    }
}
