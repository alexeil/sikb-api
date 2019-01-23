package com.boschat.sikb.model;

import com.boschat.sikb.tables.pojos.Application;

import java.security.Principal;
import java.util.List;

public class ApplicationBean implements Principal {

    private Application application;

    private List<String> role;

    public ApplicationBean(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public List<String> getRole() {
        return role;
    }

    @Override
    public String getName() {
        return this.application.getLogin();
    }
}