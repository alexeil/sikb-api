package com.boschat.sikb.servlet;

import com.boschat.sikb.model.ApplicationBean;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class ApplicationSecurityContext implements SecurityContext {

    private ApplicationBean application;

    private String scheme;

    public ApplicationSecurityContext(ApplicationBean application, String scheme) {
        this.application = application;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.application;
    }

    @Override
    public boolean isUserInRole(String s) {
        if (application.getRole() != null) {
            return application.getRole().contains(s);
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}