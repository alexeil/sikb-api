package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.NotFoundException;
import com.boschat.sikb.api.UsersApiService;
import com.boschat.sikb.model.UserForCreation;
import com.boschat.sikb.model.UserForUpdate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.USER_CREATE;
import static com.boschat.sikb.api.CallType.USER_DELETE;
import static com.boschat.sikb.api.CallType.USER_FIND;
import static com.boschat.sikb.api.CallType.USER_GET;
import static com.boschat.sikb.api.CallType.USER_UPDATE;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-19T12:29:54.648+01:00[Europe/Paris]")
public class UsersApiServiceImpl extends UsersApiService {

    @Override
    public Response createUser(UserForCreation userForCreation, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_CREATE, userForCreation);
    }

    @Override
    public Response deleteUser(Integer userId, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_DELETE, userId);
    }

    @Override
    public Response findUsers(SecurityContext securityContext) throws NotFoundException {
        return runService(USER_FIND);
    }

    @Override
    public Response getUser(Integer userId, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_GET, userId);
    }

    @Override
    public Response updateUser(Integer userId, UserForUpdate userForUpdate, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_UPDATE, userId, userForUpdate);
    }
}
