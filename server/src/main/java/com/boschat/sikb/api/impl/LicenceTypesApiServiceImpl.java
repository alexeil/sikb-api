package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.LicenceTypesApiService;
import com.boschat.sikb.api.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.LICENCE_TYPE_FIND;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-02-18T09:58:22.375+01:00[Europe/Paris]")
public class LicenceTypesApiServiceImpl extends LicenceTypesApiService {

    @Override
    public Response findLicenceTypes(String accessToken, SecurityContext securityContext) throws NotFoundException {
        return runService(LICENCE_TYPE_FIND, accessToken, securityContext);
    }
}
