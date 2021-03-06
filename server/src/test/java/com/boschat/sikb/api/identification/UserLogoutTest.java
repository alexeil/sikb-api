package com.boschat.sikb.api.identification;

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

@DisplayName(" Logout a User ")
@ExtendWith(JerseyTestExtension.class)
class UserLogoutTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadUsers();
    }

    @Test
    @DisplayName(" existing ")
    void existing() throws Exception {
        Response response = userLogout(V1, USER_DEFAULT_ACCESS_TOKEN);
        checkResponse(response, NO_CONTENT);
    }

    @Test
    @DisplayName(" not existing ")
    void notExisting() throws Exception {
        Response response = userLogout(V1, "unknownToken");
        checkResponse(response, NO_CONTENT);
    }
}
