package com.boschat.sikb.context;

import com.boschat.sikb.model.UserForUpdate;

public class CreateOrUpdateUserContext {

    private String email;

    public static CreateOrUpdateUserContext create(UserForUpdate userForUpdate) {
        CreateOrUpdateUserContext createOrUpdateContext = new CreateOrUpdateUserContext();
        createOrUpdateContext.setEmail(userForUpdate.getEmail());
        return createOrUpdateContext;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
