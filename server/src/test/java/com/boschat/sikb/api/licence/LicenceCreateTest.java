package com.boschat.sikb.api.licence;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Licence;
import com.boschat.sikb.model.LicenceForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.PERSON_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.SEASON_NOT_FOUND;

@DisplayName(" Create a licence ")
class LicenceCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        loadSeasons();
        loadClubs();
        loadPersons();
    }

    @Test
    @DisplayName(" with everything ")
    void withEverything() throws Exception {
        LicenceForCreation licenceForCreation = new LicenceForCreation();
        licenceForCreation.setTypeLicences(LICENCE_DEFAULT_TYPE_LICENCE_ID);
        licenceForCreation.setFormationNeed(LICENCE_DEFAULT_FORMATION_NEED_ID);

        Response response = licenceCreate(V1, PERSON_DEFAULT_ID, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, licenceForCreation);

        checkResponse(response, CREATED);
        Licence licence = getLicence(response);
        checkLicence(licence, LICENCE_DEFAULT_TYPE_LICENCE, LICENCE_DEFAULT_MEDICAL_CERTIFICATE, LICENCE_DEFAULT_FORMATION_NEED,
            CLUB_DEFAULT_ID, SEASON_DEFAULT_ID);
    }

    @Test
    @DisplayName(" unknown person ")
    void unknownPerson() throws Exception {
        LicenceForCreation licenceForCreation = new LicenceForCreation();
        Response response = licenceCreate(V1, 999, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, licenceForCreation);
        checkResponse(response, PERSON_NOT_FOUND, 999);
    }

    @Test
    @DisplayName(" unknown club ")
    void unknownClub() throws Exception {
        LicenceForCreation licenceForCreation = new LicenceForCreation();
        Response response = licenceCreate(V1, PERSON_DEFAULT_ID, 999, SEASON_DEFAULT_ID, licenceForCreation);
        checkResponse(response, CLUB_NOT_FOUND, 999);
    }

    @Test
    @DisplayName(" unknown season ")
    void unknownSeason() throws Exception {
        LicenceForCreation licenceForCreation = new LicenceForCreation();
        Response response = licenceCreate(V1, PERSON_DEFAULT_ID, CLUB_DEFAULT_ID, "10001000", licenceForCreation);
        checkResponse(response, SEASON_NOT_FOUND, "10001000");
    }

}
