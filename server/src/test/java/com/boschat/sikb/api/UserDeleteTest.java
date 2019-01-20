package com.boschat.sikb.api;

import com.boschat.sikb.AbstractTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.api.ResponseCode.DELETED;
import static com.boschat.sikb.api.ResponseCode.USER_NOT_FOUND;

@DisplayName(" Delete a user ")
class UserDeleteTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        truncateData();
        loadUsers("sql/insertUser.csv");
    }

    @Test
    @DisplayName(" default user ")
    void name() throws Exception {
        Response response = userDelete(V1, DEFAULT_USER_ID);
        checkResponse(response, DELETED);
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws Exception {
        Response response = userDelete(V1, 999);
        checkResponse(response, USER_NOT_FOUND, 999);
    }
}
