package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.ProfileTypesApiService;
import com.boschat.sikb.api.impl.ProfileTypesApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-06T10:30:38.839+02:00[Europe/Paris]")
public class ProfileTypesApiServiceFactory {

    private final static ProfileTypesApiService service = new ProfileTypesApiServiceImpl();

    public static ProfileTypesApiService getProfileTypesApi() {
        return service;
    }
}
