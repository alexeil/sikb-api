package com.boschat.sikb.api.user;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.PersistenceUtils;
import com.boschat.sikb.model.User;
import com.boschat.sikb.model.UserForCreation;
import com.boschat.sikb.persistence.DAOFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ApplicationProperties.TEMPLATE_CREATE_USER_TITLE;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName(" Create a User ")
class UserCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() {
        PersistenceUtils.truncateData();
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

        com.boschat.sikb.tables.pojos.User userBean = DAOFactory.getInstance().getUserDAO().findById(user.getId());
        assertAll("Check User Bean",
            () -> assertNotNull(userBean, " User shouldn't be null"),
            () -> assertNull(userBean.getPassword(), "Password should be null"),
            () -> assertNull(userBean.getSalt(), "Password should be null"),
            () -> assertNotNull(userBean.getActivationtoken(), "Activationtoken shouldn't be null"),
            () -> assertNotNull(userBean.getActivationtokenexpirationdate(), "Activationtokenexpirationdate shouldn't be null")
        );

        checkEmail(DEFAULT_AFFILIATION_EMAIL, TEMPLATE_CREATE_USER_TITLE.getValue());
    }
}
