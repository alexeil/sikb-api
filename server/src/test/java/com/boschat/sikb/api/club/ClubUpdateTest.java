package com.boschat.sikb.api.club;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Club;
import com.boschat.sikb.model.ClubForUpdate;
import com.boschat.sikb.model.Logo;
import com.boschat.sikb.model.LogoForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.FILE_EXTENSION_NOT_AUTHORIZED;
import static com.boschat.sikb.common.configuration.ResponseCode.FILE_EXTENSION_NOT_SUPPORTED;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;

@DisplayName(" Update a club ")
@ExtendWith(JerseyTestExtension.class)
class ClubUpdateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadClubs();
    }

    private static ClubForUpdate buildClubForUpdate(String name, String shortName, String logo) {
        ClubForUpdate clubForUpdate = new ClubForUpdate();
        if (name != null) {
            clubForUpdate.setName(name);
        }
        if (shortName != null) {
            clubForUpdate.setShortName(shortName);
        }
        return clubForUpdate;
    }

    @Test
    @DisplayName(" name ")
    void name() throws Exception {
        Response response = clubUpdate(V1, CLUB_DEFAULT_ID, buildClubForUpdate("New Club", null, null));

        checkResponse(response, OK);
        Club club = getClub(response);
        checkClub(club, "New Club", CLUB_DEFAULT_SHORT_NAME, true);
    }

    @Test
    @DisplayName(" shortName ")
    void shortName() throws Exception {
        Response response = clubUpdate(V1, CLUB_DEFAULT_ID, buildClubForUpdate(null, "New shortName", null));

        checkResponse(response, OK);
        Club club = getClub(response);
        checkClub(club, CLUB_DEFAULT_NAME, "New shortName", true);
    }

    @Test
    @DisplayName(" logo ")
    void logo() throws Exception {
        Response response = clubUpdate(V1, CLUB_DEFAULT_ID, buildClubForUpdate(null, null, "New Logo"));

        checkResponse(response, OK);
        Club club = getClub(response);
        checkClub(club, CLUB_DEFAULT_NAME, CLUB_DEFAULT_SHORT_NAME, true);
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws Exception {
        Response response = clubUpdate(V1, 999, buildClubForUpdate(null, null, "New Logo"));

        checkResponse(response, CLUB_NOT_FOUND, 999);
    }

    @Nested
    @DisplayName(" logo ")
    class LogoTests {

        @Test
        @DisplayName(" right case")
        void logo() throws Exception {
            LogoForCreation logoForCreation = new LogoForCreation();
            logoForCreation.setLogoFileName(new File(PATH_DOCUMENT_CERTIFICATE));
            Response response = logoUpload(V1, CLUB_DEFAULT_ID, logoForCreation);

            checkResponse(response, OK);
            Logo logo = getLogo(response);
            checkLogo(logo, true);
        }

        @Test
        @DisplayName(" File extension not supported")
        void fileExtensionNotSupported() throws Exception {
            LogoForCreation logoForCreation = new LogoForCreation();
            logoForCreation.setLogoFileName(new File("src/test/resources/documents/certificate.toto"));

            Response response = logoUpload(V1, CLUB_DEFAULT_ID, logoForCreation);
            checkResponse(response, FILE_EXTENSION_NOT_SUPPORTED, "certificate.toto");
        }

        @Test
        @DisplayName(" File extension not authorized")
        void fileExtensionNotAuthorized() throws Exception {
            LogoForCreation logoForCreation = new LogoForCreation();
            logoForCreation.setLogoFileName(new File("src/test/resources/documents/certificate.txt"));

            Response response = logoUpload(V1, CLUB_DEFAULT_ID, logoForCreation);
            checkResponse(response, FILE_EXTENSION_NOT_AUTHORIZED, "text/plain", "certificate.txt");
        }
    }
}
