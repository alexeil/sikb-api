package com.boschat.sikb.api.person;

import com.boschat.sikb.AbstractTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.common.configuration.ResponseCode.PERSON_NOT_FOUND;

@DisplayName(" Delete a person ")
class PersonDeleteTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadPersons();
    }

    @Test
    @DisplayName(" right case ")
    void rightCase() throws Exception {
        Response response = personDelete(V1, PERSON_DEFAULT_ID);
        checkResponse(response, NO_CONTENT);
    }

    @Test
    @DisplayName(" unknown person ")
    void unknownPerson() throws Exception {
        Response response = personDelete(V1, 999);
        checkResponse(response, PERSON_NOT_FOUND, 999);
    }
}
