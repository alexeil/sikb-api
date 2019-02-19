package com.boschat.sikb.api.team;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Team;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.PersistenceUtils.loadTeams;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.TEAM_NOT_FOUND;

@DisplayName(" get a team ")
@ExtendWith(JerseyTestExtension.class)
class TeamGetTest extends AbstractTest {

    @BeforeAll
    static void loadDataSuite() throws IOException {
        loadClubs();
        loadSeasons();
        loadTeams();
    }

    @Test
    @DisplayName(" (default) ")
    void getDefaultClub() throws Exception {
        Response response = teamGet(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, TEAM_DEFAULT_ID);

        checkResponse(response, OK);
        Team team = getTeam(response);
        checkTeam(team, TEAM_DEFAULT_NAME);
    }

    @Test
    @DisplayName(" unknown ")
    void unknown() throws Exception {
        Response response = teamGet(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, 999);

        checkResponse(response, TEAM_NOT_FOUND, 999);
    }
}
