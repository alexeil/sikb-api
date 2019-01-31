package com.boschat.sikb.api.user;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.PersistenceUtils;
import com.boschat.sikb.model.User;
import com.boschat.sikb.model.UserForUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.USER_NOT_FOUND;

@DisplayName(" Update a user ")
class UserUpdateTest extends AbstractTest {

    private static UserForUpdate buildUserForUpdate(String email) {
        UserForUpdate userForUpdate = new UserForUpdate();
        if (email != null) {
            userForUpdate.setEmail(email);
        }
        return userForUpdate;
    }

    @BeforeEach
    void loadDataSuite() throws IOException {
        PersistenceUtils.truncateData();
        PersistenceUtils.loadUsers();
    }

    @Test
    @DisplayName(" email ")
    void name() throws Exception {
        Response response = userUpdate(V1, DEFAULT_CLUB_ID, buildUserForUpdate("newEmail@kin-ball.fr"));

        checkResponse(response, OK);
        User user = getUser(response);
        checkUser(user, "newEmail@kin-ball.fr");
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws Exception {
        Response response = userUpdate(V1, 999, buildUserForUpdate("newEmail@kin-ball.fr"));

        checkResponse(response, USER_NOT_FOUND, 999);
    }
}
