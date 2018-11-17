package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.AffiliationsApiService;
import com.boschat.sikb.api.impl.AffiliationsApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-17T13:30:50.718+01:00[Europe/Paris]")
public class AffiliationsApiServiceFactory {
    private final static AffiliationsApiService service = new AffiliationsApiServiceImpl();

    public static AffiliationsApiService getAffiliationsApi() {
        return service;
    }
}
