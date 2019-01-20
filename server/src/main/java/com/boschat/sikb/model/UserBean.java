package com.boschat.sikb.model;

import java.security.Principal;
import java.util.List;

public class UserBean implements Principal {

    private String id;

    private String email;

    private String password;

    private String information;

    private List<Integer> clubId;

    private List<String> role;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public List<Integer> getClubId() {
        return clubId;
    }

    public void setClubId(List<Integer> clubId) {
        this.clubId = clubId;
    }

    @Override
    public String getName() {
        return this.email;
    }
}