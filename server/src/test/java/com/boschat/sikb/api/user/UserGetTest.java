package com.boschat.sikb.api.user;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.PersistenceUtils;
import com.boschat.sikb.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.USER_NOT_FOUND;

@DisplayName(" get a user ")
class UserGetTest extends AbstractTest {

    @BeforeAll
    static void loadDataSuite() throws IOException {
        PersistenceUtils.truncateData();
        PersistenceUtils.loadUsers();
    }

    @Test
    @DisplayName(" (default) ")
    void getDefaultUser() throws Exception {
        Response response = userGet(V1, CLUB_DEFAULT_ID);

        checkResponse(response, OK);
        User user = getUser(response);
        checkUser(user, USER_DEFAULT_EMAIL);
    }

    @Test
    @DisplayName(" unknown ")
    void unknown() throws Exception {
        Response response = userGet(V1, 999);

        checkResponse(response, USER_NOT_FOUND, 999);
    }
}
