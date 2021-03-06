package com.boschat.sikb.api.user;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.common.configuration.ResponseCode.USER_NOT_FOUND;

@DisplayName(" Delete a user ")
@ExtendWith(JerseyTestExtension.class)
class UserDeleteTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadUsers();
    }

    @Test
    @DisplayName(" default user ")
    void name() throws Exception {
        Response response = userDelete(V1, USER_DEFAULT_ID);
        checkResponse(response, NO_CONTENT);
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws Exception {
        Response response = userDelete(V1, 999);
        checkResponse(response, USER_NOT_FOUND, 999);
    }
}
