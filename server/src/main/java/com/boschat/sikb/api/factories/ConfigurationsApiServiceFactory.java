package com.boschat.sikb.api.factories;

import com.boschat.sikb.api.ConfigurationsApiService;
import com.boschat.sikb.api.impl.ConfigurationsApiServiceImpl;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class ConfigurationsApiServiceFactory {

    private final static ConfigurationsApiService service = new ConfigurationsApiServiceImpl();

    private ConfigurationsApiServiceFactory() {

    }

    public static ConfigurationsApiService getConfigurationsApi() {
        return service;
    }
}
