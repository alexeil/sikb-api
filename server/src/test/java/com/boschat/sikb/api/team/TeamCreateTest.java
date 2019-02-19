package com.boschat.sikb.api.team;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Team;
import com.boschat.sikb.model.TeamForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.MISSING_BODY_FIELD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_NAME;

@DisplayName(" Create a team ")
@ExtendWith(JerseyTestExtension.class)
class TeamCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadClubs();
        loadSeasons();
    }

    @Test
    @DisplayName(" with only a name")
    void withAName() throws Exception {
        TeamForCreation teamForCreation = new TeamForCreation();
        teamForCreation.setName(TEAM_DEFAULT_NAME);
        Response response = teamCreate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, teamForCreation);

        checkResponse(response, CREATED);
        Team team = getTeam(response);
        checkTeam(team, TEAM_DEFAULT_NAME);
    }

    @Test
    @DisplayName(" with all information ")
    void withAllInformation() throws Exception {
        TeamForCreation teamForCreation = new TeamForCreation();
        teamForCreation.setName(TEAM_DEFAULT_NAME);
        teamForCreation.setMembers(TEAM_DEFAULT_MEMBERS);

        Response response = teamCreate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, teamForCreation);

        checkResponse(response, CREATED);
        Team team = getTeam(response);
        checkTeam(team, TEAM_DEFAULT_NAME);
    }

    @Test
    @DisplayName(" with missing field")
    void missingField() throws Exception {
        TeamForCreation teamForCreation = new TeamForCreation();
        Response response = teamCreate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, teamForCreation);
        checkResponse(response, MISSING_BODY_FIELD, BODY_FIELD_NAME);
    }

}
