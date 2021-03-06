package com.boschat.sikb.api.identification;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.UpdatePassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.MISSING_BODY_FIELD;
import static com.boschat.sikb.common.configuration.ResponseCode.NEW_PASSWORD_CANNOT_BE_SAME;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.common.configuration.ResponseCode.VALIDATION_ERROR;
import static com.boschat.sikb.common.configuration.ResponseCode.WRONG_OLD_PASSWORD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_UPDATE_PASSWORD;

@DisplayName(" Update a User password ")
@ExtendWith(JerseyTestExtension.class)
class UserUpdatePasswordTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadUsers();
    }

    @Test
    @DisplayName(" missing passwords ")
    void missingPasswords() throws Exception {
        Response response = userUpdatePassword(V1, null, USER_DEFAULT_ACCESS_TOKEN);
        checkResponse(response, MISSING_BODY_FIELD, BODY_FIELD_UPDATE_PASSWORD);
    }

    @Test
    @DisplayName(" missing old password ")
    void missingOldPassword() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setNewPassword(USER_DEFAULT_PASSWORD);
        Response response = userUpdatePassword(V1, updatePassword, USER_DEFAULT_ACCESS_TOKEN);
        checkResponse(response, VALIDATION_ERROR, "userUpdatePassword.arg0.oldPassword: must not be null");

    }

    @Test
    @DisplayName(" missing new password ")
    void missingNewPassword() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setOldPassword(USER_DEFAULT_PASSWORD);
        Response response = userUpdatePassword(V1, updatePassword, USER_DEFAULT_ACCESS_TOKEN);
        checkResponse(response, VALIDATION_ERROR, "userUpdatePassword.arg0.newPassword: must not be null");

    }

    @Test
    @DisplayName(" same password ")
    void samePassword() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setOldPassword(USER_DEFAULT_PASSWORD);
        updatePassword.setNewPassword(USER_DEFAULT_PASSWORD);
        Response response = userUpdatePassword(V1, updatePassword, USER_DEFAULT_ACCESS_TOKEN);
        checkResponse(response, NEW_PASSWORD_CANNOT_BE_SAME);
    }

    @Test
    @DisplayName(" right case ")
    void existing() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setOldPassword(USER_DEFAULT_PASSWORD);
        updatePassword.setNewPassword("Test2");
        Response response = userUpdatePassword(V1, updatePassword, USER_DEFAULT_ACCESS_TOKEN);
        checkResponse(response, NO_CONTENT);
    }

    @Test
    @DisplayName(" wrong old password ")
    void wrongOld() throws Exception {
        UpdatePassword updatePassword = new UpdatePassword();
        updatePassword.setOldPassword("Test5");
        updatePassword.setNewPassword("Test2");
        Response response = userUpdatePassword(V1, updatePassword, USER_DEFAULT_ACCESS_TOKEN);
        checkResponse(response, WRONG_OLD_PASSWORD);
    }
}
