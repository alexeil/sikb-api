package com.boschat.sikb.api.affiliation;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.PersistenceUtils;
import com.boschat.sikb.model.Affiliation;
import com.boschat.sikb.model.AffiliationForUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.AFFILIATION_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;

@DisplayName(" Update an affiliation ")
class AffiliationUpdateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        PersistenceUtils.truncateData();
        PersistenceUtils.loadClubs();
        PersistenceUtils.loadAffiliations();
    }

    @Test
    @DisplayName(" only one field ")
    void defaultAffiliation() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        affiliationForUpdate.setPrefectureNumber("New Prefecture Number");
        Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_SHORT_NAME, affiliationForUpdate);

        checkResponse(response, OK);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, "New Prefecture Number", AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
            AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
            AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, NOW, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
            AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
            AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE);
    }

    @Test
    @DisplayName(" no field ")
    void noField() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_SHORT_NAME, affiliationForUpdate);

        checkResponse(response, OK);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
            AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
            AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, NOW, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
            AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
            AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE);
    }

    @Test
    @DisplayName(" affiliation not found ")
    void affiliationNotFound() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        affiliationForUpdate.setPrefectureNumber("New Prefecture Number");
        Response response = affiliationUpdate(V1, 999, SEASON_DEFAULT_SHORT_NAME, affiliationForUpdate);
        checkResponse(response, AFFILIATION_NOT_FOUND, 999, SEASON_DEFAULT_SHORT_NAME);
    }

    @Test
    @DisplayName(" season not found ")
    void seasonNotFound() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        affiliationForUpdate.setPrefectureNumber("New Prefecture Number");
        Response response = affiliationUpdate(V1, CLUB_DEFAULT_ID, "20002001", affiliationForUpdate);
        checkResponse(response, AFFILIATION_NOT_FOUND, CLUB_DEFAULT_ID, "20002001");
    }
}
