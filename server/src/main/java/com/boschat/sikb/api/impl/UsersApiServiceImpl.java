package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.NotFoundException;
import com.boschat.sikb.api.UsersApiService;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.model.UserForCreation;
import com.boschat.sikb.model.UserForUpdate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.USER_CONFIRM;
import static com.boschat.sikb.api.CallType.USER_CREATE;
import static com.boschat.sikb.api.CallType.USER_DELETE;
import static com.boschat.sikb.api.CallType.USER_FIND;
import static com.boschat.sikb.api.CallType.USER_GET;
import static com.boschat.sikb.api.CallType.USER_LOGIN;
import static com.boschat.sikb.api.CallType.USER_UPDATE;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-19T12:29:54.648+01:00[Europe/Paris]")
public class UsersApiServiceImpl extends UsersApiService {

    @Override
    public Response createUser(UserForCreation userForCreation, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_CREATE, null, userForCreation);
    }

    @Override
    public Response deleteUser(Integer userId, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_DELETE, null, userId);
    }

    @Override
    public Response findUsers(SecurityContext securityContext) throws NotFoundException {
        return runService(USER_FIND, null);
    }

    @Override
    public Response userConfirm(String token, UpdatePassword updatePassword, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_CONFIRM, null, token, updatePassword);
    }

    @Override
    public Response getUser(String accessToken, Integer userId, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_GET, accessToken, userId);
    }

    @Override
    public Response updateUser(String accessToken, Integer userId, UserForUpdate userForUpdate, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_UPDATE, accessToken, userId, userForUpdate);
    }

    @Override
    public Response userLogin(Credentials loginData, SecurityContext securityContext) throws NotFoundException {
        return runService(USER_LOGIN, null, loginData);
    }

    @Override
    public Response userLogout(String accessToken, SecurityContext securityContext) throws NotFoundException {
        return null;
    }

    @Override
    public Response userReset(String accessToken, SecurityContext securityContext) throws NotFoundException {
        return null;
    }

    @Override
    public Response userUpdatePassword(String accessToken, UpdatePassword updatePassword, SecurityContext securityContext) throws NotFoundException {
        return null;
    }

}
