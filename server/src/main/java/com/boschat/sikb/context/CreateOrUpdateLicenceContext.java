package com.boschat.sikb.context;

import com.boschat.sikb.model.LicenceForUpdate;

import java.util.List;

public class CreateOrUpdateLicenceContext {

    private List<Integer> typeLicences;

    private List<Integer> formationNeed;

    public static CreateOrUpdateLicenceContext create(LicenceForUpdate licence) {
        CreateOrUpdateLicenceContext createOrUpdateContext = new CreateOrUpdateLicenceContext();
        createOrUpdateContext.setFormationNeed(licence.getFormationNeed());
        createOrUpdateContext.setTypeLicences(licence.getTypeLicences());
        return createOrUpdateContext;
    }

    public List<Integer> getTypeLicences() {
        return typeLicences;
    }

    public void setTypeLicences(List<Integer> typeLicences) {
        this.typeLicences = typeLicences;
    }

    public List<Integer> getFormationNeed() {
        return formationNeed;
    }

    public void setFormationNeed(List<Integer> formationNeed) {
        this.formationNeed = formationNeed;
    }
}
