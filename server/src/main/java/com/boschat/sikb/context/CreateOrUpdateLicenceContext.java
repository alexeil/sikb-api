package com.boschat.sikb.context;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.model.LicenceForCreation;
import com.boschat.sikb.model.LicenceForUpdate;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.INVALID_BODY_FIELD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_LICENCE_TYPE;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class CreateOrUpdateLicenceContext {

    private List<Integer> typeLicences;

    private List<Integer> formationNeed;

    private static CreateOrUpdateLicenceContext buildCommon(LicenceForUpdate licence) {
        if (CollectionUtils.isEmpty(licence.getTypeLicences())) {
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
