package com.boschat.sikb.api.user;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.User;
import com.boschat.sikb.model.UserForUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.USER_NOT_FOUND;

@DisplayName(" Update a user ")
@ExtendWith(JerseyTestExtension.class)
class UserUpdateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadUsers();
    }

    private static UserForUpdate buildUserForUpdate(String email) {
        UserForUpdate userForUpdate = new UserForUpdate();
        if (email != null) {
            userForUpdate.setEmail(email);
        }
        return userForUpdate;
    }

    @Test
    @DisplayName(" email ")
    void email() throws Exception {
        Response response = userUpdate(V1, USER_DEFAULT_ID, buildUserForUpdate("newEmail@kin-ball.fr"));

        checkResponse(response, OK);
        User user = getUser(response);
        checkUser(user, "newEmail@kin-ball.fr", PROFILE_TYPE_ID_ADMINISTRATOR, PROFILE_TYPE_NAME_ADMINISTRATOR, PROFILE_TYPE_FUNCTIONALITIES_ADMINISTRATOR,
            PROFILE_CLUB_IDS_DEFAULT);
    }

    @Test
    @DisplayName(" with nothing ")
    void nothing() throws Exception {
        Response response = userUpdate(V1, USER_DEFAULT_ID, buildUserForUpdate(null));

        checkResponse(response, OK);
        User user = getUser(response);
        checkUser(user, USER_DEFAULT_EMAIL, PROFILE_TYPE_ID_ADMINISTRATOR, PROFILE_TYPE_NAME_ADMINISTRATOR, PROFILE_TYPE_FUNCTIONALITIES_ADMINISTRATOR,
            PROFILE_CLUB_IDS_DEFAULT);
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws Exception {
        Response response = userUpdate(V1, 999, buildUserForUpdate("newEmail@kin-ball.fr"));

        checkResponse(response, USER_NOT_FOUND, 999);
    }
}
