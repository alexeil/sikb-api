package com.boschat.sikb.api;

import com.boschat.sikb.CreateOrUpdateAffiliationContext;
import com.boschat.sikb.CreateOrUpdateClubContext;
import com.boschat.sikb.CreateOrUpdateUserContext;
import com.boschat.sikb.MyThreadLocal;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.AffiliationForUpdate;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.model.UserForCreation;
import com.boschat.sikb.model.UserForUpdate;

import static com.boschat.sikb.Helper.confirmUser;
import static com.boschat.sikb.Helper.convertBeanToModel;
import static com.boschat.sikb.Helper.convertBeansToModels;
import static com.boschat.sikb.Helper.convertUserBeansToModels;
import static com.boschat.sikb.Helper.createAffiliation;
import static com.boschat.sikb.Helper.createClub;
import static com.boschat.sikb.Helper.createUser;
import static com.boschat.sikb.Helper.deleteAffiliation;
import static com.boschat.sikb.Helper.deleteClub;
import static com.boschat.sikb.Helper.deleteUser;
import static com.boschat.sikb.Helper.findClubs;
import static com.boschat.sikb.Helper.findUsers;
import static com.boschat.sikb.Helper.getAffiliation;
import static com.boschat.sikb.Helper.getClub;
import static com.boschat.sikb.Helper.getUser;
import static com.boschat.sikb.Helper.loginUser;
import static com.boschat.sikb.Helper.updateAffiliation;
import static com.boschat.sikb.Helper.updateClub;
import static com.boschat.sikb.Helper.updateUser;
import static com.boschat.sikb.api.ResponseCode.CREATED;
import static com.boschat.sikb.api.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.api.ResponseCode.OK;

public enum CallType {
    USER_CREATE("Create a user", CREATED) {
        @Override
        public Object call() {
            return convertBeanToModel(createUser());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdateUserContext(CreateOrUpdateUserContext.create((UserForCreation) params[0]));
        }
    },
    USER_DELETE("Delete a user", NO_CONTENT) {
        @Override
        public Object call() {
            deleteUser();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setUserId((Integer) params[0]);
        }
    },
    USER_UPDATE("Update a User", OK) {
        @Override
        public Object call() {
            return convertBeanToModel(updateUser());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setUserId((Integer) params[0]);
            MyThreadLocal.get().setCreateOrUpdateUserContext(CreateOrUpdateUserContext.create((UserForUpdate) params[1]));
        }
    },
    USER_GET("get a user", OK) {
        @Override
        public Object call() {
            return convertBeanToModel(getUser());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setUserId((Integer) params[0]);
        }
    },
    USER_FIND("find all users", OK) {
        @Override
        public Object call() {
            return convertUserBeansToModels(findUsers());
        }

        @Override
        public void fillContext(Object... params) {
            // No additional parameters
        }
    },
    USER_LOGIN("Log in a user", CREATED) {
        @Override
        public Object call() {
            return loginUser();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCredentials((Credentials) params[0]);
        }
    },
    USER_CONFIRM("Confirm user email & password", NO_CONTENT) {
        @Override
        public Object call() {
            return confirmUser();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setToken((String) params[0]);
            MyThreadLocal.get().setUpdatePassword((UpdatePassword) params[1]);
        }
    },
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
    CLUB_DELETE("Delete a club", NO_CONTENT) {
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
            return convertBeanToModel(updateClub());
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
            // No additional parameters
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
    AFFILIATION_UPDATE("Update an affiliation", OK) {
        @Override
        public Object call() {
            return convertBeanToModel(updateAffiliation());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeason((String) params[1]);
            MyThreadLocal.get().setCreateOrUpdateAffiliationContext(CreateOrUpdateAffiliationContext.create((AffiliationForUpdate) params[2]));
        }
    },
    AFFILIATION_DELETE("Delete an affiliation", NO_CONTENT) {
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
