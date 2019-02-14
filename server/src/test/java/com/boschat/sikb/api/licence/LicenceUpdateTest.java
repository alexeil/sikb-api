package com.boschat.sikb.api.licence;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Licence;
import com.boschat.sikb.model.LicenceForUpdate;
import com.boschat.sikb.model.LicenceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadLicences;
import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.LICENCE_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;

@DisplayName(" Create a licence ")
class LicenceUpdateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        loadSeasons();
        loadClubs();
        loadPersons();
        loadLicences();
    }

    @Test
    @DisplayName(" with everything ")
    void withEverything() throws Exception {
        LicenceForUpdate licenceForUpdate = new LicenceForUpdate();
        licenceForUpdate.setTypeLicences(Collections.singletonList(1));
        licenceForUpdate.setFormationNeed(new ArrayList<>());

        Response response = licenceUpdate(V1, PERSON_DEFAULT_ID, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, LICENCE_DEFAULT_NUMBER, licenceForUpdate);

        checkResponse(response, OK);
        Licence licence = getLicence(response);
        checkLicence(licence, Collections.singletonList(new LicenceType().id(1).name("Sénior Compétition").medicalCertificateRequired(true)),
            null, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID);
    }

    @Test
    @DisplayName(" unknown licence ")
    void unknownSeason() throws Exception {
        LicenceForUpdate licenceForUpdate = new LicenceForUpdate();
        Response response = licenceUpdate(V1, PERSON_DEFAULT_ID, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, "UNKNOWN", licenceForUpdate);
        checkResponse(response, LICENCE_NOT_FOUND, "UNKNOWN");
    }
}
