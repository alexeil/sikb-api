package com.boschat.sikb.api;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.User;
import com.boschat.sikb.model.UserForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.api.ResponseCode.CREATED;

@DisplayName(" Create a User ")
class UserCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() {
        truncateData();
    }

    @Test
    @DisplayName(" with only a email ")
    void withEmailOnly() throws Exception {
        UserForCreation userForCreation = new UserForCreation();
        userForCreation.setEmail(DEFAULT_AFFILIATION_EMAIL);
        Response response = userCreate(V1, userForCreation);

        checkResponse(response, CREATED);
        User user = getUser(response);
        checkUser(user, DEFAULT_AFFILIATION_EMAIL);
    }
}
