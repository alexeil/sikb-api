package com.boschat.sikb.context;

import com.boschat.sikb.api.CallType;
import com.boschat.sikb.model.ConfirmPassword;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.Functionality;
import com.boschat.sikb.model.Reset;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.tables.pojos.User;

import java.util.List;

import static com.boschat.sikb.Helper.convertBeanToModel;
import static com.boschat.sikb.common.configuration.ApplicationProperties.CHECK_QUERY_STRING_SEASON_ID_REGEXP;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_CONFIRM_PASSWORD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_UPDATE_PASSWORD;
import static com.boschat.sikb.common.configuration.SikbConstants.QUERY_STRING_SEASON_ID;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;
import static com.boschat.sikb.utils.CheckUtils.checkRequestQueryStringParam;

public class Context {

    private CreateOrUpdateAffiliationContext createOrUpdateAffiliationContext;

    private CreateOrUpdateClubContext createOrUpdateClubContext;

    private CallType callType;

    private String accessToken;

    private Integer clubId;

    private CreateOrUpdateUserContext createOrUpdateUserContext;

    private CreateOrUpdatePersonContext createOrUpdatePersonContext;

    private Integer userId;

    private Credentials credentials;

    private String token;

    private UpdatePassword updatePassword;

    private User currentUser;

    private List<Functionality> currentUserFunctionalities;

    private Reset reset;

    private Integer personId;

    private String seasonId;

    private CreateOrUpdateSeasonContext createOrUpdateSeasonContext;

    private String licenceId;

    private CreateOrUpdateLicenceContext createOrUpdateLicenceContext;

    private CreateOrUpdateTeamContext createOrUpdateTeamContext;

    private Integer teamId;

    private ConfirmPassword confirmPassword;

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

    public void setUpdatePassword(UpdatePassword updatePassword) {
        this.updatePassword = checkRequestBodyField(updatePassword, BODY_FIELD_UPDATE_PASSWORD);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.currentUserFunctionalities = convertBeanToModel(currentUser).getProfile().getType().getFunctionalities();
    }

    public Reset getReset() {
        return reset;
    }

    public void setReset(Reset reset) {
        this.reset = reset;
    }

    public CreateOrUpdatePersonContext getCreateOrUpdatePersonContext() {
        return createOrUpdatePersonContext;
    }

    public void setCreateOrUpdatePersonContext(CreateOrUpdatePersonContext createOrUpdatePersonContext) {
        this.createOrUpdatePersonContext = createOrUpdatePersonContext;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = checkRequestQueryStringParam(seasonId, QUERY_STRING_SEASON_ID, CHECK_QUERY_STRING_SEASON_ID_REGEXP.getValue());
    }

    public CreateOrUpdateSeasonContext getCreateOrUpdateSeasonContext() {
        return createOrUpdateSeasonContext;
    }

    public void setCreateOrUpdateSeasonContext(CreateOrUpdateSeasonContext createOrUpdateSeasonContext) {
        this.createOrUpdateSeasonContext = createOrUpdateSeasonContext;
    }

    public String getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }

    public CreateOrUpdateLicenceContext getCreateOrUpdateLicenceContext() {
        return createOrUpdateLicenceContext;
    }

    public void setCreateOrUpdateLicenceContext(CreateOrUpdateLicenceContext createOrUpdateLicenceContext) {
        this.createOrUpdateLicenceContext = createOrUpdateLicenceContext;
    }

    public CreateOrUpdateTeamContext getCreateOrUpdateTeamContext() {
        return createOrUpdateTeamContext;
    }

    public void setCreateOrUpdateTeamContext(CreateOrUpdateTeamContext createOrUpdateTeamContext) {
        this.createOrUpdateTeamContext = createOrUpdateTeamContext;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public ConfirmPassword getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(ConfirmPassword confirmPassword) {
        this.confirmPassword = checkRequestBodyField(confirmPassword, BODY_FIELD_CONFIRM_PASSWORD);
    }

    public List<Functionality> getCurrentUserFunctionalities() {
        return currentUserFunctionalities;
    }

    public boolean hasCurrentUserRight(Functionality functionality) {
        return functionality == null || getCurrentUserFunctionalities().contains(functionality);
    }
}
