package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.LicenceTypesApiService;
import com.boschat.sikb.api.impl.LicenceTypesApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-02-18T09:58:22.375+01:00[Europe/Paris]")
public class LicenceTypesApiServiceFactory {

    private final static LicenceTypesApiService service = new LicenceTypesApiServiceImpl();

    public static LicenceTypesApiService getLicenceTypesApi() {
        return service;
    }
}
