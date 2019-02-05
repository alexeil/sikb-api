package com.boschat.sikb.api;

import com.boschat.sikb.common.configuration.ResponseCode;
import com.boschat.sikb.context.CreateOrUpdateAffiliationContext;
import com.boschat.sikb.context.CreateOrUpdateClubContext;
import com.boschat.sikb.context.CreateOrUpdatePersonContext;
import com.boschat.sikb.context.CreateOrUpdateSeasonContext;
import com.boschat.sikb.context.CreateOrUpdateUserContext;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.AffiliationForUpdate;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.LicenceForCreation;
import com.boschat.sikb.model.PersonForCreation;
import com.boschat.sikb.model.PersonForUpdate;
import com.boschat.sikb.model.Reset;
import com.boschat.sikb.model.SeasonForCreation;
import com.boschat.sikb.model.SeasonForUpdate;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.model.UserForCreation;
import com.boschat.sikb.model.UserForUpdate;

import static com.boschat.sikb.Helper.convertBeanToModel;
import static com.boschat.sikb.Helper.convertBeansToModels;
import static com.boschat.sikb.Helper.convertFormationTypesBeansToModels;
import static com.boschat.sikb.Helper.convertLicenceTypesBeansToModels;
import static com.boschat.sikb.Helper.convertPersonsBeansToModels;
import static com.boschat.sikb.Helper.convertSeasonsBeansToModels;
import static com.boschat.sikb.Helper.convertUserBeansToModels;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.service.AffiliationUtils.createAffiliation;
import static com.boschat.sikb.service.AffiliationUtils.deleteAffiliation;
import static com.boschat.sikb.service.AffiliationUtils.getAffiliation;
import static com.boschat.sikb.service.AffiliationUtils.updateAffiliation;
import static com.boschat.sikb.service.ClubUtils.createClub;
import static com.boschat.sikb.service.ClubUtils.deleteClub;
import static com.boschat.sikb.service.ClubUtils.findClubs;
import static com.boschat.sikb.service.ClubUtils.getClub;
import static com.boschat.sikb.service.ClubUtils.updateClub;
import static com.boschat.sikb.service.ConfigurationUtils.findFormationTypes;
import static com.boschat.sikb.service.ConfigurationUtils.findLicenceTypes;
import static com.boschat.sikb.service.LicenceUtils.createLicence;
import static com.boschat.sikb.service.PersonUtils.createPerson;
import static com.boschat.sikb.service.PersonUtils.deletePerson;
import static com.boschat.sikb.service.PersonUtils.findPersons;
import static com.boschat.sikb.service.PersonUtils.getPerson;
import static com.boschat.sikb.service.PersonUtils.updatePerson;
import static com.boschat.sikb.service.SeasonUtils.createSeason;
import static com.boschat.sikb.service.SeasonUtils.deleteSeason;
import static com.boschat.sikb.service.SeasonUtils.findSeasons;
import static com.boschat.sikb.service.SeasonUtils.updateSeason;
import static com.boschat.sikb.service.UserUtils.confirmUser;
import static com.boschat.sikb.service.UserUtils.createUser;
import static com.boschat.sikb.service.UserUtils.deleteUser;
import static com.boschat.sikb.service.UserUtils.findUsers;
import static com.boschat.sikb.service.UserUtils.getUser;
import static com.boschat.sikb.service.UserUtils.loginUser;
import static com.boschat.sikb.service.UserUtils.logoutUser;
import static com.boschat.sikb.service.UserUtils.resetUserPassword;
import static com.boschat.sikb.service.UserUtils.updateUser;
import static com.boschat.sikb.service.UserUtils.updateUserPassword;

public enum CallType {
    USER_CREATE("Create a user", CREATED, true) {
        @Override
        public Object call() {
            return convertBeanToModel(createUser());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdateUserContext(CreateOrUpdateUserContext.create((UserForCreation) params[0]));
        }
    },
    USER_DELETE("Delete a user", NO_CONTENT, true) {
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
    USER_UPDATE("Update a User", OK, true) {
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
    USER_GET("get a user", OK, true) {
        @Override
        public Object call() {
            return convertBeanToModel(getUser());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setUserId((Integer) params[0]);
        }
    },
    USER_FIND("find all users", OK, true) {
        @Override
        public Object call() {
            return convertUserBeansToModels(findUsers());
        }

        @Override
        public void fillContext(Object... params) {
            // No additional parameters
        }
    },
    USER_LOGIN("Log in a user", CREATED, false) {
        @Override
        public Object call() {
            return loginUser();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCredentials((Credentials) params[0]);
        }
    },
    USER_LOGOUT("Log out a user", NO_CONTENT, true) {
        @Override
        public Object call() {
            logoutUser();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    USER_CONFIRM("Confirm user email & password", NO_CONTENT, false) {
        @Override
        public Object call() {
            return confirmUser();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setToken((String) params[0]);
            MyThreadLocal.get().setUpdatePassword((UpdatePassword) params[1], false);
        }
    },
    USER_UPDATE_PASSWORD("Update user password", NO_CONTENT, true) {
        @Override
        public Object call() {
            updateUserPassword();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setUpdatePassword((UpdatePassword) params[0], true);
        }
    },
    USER_RESET("Ask to reset user password", NO_CONTENT, false) {
        @Override
        public Object call() {
            resetUserPassword();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setReset((Reset) params[0]);
        }
    },
    CLUB_CREATE("Create a club", CREATED, true) {
        @Override
        public Object call() {
            return convertBeanToModel(createClub());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdateClubContext(CreateOrUpdateClubContext.create((ClubForCreation) params[0]));
        }
    },
    CLUB_DELETE("Delete a club", NO_CONTENT, true) {
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
    CLUB_UPDATE("Update a club", OK, true) {
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
    CLUB_GET("get a club", OK, true) {
        @Override
        public Object call() {
            return convertBeanToModel(getClub());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
        }
    },
    CLUB_FIND("find all clubs", OK, true) {
        @Override
        public Object call() {
            return convertBeansToModels(findClubs());
        }

        @Override
        public void fillContext(Object... params) {
            // No additional parameters
        }
    },
    AFFILIATION_CREATE("Create an affiliation", CREATED, true) {
        @Override
        public Object call() {
            return convertBeanToModel(createAffiliation());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
            MyThreadLocal.get().setCreateOrUpdateAffiliationContext(CreateOrUpdateAffiliationContext.create((AffiliationForCreation) params[2]));
        }
    },
    AFFILIATION_GET("Get an affiliation", OK, true) {
        @Override
        public Object call() {
            return convertBeanToModel(getAffiliation());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
        }
    },
    AFFILIATION_UPDATE("Update an affiliation", OK, true) {
        @Override
        public Object call() {
            return convertBeanToModel(updateAffiliation());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
            MyThreadLocal.get().setCreateOrUpdateAffiliationContext(CreateOrUpdateAffiliationContext.create((AffiliationForUpdate) params[2]));
        }
    },
    AFFILIATION_DELETE("Delete an affiliation", NO_CONTENT, true) {
        @Override
        public Object call() {
            deleteAffiliation();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
        }
    },
    PERSON_CREATE("Create a person", CREATED, true) {
        @Override
        public Object call() {
            return convertBeanToModel(createPerson());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdatePersonContext(CreateOrUpdatePersonContext.create((PersonForCreation) params[0]));
        }
    },
    PERSON_UPDATE("Update a person", OK, true) {
        @Override
        public Object call() {
            return convertBeanToModel(updatePerson());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
            MyThreadLocal.get().setCreateOrUpdatePersonContext(CreateOrUpdatePersonContext.create((PersonForUpdate) params[1]));
        }
    },
    PERSON_GET("Get a person", OK, true) {
        @Override
        public Object call() {
            return convertBeanToModel(getPerson());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
        }
    },
    PERSON_FIND("Find persons", OK, true) {
        @Override
        public Object call() {
            return convertPersonsBeansToModels(findPersons());
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    PERSON_DELETE("Delete a person", NO_CONTENT, true) {
        @Override
        public Object call() {
            deletePerson();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
        }
    },
    SEASON_CREATE("Create a season", CREATED, true) {
        @Override
        public Object call() {
            return convertBeanToModel(createSeason());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdateSeasonContext(CreateOrUpdateSeasonContext.create((SeasonForCreation) params[0]));
        }
    },
    SEASON_UPDATE("Update a season", OK, true) {
        @Override
        public Object call() {
            return convertBeanToModel(updateSeason());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setSeasonId((String) params[0]);
            MyThreadLocal.get().setCreateOrUpdateSeasonContext(CreateOrUpdateSeasonContext.create((SeasonForUpdate) params[1]));
        }
    },
    SEASON_FIND("Find Seasons", OK, true) {
        @Override
        public Object call() {
            return convertSeasonsBeansToModels(findSeasons());
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    SEASON_DELETE("Delete a Season", NO_CONTENT, true) {
        @Override
        public Object call() {
            deleteSeason();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setSeasonId((String) params[0]);
        }
    },
    FORMATION_TYPE_FIND("Find formation types", OK, true) {
        @Override
        public Object call() {
            return convertFormationTypesBeansToModels(findFormationTypes());
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    LICENCE_TYPE_FIND("Find licence types", OK, true) {
        @Override
        public Object call() {
            return convertLicenceTypesBeansToModels(findLicenceTypes());
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    LICENCE_CREATE("Create a licence", CREATED, true) {
        @Override
        public Object call() {
            return convertBeanToModel(createLicence());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
            MyThreadLocal.get().setClubId((Integer) params[1]);
            MyThreadLocal.get().setSeasonId((String) params[2]);
            MyThreadLocal.get().setLicenceForCreation((LicenceForCreation) params[3]);
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

    private final boolean checkAccessToken;

    CallType(String infoLogMessage, ResponseCode responseCode, boolean checkAccessToken) {
        this.infoLogMessage = infoLogMessage;
        this.responseCode = responseCode;
        this.checkAccessToken = checkAccessToken;
    }

    public abstract Object call();

    public abstract void fillContext(Object... params);

    public String getInfoLogMessage() {
        return infoLogMessage;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public boolean isCheckAccessToken() {
        return checkAccessToken;
    }
}
