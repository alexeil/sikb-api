package com.boschat.sikb.api;

import com.boschat.sikb.CreateOrUpdateAffiliationContext;
import com.boschat.sikb.CreateOrUpdateClubContext;
import com.boschat.sikb.MyThreadLocal;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;

import static com.boschat.sikb.Helper.UpdateClub;
import static com.boschat.sikb.Helper.convertBeanToModel;
import static com.boschat.sikb.Helper.convertBeansToModels;
import static com.boschat.sikb.Helper.createAffiliation;
import static com.boschat.sikb.Helper.createClub;
import static com.boschat.sikb.Helper.deleteAffiliation;
import static com.boschat.sikb.Helper.deleteClub;
import static com.boschat.sikb.Helper.findClubs;
import static com.boschat.sikb.Helper.getAffiliation;
import static com.boschat.sikb.Helper.getClub;
import static com.boschat.sikb.api.ResponseCode.CREATED;
import static com.boschat.sikb.api.ResponseCode.DELETED;
import static com.boschat.sikb.api.ResponseCode.OK;

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
    CLUB_DELETE("Delete a club", DELETED) {
        @Override
        public Object call() {
            deleteClub();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
        }
    },
    CLUB_UPDATE("Update a club", OK) {
        @Override
        public Object call() {
            return convertBeanToModel(UpdateClub());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setCreateOrUpdateClubContext(CreateOrUpdateClubContext.create((ClubForUpdate) params[1]));
        }
    },
    CLUB_GET("get a club", OK) {
        @Override
        public Object call() {
            return convertBeanToModel(getClub());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
        }
    },
    CLUB_FIND("find all clubs", OK) {
        @Override
        public Object call() {
            return convertBeansToModels(findClubs());
        }

        @Override
        public void fillContext(Object... params) {
        }
    },
    AFFILIATION_CREATE("Create an affiliation", CREATED) {
        @Override
        public Object call() {
            return convertBeanToModel(createAffiliation());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeason((String) params[1]);
            MyThreadLocal.get().setCreateOrUpdateAffiliationContext(CreateOrUpdateAffiliationContext.create((AffiliationForCreation) params[2]));
        }
    },
    AFFILIATION_GET("Get an affiliation", OK) {
        @Override
        public Object call() {
            return convertBeanToModel(getAffiliation());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeason((String) params[1]);
        }
    },
    AFFILIATION_DELETE("Delete an affiliation", DELETED) {
        @Override
        public Object call() {
            deleteAffiliation();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeason((String) params[1]);
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
