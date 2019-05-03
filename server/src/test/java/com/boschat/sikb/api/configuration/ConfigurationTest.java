package com.boschat.sikb.api.configuration;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.FormationType;
import com.boschat.sikb.model.LicenceType;
import com.boschat.sikb.model.ProfileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.util.List;

import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Check configuration : ")
@ExtendWith(JerseyTestExtension.class)
class ConfigurationTest extends AbstractTest {

    @Test
    @DisplayName(" formation types ")
    void findAllFormationTypes() throws Exception {
        Response response = formationTypesFind(V1);
        checkResponse(response, OK);
        List<FormationType> formationTypes = getFormationTypes(response);

        assertAll("Check formationTypes ",
            () -> assertNotNull(formationTypes, " formationTypes shouldn't be null"),
            () -> assertEquals(6, formationTypes.size(), " only 6 formationTypes "),
            () -> assertEquals(1, formationTypes.get(0).getId(), "Incorrect id"),
            () -> assertEquals("Arbitre Niveau 1", formationTypes.get(0).getName(), "Incorrect Name")
        );
    }

    @Test
    @DisplayName(" licence types ")
    void findAllLicenceTypes() throws Exception {
        Response response = licenceTypesFind(V1);
        checkResponse(response, OK);
        List<LicenceType> licenceTypes = getLicenceTypes(response);

        assertAll("Check licenceTypes ",
            () -> assertNotNull(licenceTypes, " licenceTypes shouldn't be null"),
            () -> assertEquals(6, licenceTypes.size(), " only 6 licenceTypes "),
            () -> assertEquals(1, licenceTypes.get(0).getId(), "Incorrect id"),
            () -> assertEquals("Sénior Compétition", licenceTypes.get(0).getName(), "Incorrect Name"),
            () -> assertEquals(true, licenceTypes.get(0).getMedicalCertificateRequired(), "Incorrect medical certificate required")
        );
    }

    @Test
    @DisplayName(" profile types ")
    void findAllProfileTypes() throws Exception {
        Response response = profileTypesFind(V1);
        checkResponse(response, OK);
        List<ProfileType> profileTypes = getProfileTypes(response);

        assertAll("Check profile Types ",
            () -> assertNotNull(profileTypes, " profileTypes shouldn't be null"),
            () -> assertEquals(4, profileTypes.size(), " only 4 profiles "),
            () -> assertEquals(1, profileTypes.get(0).getId(), "Incorrect id"),
            () -> assertEquals("Administrator", profileTypes.get(0).getName(), "Incorrect Name")
        );
    }
}
