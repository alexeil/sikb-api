package com.boschat.sikb;

import com.boschat.sikb.model.ClubForUpdate;

public class CreateOrUpdateClubContext {

    private String name;

    private String shortName;

    private String logo;

    public static CreateOrUpdateClubContext create(ClubForUpdate clubForUpdate) {
        CreateOrUpdateClubContext createOrUpdateContext = new CreateOrUpdateClubContext();
        createOrUpdateContext.setName(clubForUpdate.getName());
        createOrUpdateContext.setShortName(clubForUpdate.getShortName());
        createOrUpdateContext.setLogo(clubForUpdate.getLogo());
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
