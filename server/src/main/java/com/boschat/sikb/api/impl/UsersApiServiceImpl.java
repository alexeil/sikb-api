package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.UsersApiService;
import com.boschat.sikb.model.ConfirmPassword;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.Reset;
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
import static com.boschat.sikb.api.CallType.USER_LOGOUT;
import static com.boschat.sikb.api.CallType.USER_RESET;
import static com.boschat.sikb.api.CallType.USER_UPDATE;
import static com.boschat.sikb.api.CallType.USER_UPDATE_PASSWORD;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-19T12:29:54.648+01:00[Europe/Paris]")
public class UsersApiServiceImpl extends UsersApiService {

    @Override
    public Response createUser(String accessToken, UserForCreation userForCreation, SecurityContext securityContext) {
        return runService(USER_CREATE, accessToken, securityContext, userForCreation);
    }

    @Override
    public Response deleteUser(String accessToken, Integer userId, SecurityContext securityContext) {
        return runService(USER_DELETE, accessToken, securityContext, userId);
    }

    @Override
    public Response findUsers(String accessToken, SecurityContext securityContext) {
        return runService(USER_FIND, accessToken, securityContext);
    }

    @Override
    public Response userConfirm(String token, ConfirmPassword confirmPassword, SecurityContext securityContext) {
        return runService(USER_CONFIRM, null, securityContext, token, confirmPassword);
    }

    @Override
    public Response getUser(String accessToken, Integer userId, SecurityContext securityContext) {
        return runService(USER_GET, accessToken, securityContext, userId);
    }

    @Override
    public Response updateUser(String accessToken, Integer userId, UserForUpdate userForUpdate, SecurityContext securityContext) {
        return runService(USER_UPDATE, accessToken, securityContext, userId, userForUpdate);
    }

    @Override
    public Response userLogin(Credentials loginData, SecurityContext securityContext) {
        return runService(USER_LOGIN, null, securityContext, loginData);
    }

    @Override
    public Response userLogout(String accessToken, SecurityContext securityContext) {
        return runService(USER_LOGOUT, accessToken, securityContext);
    }

    @Override
    public Response userReset(Reset reset, SecurityContext securityContext) {
        return runService(USER_RESET, null, securityContext, reset);

    }

    @Override
    public Response userUpdatePassword(UpdatePassword updatePassword, String accessToken, String token, SecurityContext securityContext) {
        return runService(USER_UPDATE_PASSWORD, accessToken, securityContext, updatePassword);
    }

}
