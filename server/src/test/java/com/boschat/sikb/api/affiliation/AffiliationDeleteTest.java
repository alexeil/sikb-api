package com.boschat.sikb.api.affiliation;

import com.boschat.sikb.AbstractTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.PersistenceUtils.loadAffiliations;
import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.AFFILIATION_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;

@DisplayName(" Delete an affiliation ")
class AffiliationDeleteTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        loadSeasons();
        loadClubs();
        loadAffiliations();
    }

    @Test
    @DisplayName(" (default) ")
    void defaultAffiliation() throws Exception {
        Response response = affiliationDelete(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID);
        checkResponse(response, NO_CONTENT);
    }

    @Test
    @DisplayName(" club not found ")
    void clubNotFound() throws Exception {
        Response response = affiliationDelete(V1, 999, SEASON_DEFAULT_ID);
        checkResponse(response, AFFILIATION_NOT_FOUND, 999, SEASON_DEFAULT_ID);
    }

    @Test
    @DisplayName(" season not found ")
    void seasonNotFound() throws Exception {
        Response response = affiliationDelete(V1, CLUB_DEFAULT_ID, "20002001");
        checkResponse(response, AFFILIATION_NOT_FOUND, CLUB_DEFAULT_ID, "20002001");
    }

}
