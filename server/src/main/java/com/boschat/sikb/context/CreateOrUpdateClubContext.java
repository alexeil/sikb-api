package com.boschat.sikb.context;

import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;

import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_NAME;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_SHORT_NAME;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class CreateOrUpdateClubContext {

    private String name;

    private String shortName;

    private String logo;

    private static CreateOrUpdateClubContext buildCommon(ClubForUpdate clubForUpdate) {
        CreateOrUpdateClubContext createOrUpdateContext = new CreateOrUpdateClubContext();
        createOrUpdateContext.setName(clubForUpdate.getName());
        createOrUpdateContext.setShortName(clubForUpdate.getShortName());
        createOrUpdateContext.setLogo(clubForUpdate.getLogo());
        return createOrUpdateContext;
    }

    public static CreateOrUpdateClubContext create(ClubForCreation clubForCreation) {
        checkRequestBodyField(clubForCreation.getName(), BODY_FIELD_NAME);
        checkRequestBodyField(clubForCreation.getShortName(), BODY_FIELD_SHORT_NAME);
        return buildCommon(clubForCreation);
    }

    public static CreateOrUpdateClubContext create(ClubForUpdate clubForUpdate) {
        return buildCommon(clubForUpdate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
