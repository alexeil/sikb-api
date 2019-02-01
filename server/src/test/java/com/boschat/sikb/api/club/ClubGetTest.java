package com.boschat.sikb.api.club;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.PersistenceUtils;
import com.boschat.sikb.model.Club;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;

@DisplayName(" get a club ")
class ClubGetTest extends AbstractTest {

    @BeforeAll
    static void loadDataSuite() throws IOException {
        PersistenceUtils.truncateData();
        PersistenceUtils.loadClubs();
    }

    @Test
    @DisplayName(" (default) ")
    void getDefaultClub() throws Exception {
        Response response = clubGet(V1, CLUB_DEFAULT_ID);

        checkResponse(response, OK);
        Club club = getClub(response);
        checkClub(club, CLUB_DEFAULT_NAME, CLUB_DEFAULT_SHORT_NAME, CLUB_DEFAULT_LOGO);
    }

    @Test
    @DisplayName(" unknown ")
    void unknown() throws Exception {
        Response response = clubGet(V1, 999);

        checkResponse(response, CLUB_NOT_FOUND, 999);
    }
}
