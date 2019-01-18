package com.boschat.sikb.api;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Club;
import com.boschat.sikb.model.ClubForUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.api.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.api.ResponseCode.OK;

@DisplayName(" Update a club ")
class ClubUpdateTest extends AbstractTest {

    private static ClubForUpdate buildClubForUpdate(String name, String shortName, String logo) {
        ClubForUpdate clubForUpdate = new ClubForUpdate();
        if (name != null) {
            clubForUpdate.setName(name);
        }
        if (shortName != null) {
            clubForUpdate.setShortName(shortName);
        }
        if (logo != null) {
            clubForUpdate.setLogo(logo);
        }
        return clubForUpdate;
    }

    @BeforeEach
    void loadDataSuite() throws IOException {
        truncateData();
        loadDataSuite("sql/clubUpdate.csv");
    }

    @Test
    @DisplayName(" name ")
    void name() throws Exception {
        Response response = clubUpdate(V1, DEFAULT_CLUB_ID, buildClubForUpdate("New Club", null, null));

        checkResponse(response, OK);
        Club club = getClub(response);
        checkClub(club, "New Club", DEFAULT_CLUB_SHORT_NAME, DEFAULT_CLUB_LOGO);
    }

    @Test
    @DisplayName(" shortName ")
    void shortName() throws Exception {
        Response response = clubUpdate(V1, DEFAULT_CLUB_ID, buildClubForUpdate(null, "New shortName", null));

        checkResponse(response, OK);
        Club club = getClub(response);
        checkClub(club, DEFAULT_CLUB_NAME, "New shortName", DEFAULT_CLUB_LOGO);
    }

    @Test
    @DisplayName(" logo ")
    void logo() throws Exception {
        Response response = clubUpdate(V1, DEFAULT_CLUB_ID, buildClubForUpdate(null, null, "New Logo"));

        checkResponse(response, OK);
        Club club = getClub(response);
        checkClub(club, DEFAULT_CLUB_NAME, DEFAULT_CLUB_SHORT_NAME, "New Logo");
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws Exception {
        Response response = clubUpdate(V1, 999, buildClubForUpdate(null, null, "New Logo"));

        checkResponse(response, CLUB_NOT_FOUND, 999);
    }
}
