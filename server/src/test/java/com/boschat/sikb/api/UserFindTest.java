package com.boschat.sikb.api;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.User;
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

@DisplayName(" Find all users ")
class UserFindTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        truncateData();
        loadUsers("sql/insertUser.csv");
    }

    @Test
    @DisplayName(" (default) ")
    void getDefaultClub() throws Exception {
        Response response = userFind(V1);

        checkResponse(response, OK);
        List<User> users = getUsers(response);

        assertAll("Check users ",
                () -> assertNotNull(users, " users shouldn't be null"),
                () -> assertEquals(2, users.size(), " only 2 users "),
                () -> checkUser(users.get(0), DEFAULT_USER_EMAIL),
                () -> checkUser(users.get(1), "myEmail2@kin-ball.fr")
        );
    }

    @Test
    @DisplayName(" no clubs ")
    void unknown() throws Exception {
        truncateData();
        Response response = userFind(V1);

        checkResponse(response, OK);
        List<User> users = getUsers(response);
        assertNotNull(users, " users shouldn't be null");
        assertEquals(0, users.size(), " no users ");
    }
}
