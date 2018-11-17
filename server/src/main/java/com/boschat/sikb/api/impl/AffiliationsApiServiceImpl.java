package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.AffiliationsApiService;
import com.boschat.sikb.api.ApiResponseMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-17T13:30:50.718+01:00[Europe/Paris]")
public class AffiliationsApiServiceImpl extends AffiliationsApiService {
    @Override
    public Response createAffiliation(SecurityContext securityContext) {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response deleteAffiliation(SecurityContext securityContext) {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response findAffiliations(SecurityContext securityContext) {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response getAffiliation(SecurityContext securityContext) {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response updateAffiliation(SecurityContext securityContext) {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
