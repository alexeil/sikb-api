package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.UsersApiService;
import com.boschat.sikb.api.impl.UsersApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-19T12:29:54.648+01:00[Europe/Paris]")
public class UsersApiServiceFactory {

    private final static UsersApiService service = new UsersApiServiceImpl();

    public static UsersApiService getUsersApi() {
        return service;
    }
}
