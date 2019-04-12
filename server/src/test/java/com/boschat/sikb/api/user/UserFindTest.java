package com.boschat.sikb.api.user;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.User;
import com.boschat.sikb.persistence.dao.DAOFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Find all users ")
@ExtendWith(JerseyTestExtension.class)
class UserFindTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadUsers();
    }

    @Test
    @DisplayName(" (default) ")
    void getDefaultClub() throws Exception {
        Response response = userFind(V1);

        checkResponse(response, OK);
        List<User> users = getUsers(response);

        assertAll("Check users ",
            () -> assertNotNull(users, " users shouldn't be null"),
            () -> assertEquals(4, users.size(), " only 2 users "),
            () -> checkUser(users.get(0), USER_DEFAULT_EMAIL, PROFILE_TYPE_ID_ADMINISTRATOR, PROFILE_TYPE_NAME_ADMINISTRATOR,
                PROFILE_TYPE_FUNCTIONALITIES_ADMINISTRATOR, PROFILE_CLUB_IDS),
            () -> checkUser(users.get(1), "myEmail2@kin-ball.fr", PROFILE_TYPE_ID_ADMINISTRATOR, PROFILE_TYPE_NAME_ADMINISTRATOR,
                PROFILE_TYPE_FUNCTIONALITIES_ADMINISTRATOR, PROFILE_CLUB_IDS)
        );
    }

    @Test
    @DisplayName(" no users ")
    void unknown() throws Exception {
        DAOFactory.getInstance().truncateUser();
        Response response = userFind(V1);

        checkResponse(response, OK);
        List<User> users = getUsers(response);
        assertNotNull(users, " users shouldn't be null");
        assertEquals(0, users.size(), " no users ");
    }
}
