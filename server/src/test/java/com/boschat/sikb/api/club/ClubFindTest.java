package com.boschat.sikb.api.club;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Club;
import com.boschat.sikb.persistence.dao.DAOFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Find all clubs ")
class ClubFindTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
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
            () -> checkClub(clubs.get(0), CLUB_DEFAULT_NAME, CLUB_DEFAULT_SHORT_NAME, CLUB_DEFAULT_LOGO),
            () -> checkClub(clubs.get(1), "Nantes Atlantique Kin-ball Club", "NAKC",
                "https://i1.wp.com/www.kin-ball.fr/wp-content/uploads/2016/11/NAKC-Nantes.jpg?resize=100%2C100&ssl=1")
        );
    }

    @Test
    @DisplayName(" no clubs ")
    void unknown() throws Exception {
        DAOFactory.getInstance().truncateClub();
        Response response = clubFind(V1);

        checkResponse(response, OK);
        List<Club> clubs = getClubs(response);
        assertNotNull(clubs, " clubs shouldn't be null");
        assertEquals(0, clubs.size(), " only 2 clubs ");
    }
}
