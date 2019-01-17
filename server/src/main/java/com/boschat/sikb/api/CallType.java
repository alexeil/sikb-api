package com.boschat.sikb.api;

import com.boschat.sikb.CreateOrUpdateAffiliationContext;
import com.boschat.sikb.CreateOrUpdateClubContext;
import com.boschat.sikb.MyThreadLocal;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.ClubForCreation;

import static com.boschat.sikb.Helper.convertBeanToModel;
import static com.boschat.sikb.Helper.createAffiliation;
import static com.boschat.sikb.Helper.createClub;
import static com.boschat.sikb.api.ResponseCode.CREATED;

public enum CallType {
    CLUB_CREATE("Create a club", CREATED) {
        @Override
        public Object call() {
            return convertBeanToModel(createClub());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdateClubContext(CreateOrUpdateClubContext.create((ClubForCreation) params[0]));
        }
    },
    AFFILIATION_CREATE("Create an affiliation", CREATED) {
        @Override
        public Object call() {
            return convertBeanToModel(createAffiliation());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdateAffiliationContext(CreateOrUpdateAffiliationContext.create((AffiliationForCreation) params[0]));
        }
    };

    /**
     * info log message to display
     */
    private final String infoLogMessage;

    /**
     * Response code (200,201 etc.)
     */
    private final ResponseCode responseCode;

    CallType(String infoLogMessage, ResponseCode responseCode) {
        this.infoLogMessage = infoLogMessage;
        this.responseCode = responseCode;
    }

    public abstract Object call();

    public abstract void fillContext(Object... params);

    public String getInfoLogMessage() {
        return infoLogMessage;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
