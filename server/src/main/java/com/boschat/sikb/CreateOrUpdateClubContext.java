package com.boschat.sikb;

import com.boschat.sikb.model.ClubForCreation;

public class CreateOrUpdateClubContext {

    private String name;

    private String shortName;

    private String logo;

    public static CreateOrUpdateClubContext create(ClubForCreation clubForCreation) {
        CreateOrUpdateClubContext createOrUpdateContext = new CreateOrUpdateClubContext();
        createOrUpdateContext.setName(clubForCreation.getName());
        createOrUpdateContext.setShortName(clubForCreation.getShortName());
        createOrUpdateContext.setLogo(clubForCreation.getLogo());
        return createOrUpdateContext;
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
