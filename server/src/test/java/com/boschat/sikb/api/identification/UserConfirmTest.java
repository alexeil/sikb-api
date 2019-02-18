package com.boschat.sikb.api.identification;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.persistence.dao.DAOFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CONFIRM_TOKEN_EXPIRED;
import static com.boschat.sikb.common.configuration.ResponseCode.CONFIRM_TOKEN_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName(" Confirm a User email & password ")
@ExtendWith(JerseyTestExtension.class)
class UserConfirmTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadUsers();
    }

    @Test
    @DisplayName(" existing ")
    void existing() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setNewPassword(USER_DEFAULT_PASSWORD);
        Response response = userConfirm(V1, updatePassword, "ZDhjMTc3NTctMTk2Yi00Y2QyLWIzM2MtYjJiNDhlMWNiMjczMjAxOC0wMS0xOFQxMzoxMSswMTowMA");

        checkResponse(response, NO_CONTENT);
        com.boschat.sikb.tables.pojos.User userBean = DAOFactory.getInstance().getUserDAO().findById(3);
        assertAll("Check User Bean",
            () -> assertNotNull(userBean, " User shouldn't be null"),
            () -> assertNotNull(userBean.getPassword(), "Password shouldn't be null"),
            () -> assertNotNull(userBean.getSalt(), "Salt shouldn't be null"),
            () -> assertTrue(userBean.getEnabled(), "user should be Enabled"),
            () -> assertNull(userBean.getActivationtoken(), "Activationtoken should be null"),
            () -> assertNull(userBean.getActivationtokenexpirationdate(), "Activationtokenexpirationdate should be null")
        );
    }

    @Test
    @DisplayName(" unknown token ")
    void unknownToken() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setNewPassword(USER_DEFAULT_PASSWORD);
        Response response = userConfirm(V1, updatePassword, "test");

        checkResponse(response, CONFIRM_TOKEN_NOT_FOUND);
    }

    @Test
    @DisplayName(" no token ")
    void noToken() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setNewPassword(USER_DEFAULT_PASSWORD);
        Response response = userConfirm(V1, updatePassword, null);

        checkResponse(response, CONFIRM_TOKEN_NOT_FOUND);
    }

    @Test
    @DisplayName(" expired ")
    void expired() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setNewPassword(USER_DEFAULT_PASSWORD);
        Response response = userConfirm(V1, updatePassword, "ZDhjMTc3NTctMTk2Yi00Y2QyLWIzM2MtYjJiNDhlMWNiMjczMjAxOC0wMS0xOFQxMzoxMSswMToold");

        checkResponse(response, CONFIRM_TOKEN_EXPIRED);
    }
}
