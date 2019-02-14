package com.boschat.sikb.api.licence;

import com.boschat.sikb.AbstractTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadLicences;
import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.LICENCE_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;

@DisplayName(" Delete a licence ")
class LicenceDeleteTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        loadSeasons();
        loadClubs();
        loadPersons();
        loadLicences();
    }

    @Test
    @DisplayName(" exiting ")
    void existing() throws Exception {
        Response response = licenceDelete(V1, PERSON_DEFAULT_ID, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, LICENCE_DEFAULT_NUMBER);
        checkResponse(response, NO_CONTENT);
    }

    @Test
    @DisplayName(" unknown Licence ")
    void unknownLicence() throws Exception {
        Response response = licenceDelete(V1, PERSON_DEFAULT_ID, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, "UNKNOWN");
        checkResponse(response, LICENCE_NOT_FOUND, "UNKNOWN");
    }

}
