package com.boschat.sikb.api.team;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.PersistenceUtils.loadTeams;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.common.configuration.ResponseCode.SEASON_NOT_FOUND;

@DisplayName(" Delete a team ")
@ExtendWith(JerseyTestExtension.class)
class TeamDeleteTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadClubs();
        loadSeasons();
        loadTeams();
    }

    @Test
    @DisplayName(" default team ")
    void name() throws Exception {
        Response response = teamDelete(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, TEAM_DEFAULT_ID);
        checkResponse(response, NO_CONTENT);
    }

    @Test
    @DisplayName(" club not found ")
    void clubNotFound() throws Exception {
        Response response = teamDelete(V1, 999, SEASON_DEFAULT_ID, TEAM_DEFAULT_ID);
        checkResponse(response, CLUB_NOT_FOUND, 999);
    }

    @Test
    @DisplayName(" season not found ")
    void seasonNotFound() throws Exception {
        Response response = teamDelete(V1, CLUB_DEFAULT_ID, "99999999", TEAM_DEFAULT_ID);
        checkResponse(response, SEASON_NOT_FOUND, "99999999");
    }

    @Test
    @DisplayName(" team not found ")
    void notFound() throws Exception {
        Response response = teamDelete(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, 999);
        checkResponse(response, NO_CONTENT);
    }
}
