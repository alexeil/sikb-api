package com.boschat.sikb.api.club;

import com.boschat.sikb.AbstractTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.api.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.api.ResponseCode.NO_CONTENT;

@DisplayName(" Delete a club ")
class ClubDeleteTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        truncateData();
        loadClubs();
    }

    @Test
    @DisplayName(" default club ")
    void name() throws Exception {
        Response response = clubDelete(V1, DEFAULT_CLUB_ID);

        checkResponse(response, NO_CONTENT);
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws Exception {
        Response response = clubDelete(V1, 999);
        checkResponse(response, CLUB_NOT_FOUND, 999);
    }
}
