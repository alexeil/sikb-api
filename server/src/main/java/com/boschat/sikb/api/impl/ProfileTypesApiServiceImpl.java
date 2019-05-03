package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.NotFoundException;
import com.boschat.sikb.api.ProfileTypesApiService;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.PROFILE_TYPE_FIND;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-06T10:30:38.839+02:00[Europe/Paris]")
public class ProfileTypesApiServiceImpl extends ProfileTypesApiService {

    @Override
    public Response findProfileTypes(String accessToken, SecurityContext securityContext) throws NotFoundException {
        return runService(PROFILE_TYPE_FIND, accessToken, securityContext);
    }
}
