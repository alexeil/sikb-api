package com.boschat.sikb.api.club;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.PersistenceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;

@DisplayName(" Delete a club ")
class ClubDeleteTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        PersistenceUtils.truncateData();
        PersistenceUtils.loadClubs();
    }

    @Test
    @DisplayName(" default club ")
    void name() throws Exception {
        Response response = clubDelete(V1, CLUB_DEFAULT_ID);

        checkResponse(response, NO_CONTENT);
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws Exception {
        Response response = clubDelete(V1, 999);
        checkResponse(response, CLUB_NOT_FOUND, 999);
    }
}
