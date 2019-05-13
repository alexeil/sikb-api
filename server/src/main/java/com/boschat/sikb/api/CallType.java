package com.boschat.sikb.api;

import com.boschat.sikb.common.configuration.ResponseCode;
import com.boschat.sikb.context.CreateOrUpdateAffiliationContext;
import com.boschat.sikb.context.CreateOrUpdateClubContext;
import com.boschat.sikb.context.CreateOrUpdateLicenceContext;
import com.boschat.sikb.context.CreateOrUpdatePersonContext;
import com.boschat.sikb.context.CreateOrUpdateSeasonContext;
import com.boschat.sikb.context.CreateOrUpdateTeamContext;
import com.boschat.sikb.context.CreateOrUpdateUserContext;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.AffiliationForUpdate;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;
import com.boschat.sikb.model.ConfirmPassword;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.Functionality;
import com.boschat.sikb.model.LicenceForCreation;
import com.boschat.sikb.model.LicenceForUpdate;
import com.boschat.sikb.model.PersonForCreation;
import com.boschat.sikb.model.PersonForUpdate;
import com.boschat.sikb.model.Reset;
import com.boschat.sikb.model.SeasonForCreation;
import com.boschat.sikb.model.SeasonForUpdate;
import com.boschat.sikb.model.TeamForCreation;
import com.boschat.sikb.model.TeamForUpdate;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.model.UserForCreation;
import com.boschat.sikb.model.UserForUpdate;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;

import static com.boschat.sikb.Helper.convertBeanToModel;
import static com.boschat.sikb.Helper.convertBeansToModels;
import static com.boschat.sikb.Helper.convertFormationTypesBeansToModels;
import static com.boschat.sikb.Helper.convertLicenceBeansToModels;
import static com.boschat.sikb.Helper.convertLicenceTypesBeansToModels;
import static com.boschat.sikb.Helper.convertPersonsBeansToModels;
import static com.boschat.sikb.Helper.convertProfileTypesBeansToModels;
import static com.boschat.sikb.Helper.convertSeasonsBeansToModels;
import static com.boschat.sikb.Helper.convertTeamBeansToModels;
import static com.boschat.sikb.Helper.convertUserBeansToModels;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.service.AffiliationUtils.createAffiliation;
import static com.boschat.sikb.service.AffiliationUtils.deleteAffiliation;
import static com.boschat.sikb.service.AffiliationUtils.findClubAffiliations;
import static com.boschat.sikb.service.AffiliationUtils.getAffiliation;
import static com.boschat.sikb.service.AffiliationUtils.updateAffiliation;
import static com.boschat.sikb.service.ClubUtils.createClub;
import static com.boschat.sikb.service.ClubUtils.createLogo;
import static com.boschat.sikb.service.ClubUtils.deleteClub;
import static com.boschat.sikb.service.ClubUtils.findClubs;
import static com.boschat.sikb.service.ClubUtils.getClub;
import static com.boschat.sikb.service.ClubUtils.updateClub;
import static com.boschat.sikb.service.ConfigurationUtils.findFormationTypes;
import static com.boschat.sikb.service.ConfigurationUtils.findLicenceTypes;
import static com.boschat.sikb.service.ConfigurationUtils.findProfileTypes;
import static com.boschat.sikb.service.LicenceUtils.createLicence;
import static com.boschat.sikb.service.LicenceUtils.deleteLicence;
import static com.boschat.sikb.service.LicenceUtils.findLicences;
import static com.boschat.sikb.service.LicenceUtils.updateLicence;
import static com.boschat.sikb.service.PersonUtils.createMedicalCertificate;
import static com.boschat.sikb.service.PersonUtils.createPerson;
import static com.boschat.sikb.service.PersonUtils.createPhoto;
import static com.boschat.sikb.service.PersonUtils.deletePerson;
import static com.boschat.sikb.service.PersonUtils.findPersons;
import static com.boschat.sikb.service.PersonUtils.getPerson;
import static com.boschat.sikb.service.PersonUtils.updatePerson;
import static com.boschat.sikb.service.SeasonUtils.createSeason;
import static com.boschat.sikb.service.SeasonUtils.deleteSeason;
import static com.boschat.sikb.service.SeasonUtils.findSeasons;
import static com.boschat.sikb.service.SeasonUtils.updateSeason;
import static com.boschat.sikb.service.TeamUtils.createTeam;
import static com.boschat.sikb.service.TeamUtils.deleteTeam;
import static com.boschat.sikb.service.TeamUtils.findTeamMembers;
import static com.boschat.sikb.service.TeamUtils.findTeams;
import static com.boschat.sikb.service.TeamUtils.getTeam;
import static com.boschat.sikb.service.TeamUtils.updateTeam;
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
    USER_CREATE("Create a user", CREATED, true, Functionality.USER_CREATE) {
        @Override
        public Object call() {
            return convertBeanToModel(createUser());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdateUserContext(CreateOrUpdateUserContext.create((UserForCreation) params[0]));
        }
    },
    USER_DELETE("Delete a user", NO_CONTENT, true, Functionality.USER_DELETE) {
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
    USER_UPDATE("Update a User", OK, true, Functionality.USER_UPDATE) {
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
    USER_GET("get a user", OK, true, Functionality.USER_READ) {
        @Override
        public Object call() {
            return convertBeanToModel(getUser());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setUserId((Integer) params[0]);
        }
    },
    USER_FIND("find all users", OK, true, Functionality.USER_READ) {
        @Override
        public Object call() {
            return convertUserBeansToModels(findUsers());
        }

        @Override
        public void fillContext(Object... params) {
            // No additional parameters
        }
    },
    USER_LOGIN("Log in a user", CREATED, false, null) {
        @Override
        public Object call() {
            return loginUser();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCredentials((Credentials) params[0]);
        }
    },
    USER_LOGOUT("Log out a user", NO_CONTENT, true, null) {
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
    USER_CONFIRM("Confirm user email & password", NO_CONTENT, false, null) {
        @Override
        public Object call() {
            return confirmUser();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setToken((String) params[0]);
            MyThreadLocal.get().setConfirmPassword((ConfirmPassword) params[1]);
        }
    },
    USER_UPDATE_PASSWORD("Update user password", NO_CONTENT, true, null) {
        @Override
        public Object call() {
            updateUserPassword();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setUpdatePassword((UpdatePassword) params[0]);
        }
    },
    USER_RESET("Ask to reset user password", NO_CONTENT, false, null) {
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
    CLUB_CREATE("Create a club", CREATED, true, Functionality.CLUB_CREATE) {
        @Override
        public Object call() {
            return convertBeanToModel(createClub());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdateClubContext(CreateOrUpdateClubContext.create((ClubForCreation) params[0]));
        }
    },
    CLUB_DELETE("Delete a club", NO_CONTENT, true, Functionality.CLUB_DELETE) {
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
    CLUB_UPDATE("Update a club", OK, true, Functionality.CLUB_UPDATE) {
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
    CLUB_GET("get a club", OK, true, Functionality.CLUB_READ) {
        @Override
        public Object call() {
            return convertBeanToModel(getClub());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
        }
    },
    CLUB_FIND("find all clubs", OK, true, Functionality.CLUB_READ) {
        @Override
        public Object call() {
            return convertBeansToModels(findClubs());
        }

        @Override
        public void fillContext(Object... params) {
            // No additional parameters
        }
    },
    AFFILIATION_CREATE("Create an affiliation", CREATED, true, Functionality.CLUB_CREATE) {
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
    AFFILIATION_FIND("Find all club's affiliations", OK, true, Functionality.CLUB_READ) {
        @Override
        public Object call() {
            return findClubAffiliations();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
        }
    },
    AFFILIATION_GET("Get an affiliation", OK, true, Functionality.CLUB_READ) {
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
    AFFILIATION_UPDATE("Update an affiliation", OK, true, Functionality.CLUB_UPDATE) {
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
    AFFILIATION_DELETE("Delete an affiliation", NO_CONTENT, true, Functionality.CLUB_DELETE) {
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
    PERSON_CREATE("Create a person", CREATED, true, Functionality.PERSON_CREATE) {
        @Override
        public Object call() {
            return convertBeanToModel(createPerson());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdatePersonContext(CreateOrUpdatePersonContext.create((PersonForCreation) params[0]));
        }
    },
    PERSON_UPDATE("Update a person", OK, true, Functionality.PERSON_UPDATE) {
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
    PERSON_GET("Get a person", OK, true, Functionality.PERSON_READ) {
        @Override
        public Object call() {
            return convertBeanToModel(getPerson());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
        }
    },
    PERSON_FIND("Find persons", OK, true, Functionality.PERSON_READ) {
        @Override
        public Object call() {
            return convertPersonsBeansToModels(findPersons());
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    PERSON_DELETE("Delete a person", NO_CONTENT, true, Functionality.PERSON_DELETE) {
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
    SEASON_CREATE("Create a season", CREATED, true, Functionality.SEASON_CREATE) {
        @Override
        public Object call() {
            return convertBeanToModel(createSeason());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setCreateOrUpdateSeasonContext(CreateOrUpdateSeasonContext.create((SeasonForCreation) params[0]));
        }
    },
    SEASON_UPDATE("Update a season", OK, true, Functionality.SEASON_UPDATE) {
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
    SEASON_FIND("Find Seasons", OK, true, Functionality.SEASON_READ) {
        @Override
        public Object call() {
            return convertSeasonsBeansToModels(findSeasons());
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    SEASON_DELETE("Delete a Season", NO_CONTENT, true, Functionality.SEASON_DELETE) {
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
    FORMATION_TYPE_FIND("Find formation types", OK, true, null) {
        @Override
        public Object call() {
            return convertFormationTypesBeansToModels(findFormationTypes());
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    PROFILE_TYPE_FIND("Find profile types", OK, true, null) {
        @Override
        public Object call() {
            return convertProfileTypesBeansToModels(findProfileTypes());
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    LICENCE_TYPE_FIND("Find licence types", OK, true, null) {
        @Override
        public Object call() {
            return convertLicenceTypesBeansToModels(findLicenceTypes());
        }

        @Override
        public void fillContext(Object... params) {
            // no params
        }
    },
    LICENCE_CREATE("Create a licence", CREATED, true, Functionality.PERSON_CREATE) {
        @Override
        public Object call() {
            return convertBeanToModel(createLicence());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
            MyThreadLocal.get().setClubId((Integer) params[1]);
            MyThreadLocal.get().setSeasonId((String) params[2]);
            MyThreadLocal.get().setCreateOrUpdateLicenceContext(CreateOrUpdateLicenceContext.create((LicenceForCreation) params[3]));
        }
    },
    LICENCE_FIND("Find person's licences", OK, true, Functionality.PERSON_READ) {
        @Override
        public Object call() {
            return convertLicenceBeansToModels(findLicences());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
            MyThreadLocal.get().setClubId((Integer) params[1]);
            MyThreadLocal.get().setSeasonId((String) params[2]);
        }
    },
    LICENCE_DELETE("Create a licence", NO_CONTENT, true, Functionality.PERSON_DELETE) {
        @Override
        public Object call() {
            deleteLicence();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
            MyThreadLocal.get().setClubId((Integer) params[1]);
            MyThreadLocal.get().setSeasonId((String) params[2]);
            MyThreadLocal.get().setLicenceId((String) params[3]);
        }
    },
    LICENCE_UPDATE("Update a licence", OK, true, Functionality.PERSON_UPDATE) {
        @Override
        public Object call() {
            return convertBeanToModel(updateLicence());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
            MyThreadLocal.get().setClubId((Integer) params[1]);
            MyThreadLocal.get().setSeasonId((String) params[2]);
            MyThreadLocal.get().setLicenceId((String) params[3]);
            MyThreadLocal.get().setCreateOrUpdateLicenceContext(CreateOrUpdateLicenceContext.create((LicenceForUpdate) params[4]));
        }
    },
    MEDICAL_CERTIFICATE_UPLOAD("Upload a person's medical certificate", OK, true, Functionality.PERSON_CREATE) {
        @Override
        public Object call() {
            return createMedicalCertificate();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
            MyThreadLocal.get().setCreateOrUpdatePersonContext(CreateOrUpdatePersonContext.create((InputStream) params[1], (FormDataContentDisposition)
                params[2], (String) params[3]));
        }
    },
    PHOTO_UPLOAD("Upload a person's photo", OK, true, Functionality.PERSON_CREATE) {
        @Override
        public Object call() {
            return createPhoto();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setPersonId((Integer) params[0]);
            MyThreadLocal.get().setCreateOrUpdatePersonContext(
                CreateOrUpdatePersonContext.create((InputStream) params[1], (FormDataContentDisposition) params[2]));
        }
    },
    LOGO_UPLOAD("Upload a club's logo", OK, true, Functionality.CLUB_CREATE) {
        @Override
        public Object call() {
            return createLogo();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setCreateOrUpdateClubContext(
                CreateOrUpdateClubContext.create((InputStream) params[1], (FormDataContentDisposition) params[2]));
        }
    },
    TEAM_CREATE("Create a team", CREATED, true, Functionality.CLUB_CREATE) {
        @Override
        public Object call() {
            return convertBeanToModel(createTeam());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
            MyThreadLocal.get().setCreateOrUpdateTeamContext(CreateOrUpdateTeamContext.create((TeamForCreation) params[2]));
        }
    },
    TEAM_FIND("Find club's teams", OK, true, Functionality.CLUB_READ) {
        @Override
        public Object call() {
            return convertTeamBeansToModels(findTeams());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
        }
    },
    TEAM_MEMBERS_FIND("Find team's members", OK, true, Functionality.CLUB_READ) {
        @Override
        public Object call() {
            return findTeamMembers();
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
            MyThreadLocal.get().setTeamId((Integer) params[2]);
        }
    },
    TEAM_GET("Get a Team", OK, true, Functionality.CLUB_READ) {
        @Override
        public Object call() {
            return convertBeanToModel(getTeam());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
            MyThreadLocal.get().setTeamId((Integer) params[2]);
        }
    },
    TEAM_DELETE("Delete a team", NO_CONTENT, true, Functionality.CLUB_DELETE) {
        @Override
        public Object call() {
            deleteTeam();
            return null;
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
            MyThreadLocal.get().setTeamId((Integer) params[2]);
        }
    },
    TEAM_UPDATE("Update a licence", OK, true, Functionality.CLUB_UPDATE) {
        @Override
        public Object call() {
            return convertBeanToModel(updateTeam());
        }

        @Override
        public void fillContext(Object... params) {
            MyThreadLocal.get().setClubId((Integer) params[0]);
            MyThreadLocal.get().setSeasonId((String) params[1]);
            MyThreadLocal.get().setTeamId((Integer) params[2]);
            MyThreadLocal.get().setCreateOrUpdateTeamContext(CreateOrUpdateTeamContext.create((TeamForUpdate) params[3]));
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

    private final Functionality functionality;

    CallType(String infoLogMessage, ResponseCode responseCode, boolean checkAccessToken, Functionality functionality) {
        this.infoLogMessage = infoLogMessage;
        this.responseCode = responseCode;
        this.checkAccessToken = checkAccessToken;
        this.functionality = functionality;
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

    public boolean isNotAuthorized() {
        return !MyThreadLocal.get().hasCurrentUserRight(functionality);
    }

}
