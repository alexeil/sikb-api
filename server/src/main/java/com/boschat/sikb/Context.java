package com.boschat.sikb;

import com.boschat.sikb.api.CallType;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.tables.pojos.User;

import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_NEW_PASSWORD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_OLD_PASSWORD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_UPDATE_PASSWORD;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class Context {

    private CreateOrUpdateAffiliationContext createOrUpdateAffiliationContext;

    private CreateOrUpdateClubContext createOrUpdateClubContext;

    private CallType callType;

    private String accessToken;

    private Integer clubId;

    private String season;

    private CreateOrUpdateUserContext createOrUpdateUserContext;

    private Integer userId;

    private Credentials credentials;

    private String token;

    private UpdatePassword updatePassword;

    private User currentUser;

    public Context(CallType callType, String accessToken) {
        this.callType = callType;
        this.accessToken = accessToken;
    }

    public CreateOrUpdateAffiliationContext getCreateOrUpdateAffiliationContext() {
        return createOrUpdateAffiliationContext;
    }

    public void setCreateOrUpdateAffiliationContext(CreateOrUpdateAffiliationContext createOrUpdateAffiliationContext) {
        this.createOrUpdateAffiliationContext = createOrUpdateAffiliationContext;
    }

    public CreateOrUpdateClubContext getCreateOrUpdateClubContext() {
        return createOrUpdateClubContext;
    }

    public void setCreateOrUpdateClubContext(CreateOrUpdateClubContext createOrUpdateClubContext) {
        this.createOrUpdateClubContext = createOrUpdateClubContext;
    }

    public CallType getCallType() {
        return callType;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public CreateOrUpdateUserContext getCreateOrUpdateUserContext() {
        return createOrUpdateUserContext;
    }

    public void setCreateOrUpdateUserContext(CreateOrUpdateUserContext createOrUpdateUserContext) {
        this.createOrUpdateUserContext = createOrUpdateUserContext;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UpdatePassword getUpdatePassword() {
        return updatePassword;
    }

    public void setUpdatePassword(UpdatePassword updatePassword, boolean checkOldPassword) {
        checkRequestBodyField(updatePassword, BODY_FIELD_UPDATE_PASSWORD);
        checkRequestBodyField(updatePassword.getNewPassword(), BODY_FIELD_NEW_PASSWORD);
        if (checkOldPassword) {
            checkRequestBodyField(updatePassword.getOldPassword(), BODY_FIELD_OLD_PASSWORD);
        }
        this.updatePassword = updatePassword;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
