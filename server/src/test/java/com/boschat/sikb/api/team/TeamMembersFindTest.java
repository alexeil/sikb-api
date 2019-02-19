package com.boschat.sikb.api.team;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.TeamMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.PersistenceUtils.loadTeams;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.TEAM_NOT_FOUND;
import static com.boschat.sikb.model.MemberType.PLAYER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Find all team's members ")
@ExtendWith(JerseyTestExtension.class)
class TeamMembersFindTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadClubs();
        loadSeasons();
        loadTeams();
        loadPersons();
    }

    @Test
    @DisplayName(" (default) ")
    void getDefault() throws Exception {
        Response response = teamMembersFind(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, TEAM_DEFAULT_ID);

        checkResponse(response, OK);
        List<TeamMember> teamMembers = getTeamMembers(response);

        assertAll("Check teamMembers ",
            () -> assertNotNull(teamMembers, " teams shouldn't be null"),
            () -> assertEquals(2, teamMembers.size(), " only 2 teams "),
            () -> checkPerson(teamMembers.get(0).getMember(), PERSON_DEFAULT_FIRST_NAME, PERSON_DEFAULT_NAME, PERSON_DEFAULT_SEX, PERSON_DEFAULT_BIRTH_DATE,
                PERSON_DEFAULT_ADDRESS, PERSON_DEFAULT_POSTAL_CODE, PERSON_DEFAULT_CITY, PERSON_DEFAULT_PHONE_NUMBER, PERSON_DEFAULT_EMAIL,
                PERSON_DEFAULT_NATIONALITY, PERSON_DEFAULT_FORMATIONS, PERSON_MEDICAL_CERTIFICATE_VALIDITY, true),
            () -> assertEquals(PLAYER, teamMembers.get(0).getType(), " Type incorrect ")
        );
    }

    @Test
    @DisplayName(" no members teams ")
    void noTeams() throws Exception {
        Response response = teamMembersFind(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, 2);

        checkResponse(response, OK);
        List<TeamMember> teamMembers = getTeamMembers(response);

        assertAll("Check teamMembers ",
            () -> assertNotNull(teamMembers, " teams shouldn't be null"),
            () -> assertEquals(0, teamMembers.size(), " no team members ")
        );
    }

    @Test
    @DisplayName(" unknown team ")
    void unknownTeam() throws Exception {
        Response response = teamMembersFind(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID, 999);

        checkResponse(response, TEAM_NOT_FOUND, 999);
    }
}
