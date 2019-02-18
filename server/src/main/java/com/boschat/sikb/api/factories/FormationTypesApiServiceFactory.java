package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.FormationTypesApiService;
import com.boschat.sikb.api.impl.FormationTypesApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-02-18T09:58:22.375+01:00[Europe/Paris]")
public class FormationTypesApiServiceFactory {

    private final static FormationTypesApiService service = new FormationTypesApiServiceImpl();

    private FormationTypesApiServiceFactory() {
    
    }

    public static FormationTypesApiService getFormationTypesApi() {
        return service;
    }
}
