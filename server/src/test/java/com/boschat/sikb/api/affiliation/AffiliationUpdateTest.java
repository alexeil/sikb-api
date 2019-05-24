package com.boschat.sikb.api.affiliation;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Affiliation;
import com.boschat.sikb.model.AffiliationForUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.PersistenceUtils.loadAffiliations;
import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.AFFILIATION_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.TRANSITION_FORBIDDEN;
import static com.boschat.sikb.model.AffiliationStatus.SUBMITTED;
import static com.boschat.sikb.model.AffiliationStatus.TO_COMPLETE;
import static com.boschat.sikb.model.AffiliationStatus.VALIDATED;

@DisplayName(" Update an affiliation ")
@ExtendWith(JerseyTestExtension.class)
class AffiliationUpdateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        loadUsers();
        loadSeasons();
        loadClubs();
        loadAffiliations();
    }

    @Test
    @DisplayName(" only one field ")
    void defaultAffiliation() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        affiliationForUpdate.setPrefectureNumber("New Prefecture Number");
        Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, affiliationForUpdate);
        checkResponse(response, OK);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, "New Prefecture Number", AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
            AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
            AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, NOW, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
            AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
            AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE, AFFILIATION_DEFAULT_STATUS, AFFILIATION_DEFAULT_COMMENT);
    }

    @Test
    @DisplayName(" no field ")
    void noField() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, affiliationForUpdate);

        checkResponse(response, OK);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
            AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
            AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, NOW, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
            AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
            AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE, AFFILIATION_DEFAULT_STATUS, AFFILIATION_DEFAULT_COMMENT);
    }

    @Test
    @DisplayName(" affiliation not found ")
    void affiliationNotFound() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        affiliationForUpdate.setPrefectureNumber("New Prefecture Number");
        Response response = affiliationUpdate(V1, 999, SEASON_DEFAULT_ID, affiliationForUpdate);
        checkResponse(response, AFFILIATION_NOT_FOUND, 999, SEASON_DEFAULT_ID);
    }

    @Test
    @DisplayName(" season not found ")
    void seasonNotFound() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        affiliationForUpdate.setPrefectureNumber("New Prefecture Number");
        Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, "20002001", affiliationForUpdate);
        checkResponse(response, AFFILIATION_NOT_FOUND, CLUB_DEFAULT_ID, "20002001");
    }

    @Nested
    class transitions {

        @Test
        @DisplayName(" status TO_COMPLETE to SUBMITTED ")
        void submit() throws Exception {
            AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
            affiliationForUpdate.setStatus(SUBMITTED);
            Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, affiliationForUpdate);

            checkResponse(response, OK);
            Affiliation affiliation = getAffiliation(response);
            checkAffiliation(affiliation, AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
                AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
                AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, NOW, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
                AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
                AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE, SUBMITTED, null);
        }

        @Test
        @DisplayName(" status SUBMITTED to VALIDATED ")
        void validate() throws Exception {
            AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
            affiliationForUpdate.setStatus(VALIDATED);
            Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, "20162017", affiliationForUpdate);

            checkResponse(response, OK);
            Affiliation affiliation = getAffiliation(response);
            checkAffiliation(affiliation, AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
                AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
                AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, NOW, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
                AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
                AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE, VALIDATED, null);

        }

        @Test
        @DisplayName(" status SUBMITTED to TO_COMPLETE ")
        void reject() throws Exception {
            AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
            affiliationForUpdate.setStatus(TO_COMPLETE);
            affiliationForUpdate.setComment("Need banking information");
            Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, "20162017", affiliationForUpdate);

            checkResponse(response, OK);
            Affiliation affiliation = getAffiliation(response);
            checkAffiliation(affiliation, AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
                AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
                AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, NOW, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
                AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
                AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE, TO_COMPLETE, "Need banking information");
        }

        @Test
        @DisplayName(" status VALIDATED to TO_COMPLETE ")
        void incorrectStatusChangeFromValidated() throws Exception {
            AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
            affiliationForUpdate.setStatus(TO_COMPLETE);
            Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, "20172018", affiliationForUpdate);

            checkResponse(response, TRANSITION_FORBIDDEN, VALIDATED, TO_COMPLETE);
        }

        @Test
        @DisplayName(" status TO_COMPLETE to VALIDATED ")
        void forbiddenTransition() throws Exception {
            AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
            affiliationForUpdate.setStatus(VALIDATED);
            Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, affiliationForUpdate);

            checkResponse(response, TRANSITION_FORBIDDEN, TO_COMPLETE, VALIDATED);
        }
    }
}
