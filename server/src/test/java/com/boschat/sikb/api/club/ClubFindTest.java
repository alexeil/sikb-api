package com.boschat.sikb.api.club;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Club;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.api.ResponseCode.OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Find all clubs ")
class ClubFindTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        truncateData();
        loadClubs();
    }

    @Test
    @DisplayName(" (default) ")
    void getDefaultClub() throws Exception {
        Response response = clubFind(V1);

        checkResponse(response, OK);
        List<Club> clubs = getClubs(response);

        assertAll("Check clubs ",
                () -> assertNotNull(clubs, " clubs shouldn't be null"),
                () -> assertEquals(2, clubs.size(), " only 2 clubs "),
                () -> checkClub(clubs.get(0), DEFAULT_CLUB_NAME, DEFAULT_CLUB_SHORT_NAME, DEFAULT_CLUB_LOGO),
                () -> checkClub(clubs.get(1), "Nantes Atlantique Kin-ball Club", "NAKC",
                        "https://i1.wp.com/www.kin-ball.fr/wp-content/uploads/2016/11/NAKC-Nantes.jpg?resize=100%2C100&ssl=1")
        );
    }

    @Test
    @DisplayName(" no clubs ")
    void unknown() throws Exception {
        truncateData();
        Response response = clubFind(V1);

        checkResponse(response, OK);
        List<Club> clubs = getClubs(response);
        assertNotNull(clubs, " clubs shouldn't be null");
        assertEquals(0, clubs.size(), " only 2 clubs ");
    }
}
