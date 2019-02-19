package com.boschat.sikb.api.team;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.PersistenceUtils.loadTeams;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Find all clubs's teams ")
@ExtendWith(JerseyTestExtension.class)
class TeamFindTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadClubs();
        loadSeasons();
        loadTeams();
    }

    @Test
    @DisplayName(" (default) ")
    void getDefaultClub() throws Exception {
        Response response = teamFind(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID);

        checkResponse(response, OK);
        List<Team> teams = getTeams(response);

        assertAll("Check teams ",
            () -> assertNotNull(teams, " teams shouldn't be null"),
            () -> assertEquals(2, teams.size(), " only 2 teams "),
            () -> checkTeam(teams.get(0), TEAM_DEFAULT_NAME),
            () -> checkTeam(teams.get(1), "Rennes 2 M")
        );
    }

    @Test
    @DisplayName(" no teams ")
    void noTeams() throws Exception {
        Response response = teamFind(V1, CLUB_DEFAULT_ID, "20172018");

        checkResponse(response, OK);
        List<Team> teams = getTeams(response);
        assertNotNull(teams, " teams shouldn't be null");
        assertEquals(0, teams.size(), " no team");
    }
}
