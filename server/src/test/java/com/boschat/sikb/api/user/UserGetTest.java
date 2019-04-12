package com.boschat.sikb.api.user;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.USER_NOT_FOUND;

@DisplayName(" get a user ")
@ExtendWith(JerseyTestExtension.class)
class UserGetTest extends AbstractTest {

    @BeforeAll
    static void loadDataSuite() throws IOException {
        loadUsers();
    }

    @Test
    @DisplayName(" (default) ")
    void getDefaultUser() throws Exception {
        Response response = userGet(V1, CLUB_DEFAULT_ID);

        checkResponse(response, OK);
        User user = getUser(response);
        checkUser(user, USER_DEFAULT_EMAIL, PROFILE_TYPE_ID_ADMINISTRATOR, PROFILE_TYPE_NAME_ADMINISTRATOR, PROFILE_TYPE_FUNCTIONALITIES_ADMINISTRATOR,
            PROFILE_CLUB_IDS);
    }

    @Test
    @DisplayName(" unknown ")
    void unknown() throws Exception {
        Response response = userGet(V1, 999);

        checkResponse(response, USER_NOT_FOUND, 999);
    }
}
