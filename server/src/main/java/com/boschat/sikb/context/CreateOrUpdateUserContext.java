package com.boschat.sikb.context;

import com.boschat.sikb.model.ProfileForCreation;
import com.boschat.sikb.model.UserForCreation;
import com.boschat.sikb.model.UserForUpdate;

import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_EMAIL;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_PROFILE;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class CreateOrUpdateUserContext {

    private String email;

    private ProfileForCreation profile;

    private static CreateOrUpdateUserContext buildCommon(UserForUpdate userForUpdate) {
        CreateOrUpdateUserContext createOrUpdateContext = new CreateOrUpdateUserContext();
        createOrUpdateContext.setEmail(userForUpdate.getEmail());
        createOrUpdateContext.setProfile(userForUpdate.getProfile());
        return createOrUpdateContext;
    }

    public static CreateOrUpdateUserContext create(UserForUpdate userForUpdate) {
        return buildCommon(userForUpdate);
    }

    public static CreateOrUpdateUserContext create(UserForCreation userForCreation) {
        checkRequestBodyField(userForCreation.getEmail(), BODY_FIELD_EMAIL);
        checkRequestBodyField(userForCreation.getProfile(), BODY_FIELD_PROFILE);
        return buildCommon(userForCreation);

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProfileForCreation getProfile() {
        return profile;
    }

    public void setProfile(ProfileForCreation profile) {
        this.profile = profile;
    }
}
