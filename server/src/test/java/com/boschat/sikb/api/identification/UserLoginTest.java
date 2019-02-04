package com.boschat.sikb.api.identification;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.PersistenceUtils.truncateData;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.WRONG_LOGIN_OR_PASSWORD;

@DisplayName(" Login a User ")
class UserLoginTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        truncateData();
        loadUsers();
    }

    @Test
    @DisplayName(" existing ")
    void existing() throws Exception {
        Credentials credentials = new Credentials();
        credentials.setLogin(USER_DEFAULT_EMAIL);
        credentials.setPassword(USER_DEFAULT_PASSWORD);
        Response response = userLogin(V1, credentials);

        checkResponse(response, CREATED);
        Session session = getSession(response);
        checkSession(session);
    }

    @Test
    @DisplayName(" unknown login ")
    void unknownLogin() throws Exception {
        Credentials credentials = new Credentials();
        credentials.setLogin("testLogin@kin-ball.fr");
        credentials.setPassword(USER_DEFAULT_PASSWORD);
        Response response = userLogin(V1, credentials);
        checkResponse(response, WRONG_LOGIN_OR_PASSWORD);
    }

    @Test
    @DisplayName(" notYetConfirmed ")
    void notYetConfirmed() throws Exception {
        Credentials credentials = new Credentials();
        credentials.setLogin("myEmail2@kin-ball.fr");
        credentials.setPassword(USER_DEFAULT_PASSWORD);
        Response response = userLogin(V1, credentials);
        checkResponse(response, WRONG_LOGIN_OR_PASSWORD);
    }

    @Test
    @DisplayName(" wrong password ")
    void wrongPassword() throws Exception {
        Credentials credentials = new Credentials();
        credentials.setLogin(USER_DEFAULT_EMAIL);
        credentials.setPassword("WrongPassword");
        Response response = userLogin(V1, credentials);
        checkResponse(response, WRONG_LOGIN_OR_PASSWORD);
    }
}
