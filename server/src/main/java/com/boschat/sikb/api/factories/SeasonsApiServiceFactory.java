package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.SeasonsApiService;
import com.boschat.sikb.api.impl.SeasonsApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-02-18T09:58:22.375+01:00[Europe/Paris]")
public class SeasonsApiServiceFactory {

    private final static SeasonsApiService service = new SeasonsApiServiceImpl();

    public static SeasonsApiService getSeasonsApi() {
        return service;
    }
}
