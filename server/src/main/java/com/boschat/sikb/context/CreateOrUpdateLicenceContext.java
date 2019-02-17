package com.boschat.sikb.context;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.model.LicenceForCreation;
import com.boschat.sikb.model.LicenceForUpdate;

import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.INVALID_BODY_FIELD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_LICENCE_TYPE;
import static com.boschat.sikb.common.utils.IntegerUtils.toIntegerArray;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class CreateOrUpdateLicenceContext {

    private Integer[] typeLicences;

    private Integer[] formationNeed;

    private static CreateOrUpdateLicenceContext buildCommon(LicenceForUpdate licence) {
        if (licence.getTypeLicences() != null && licence.getTypeLicences().isEmpty()) {
            throw new FunctionalException(INVALID_BODY_FIELD, BODY_FIELD_LICENCE_TYPE, licence.getTypeLicences());
        }

        CreateOrUpdateLicenceContext createOrUpdateContext = new CreateOrUpdateLicenceContext();
        createOrUpdateContext.setFormationNeed(licence.getFormationNeed());
        createOrUpdateContext.setTypeLicences(licence.getTypeLicences());
        return createOrUpdateContext;
    }

    public static CreateOrUpdateLicenceContext create(LicenceForUpdate licence) {
        return buildCommon(licence);
    }

    public static CreateOrUpdateLicenceContext create(LicenceForCreation licence) {
        checkRequestBodyField(licence.getTypeLicences(), BODY_FIELD_LICENCE_TYPE);
        return buildCommon(licence);
    }

    public Integer[] getTypeLicences() {
        return typeLicences;
    }

    public void setTypeLicences(List<Integer> typeLicences) {
        this.typeLicences = toIntegerArray(typeLicences);
    }

    public Integer[] getFormationNeed() {
        return formationNeed;
    }

    public void setFormationNeed(List<Integer> formationNeed) {
        this.formationNeed = toIntegerArray(formationNeed);
    }
}
