package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.PersonsApiService;
import com.boschat.sikb.api.impl.PersonsApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class PersonsApiServiceFactory {

    private final static PersonsApiService service = new PersonsApiServiceImpl();

    private PersonsApiServiceFactory() {

    }

    public static PersonsApiService getPersonsApi() {
        return service;
    }
}
