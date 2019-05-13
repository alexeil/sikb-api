package com.boschat.sikb.api.affiliation;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Affiliation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.PersistenceUtils.loadAffiliations;
import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;

@DisplayName(" get an affiliation ")
@ExtendWith(JerseyTestExtension.class)
class AffiliationGetTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        loadSeasons();
        loadClubs();
        loadAffiliations();
    }

    @Test
    @DisplayName(" (default) ")
    void defaultAffiliation() throws Exception {
        Response response = affiliationGet(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID);

        checkResponse(response, OK);
        Affiliation affiliation = getAffiliation(response);
        checkAffiliation(affiliation, AFFILIATION_DEFAULT_PREFECTURE_NUMBER, AFFILIATION_DEFAULT_PREFECTURE_CITY, AFFILIATION_DEFAULT_SIRET_NUMBER,
            AFFILIATION_DEFAULT_ADDRESS, AFFILIATION_DEFAULT_POSTAL_CODE, AFFILIATION_DEFAULT_CITY, AFFILIATION_DEFAULT_PHONE_NUMBER,
            AFFILIATION_DEFAULT_EMAIL, AFFILIATION_DEFAULT_WEBSITE, NOW, null, AFFILIATION_DEFAULT_PRESIDENT, AFFILIATION_DEFAULT_PRESIDENT_SEX,
            AFFILIATION_DEFAULT_SECRETARY, AFFILIATION_DEFAULT_SECRETARY_SEX, AFFILIATION_DEFAULT_TREASURER, AFFILIATION_DEFAULT_TREASURER_SEX,
            AFFILIATION_DEFAULT_MEMBERS_NUMBER, AFFILIATION_DEFAULT_ELECTED_DATE, AFFILIATION_DEFAULT_STATUS, AFFILIATION_DEFAULT_COMMENT);
    }

}
