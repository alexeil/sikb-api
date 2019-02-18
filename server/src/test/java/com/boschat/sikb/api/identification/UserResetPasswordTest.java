package com.boschat.sikb.api.identification;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Reset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ApplicationProperties.TEMPLATE_RESET_USER_PASSWORD_TITLE;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.common.configuration.ResponseCode.USER_NOT_FOUND;

@DisplayName(" Ask to reset a password")
@ExtendWith(JerseyTestExtension.class)
class UserResetPasswordTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadUsers();
    }

    @Test
    @DisplayName(" existing ")
    void existing() throws Exception {
        Reset reset = new Reset();
        reset.setLogin(USER_DEFAULT_EMAIL);
        Response response = resetUserPassword(V1, reset);
        checkResponse(response, NO_CONTENT);

        checkEmail(USER_DEFAULT_EMAIL, TEMPLATE_RESET_USER_PASSWORD_TITLE.getValue());
    }

    @Test
    @DisplayName(" unknown login ")
    void unknownToken() throws Exception {
        Reset reset = new Reset();
        reset.setLogin("unknownMail");
        Response response = resetUserPassword(V1, reset);
        checkResponse(response, USER_NOT_FOUND, "unknownMail");
    }
}
