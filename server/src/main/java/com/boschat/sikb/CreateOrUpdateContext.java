package com.boschat.sikb;

import com.boschat.sikb.model.AffiliationForCreation;

public class CreateOrUpdateContext {

    private String associationName;

    private String prefectureNumber;

    private String siretNumber;

    private String address;

    private String postalCode;

    private String city;

    private String phoneNumber;

    private String email;

    private String webSite;

    public static CreateOrUpdateContext create(AffiliationForCreation affiliationForCreation) {
        CreateOrUpdateContext createOrUpdateContext = new CreateOrUpdateContext();
        createOrUpdateContext.setAssociationName(affiliationForCreation.getAssociationName());

        return createOrUpdateContext;

    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public String getPrefectureNumber() {
        return prefectureNumber;
    }

    public void setPrefectureNumber(String prefectureNumber) {
        this.prefectureNumber = prefectureNumber;
    }

    public String getSiretNumber() {
        return siretNumber;
    }

    public void setSiretNumber(String siretNumber) {
        this.siretNumber = siretNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
