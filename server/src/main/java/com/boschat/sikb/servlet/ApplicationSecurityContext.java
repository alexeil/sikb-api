package com.boschat.sikb.servlet;

import com.boschat.sikb.model.UserBean;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class ApplicationSecurityContext implements SecurityContext {

    private UserBean user;

    private String scheme;

    public ApplicationSecurityContext(UserBean user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.user;
    }

    @Override
    public boolean isUserInRole(String s) {
        if (user.getRole() != null) {
            return user.getRole().contains(s);
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