package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.ClubsApiService;
import com.boschat.sikb.api.impl.ClubsApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class ClubsApiServiceFactory {

    private final static ClubsApiService service = new ClubsApiServiceImpl();

    public static ClubsApiService getClubsApi() {
        return service;
    }
}
