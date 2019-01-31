package com.boschat.sikb.api.identification;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.PersistenceUtils;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;

@DisplayName(" Login a User ")
class UserLoginTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        PersistenceUtils.truncateData();
        PersistenceUtils.loadUsers();
    }

    @Test
    @DisplayName(" existing ")
    void existing() throws Exception {
        Credentials credentials = new Credentials();
        credentials.setLogin(DEFAULT_USER_EMAIL);
        credentials.setPassword(DEFAULT_USER_PASSWORD);
        Response response = userLogin(V1, credentials);

        checkResponse(response, CREATED);
        Session session = getSession(response);
        checkSession(session);
    }
}
