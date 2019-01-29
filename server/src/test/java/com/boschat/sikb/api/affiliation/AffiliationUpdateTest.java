package com.boschat.sikb.api.affiliation;

import com.boschat.sikb.AbstractTest;
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
        truncateData();
        loadClubs();
        loadAffiliations();
    }

    @Test
    @DisplayName(" only one field ")
    void defaultAffiliation() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        affiliationForUpdate.setPrefectureNumber("New Prefecture Number");
        Response response = affiliationUpdate(V1, DEFAULT_CLUB_ID, DEFAULT_SEASON, affiliationForUpdate);

        checkResponse(response, OK);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, "New Prefecture Number", DEFAULT_AFFILIATION_PREFECTURE_CITY, DEFAULT_AFFILIATION_SIRET_NUMBER,
                DEFAULT_AFFILIATION_ADDRESS, DEFAULT_AFFILIATION_POSTAL_CODE, DEFAULT_AFFILIATION_CITY, DEFAULT_AFFILIATION_PHONE_NUMBER,
                DEFAULT_AFFILIATION_EMAIL, DEFAULT_AFFILIATION_WEBSITE, NOW, NOW, DEFAULT_AFFILIATION_PRESIDENT, DEFAULT_AFFILIATION_PRESIDENT_SEX,
                DEFAULT_AFFILIATION_SECRETARY, DEFAULT_AFFILIATION_SECRETARY_SEX, DEFAULT_AFFILIATION_TREASURER, DEFAULT_AFFILIATION_TREASURER_SEX,
                DEFAULT_AFFILIATION_MEMBERS_NUMBER, DEFAULT_AFFILIATION_ELECTED_DATE);
    }

    @Test
    @DisplayName(" club not found ")
    void clubNotFound() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        affiliationForUpdate.setPrefectureNumber("New Prefecture Number");
        Response response = affiliationUpdate(V1, 999, DEFAULT_SEASON, affiliationForUpdate);
        checkResponse(response, AFFILIATION_NOT_FOUND, 999, DEFAULT_SEASON);
    }

    @Test
    @DisplayName(" season not found ")
    void seasonNotFound() throws Exception {
        AffiliationForUpdate affiliationForUpdate = new AffiliationForUpdate();
        affiliationForUpdate.setPrefectureNumber("New Prefecture Number");
        Response response = affiliationUpdate(V1, DEFAULT_CLUB_ID, "20002001", affiliationForUpdate);
        checkResponse(response, AFFILIATION_NOT_FOUND, DEFAULT_CLUB_ID, "20002001");
    }

}
