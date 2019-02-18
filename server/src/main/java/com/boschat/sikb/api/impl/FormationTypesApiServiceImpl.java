package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.FormationTypesApiService;
import com.boschat.sikb.api.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.FORMATION_TYPE_FIND;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-02-18T09:58:22.375+01:00[Europe/Paris]")
public class FormationTypesApiServiceImpl extends FormationTypesApiService {

    @Override
    public Response findFormationTypes(String accessToken, SecurityContext securityContext) throws NotFoundException {
        return runService(FORMATION_TYPE_FIND, accessToken, securityContext);
    }
}
