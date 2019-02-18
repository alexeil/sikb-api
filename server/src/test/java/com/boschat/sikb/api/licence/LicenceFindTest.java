package com.boschat.sikb.api.licence;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Licence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.util.List;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadLicences;
import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.PERSON_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.SEASON_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Find person's licences ")
@ExtendWith(JerseyTestExtension.class)
class LicenceFindTest extends AbstractTest {

    @BeforeAll
    static void loadDataSuite() throws Exception {
        loadSeasons();
        loadClubs();
        loadPersons();
        loadLicences();
    }

    @Test
    @DisplayName(" right case ")
    void rightCase() throws Exception {
        Response response = licenceFind(V1, PERSON_DEFAULT_ID, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID);

        checkResponse(response, OK);
        List<Licence> licences = getLicences(response);

        assertAll("Check licences ",
            () -> assertNotNull(licences, " licences shouldn't be null"),
            () -> assertEquals(1, licences.size(), " only 1 licences "),
            () -> checkLicence(licences.get(0), LICENCE_DEFAULT_TYPE_LICENCE, LICENCE_DEFAULT_FORMATION_NEED,
                CLUB_DEFAULT_ID, SEASON_DEFAULT_ID)
        );

    }

    @Test
    @DisplayName(" no licence ")
    void noLicence() throws Exception {
        Response response = licenceFind(V1, 2, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID);

        checkResponse(response, OK);
        List<Licence> licences = getLicences(response);

        assertAll("Check licences ",
            () -> assertNotNull(licences, " licences shouldn't be null"),
            () -> assertEquals(0, licences.size(), " only 1 licences ")
        );

    }

    @Test
    @DisplayName(" unknown person ")
    void unknownPerson() throws Exception {
        Response response = licenceFind(V1, 999, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID);
        checkResponse(response, PERSON_NOT_FOUND, 999);
    }

    @Test
    @DisplayName(" unknown club ")
    void unknownClub() throws Exception {
        Response response = licenceFind(V1, PERSON_DEFAULT_ID, 999, SEASON_DEFAULT_ID);
        checkResponse(response, CLUB_NOT_FOUND, 999);
    }

    @Test
    @DisplayName(" unknown season ")
    void unknownSeason() throws Exception {
        Response response = licenceFind(V1, PERSON_DEFAULT_ID, CLUB_DEFAULT_ID, "10001000");
        checkResponse(response, SEASON_NOT_FOUND, "10001000");
    }
}
