package com.boschat.sikb.api.affiliation;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Affiliation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;

@DisplayName(" get an affiliation ")
class AffiliationGetTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        truncateData();
        loadClubs();
        loadAffiliations();
    }

    @Test
    @DisplayName(" (default) ")
    void defaultAffiliation() throws Exception {
        Response response = affiliationGet(V1, DEFAULT_CLUB_ID, DEFAULT_SEASON);

        checkResponse(response, OK);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, DEFAULT_AFFILIATION_PREFECTURE_NUMBER, DEFAULT_AFFILIATION_PREFECTURE_CITY, DEFAULT_AFFILIATION_SIRET_NUMBER,
            DEFAULT_AFFILIATION_ADDRESS, DEFAULT_AFFILIATION_POSTAL_CODE, DEFAULT_AFFILIATION_CITY, DEFAULT_AFFILIATION_PHONE_NUMBER,
            DEFAULT_AFFILIATION_EMAIL, DEFAULT_AFFILIATION_WEBSITE, NOW, null, DEFAULT_AFFILIATION_PRESIDENT, DEFAULT_AFFILIATION_PRESIDENT_SEX,
            DEFAULT_AFFILIATION_SECRETARY, DEFAULT_AFFILIATION_SECRETARY_SEX, DEFAULT_AFFILIATION_TREASURER, DEFAULT_AFFILIATION_TREASURER_SEX,
            DEFAULT_AFFILIATION_MEMBERS_NUMBER, DEFAULT_AFFILIATION_ELECTED_DATE);
    }

}
