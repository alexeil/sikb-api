package com.boschat.sikb.api.team;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Team;
import com.boschat.sikb.model.TeamForUpdate;
import com.boschat.sikb.model.TeamMemberForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.PersistenceUtils.loadTeams;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.TEAM_NOT_FOUND;
import static com.boschat.sikb.model.MemberType.ASSISTANT;

@DisplayName(" Update a team ")
@ExtendWith(JerseyTestExtension.class)
class TeamUpdateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadClubs();
        loadSeasons();
        loadTeams();
    }

    @Test
    @DisplayName(" name ")
    void name() throws Exception {
        TeamForUpdate teamForUpdate = new TeamForUpdate();
        teamForUpdate.setName("New Name");
        Response response = teamUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, TEAM_DEFAULT_ID, teamForUpdate);

        checkResponse(response, OK);
        Team team = getTeam(response);
        checkTeam(team, "New Name");
    }

    @Test
    @DisplayName(" members ")
    void members() throws Exception {
        TeamForUpdate teamForUpdate = new TeamForUpdate();
        teamForUpdate.setMembers(Collections.singletonList(new TeamMemberForCreation().id(1).type(ASSISTANT)));
        Response response = teamUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, TEAM_DEFAULT_ID, teamForUpdate);

        checkResponse(response, OK);
        Team team = getTeam(response);
        checkTeam(team, TEAM_DEFAULT_NAME);
    }

    @Test
    @DisplayName(" clear members ")
    void cleartMembers() throws Exception {
        TeamForUpdate teamForUpdate = new TeamForUpdate();
        teamForUpdate.setMembers(new ArrayList<>());
        Response response = teamUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, TEAM_DEFAULT_ID, teamForUpdate);

        checkResponse(response, OK);
        Team team = getTeam(response);
        checkTeam(team, TEAM_DEFAULT_NAME);
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws Exception {
        TeamForUpdate teamForUpdate = new TeamForUpdate();
        Response response = teamUpdate(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, 999, teamForUpdate);

        checkResponse(response, TEAM_NOT_FOUND, 999);
    }
}
