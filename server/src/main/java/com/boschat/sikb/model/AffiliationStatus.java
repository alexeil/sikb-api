package com.boschat.sikb.model;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.context.MyThreadLocal;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.NOT_ENOUGH_RIGHT;
import static com.boschat.sikb.common.configuration.ResponseCode.TRANSITION_FORBIDDEN;
import static com.boschat.sikb.model.Functionality.AFFILIATION_CREATE;
import static com.boschat.sikb.model.Functionality.AFFILIATION_REJECT;
import static com.boschat.sikb.model.Functionality.AFFILIATION_VALIDATE;

/**
 * Gets or Sets affiliationStatus
 */
public enum AffiliationStatus {

    TO_COMPLETE(Collections.singletonList("SUBMITTED"), AFFILIATION_REJECT),

    SUBMITTED(Arrays.asList("VALIDATED", "TO_COMPLETE"), AFFILIATION_CREATE),

    VALIDATED(null, AFFILIATION_VALIDATE);

    private List<String> nextStatus;

    private Functionality requiredFunctionality;

    AffiliationStatus(List<String> nextStatus, Functionality requiredFunctionality) {
        this.nextStatus = nextStatus;
        this.requiredFunctionality = requiredFunctionality;
    }

    @JsonCreator
    public static AffiliationStatus fromValue(String text) {
        for (AffiliationStatus b : AffiliationStatus.values()) {
            if (b.name().equals(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + text + "'");
    }

    @Override
    public String toString() {
        return name();
    }

    public void checkTransition(AffiliationStatus status) {
        if (!MyThreadLocal.get().hasCurrentUserRight(status.requiredFunctionality)) {
            throw new FunctionalException(NOT_ENOUGH_RIGHT);
        }
        if (nextStatus == null || !nextStatus.contains(status.toString())) {
            throw new FunctionalException(TRANSITION_FORBIDDEN, name(), status);
        }
    }
}

