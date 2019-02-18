package com.boschat.sikb.api.person;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.PERSON_NOT_FOUND;

@DisplayName(" get a person ")
@ExtendWith(JerseyTestExtension.class)
class PersonGetTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadPersons();
    }

    @Test
    @DisplayName(" right case ")
    void rightCase() throws Exception {
        Response response = personGet(V1, PERSON_DEFAULT_ID);

        checkResponse(response, OK);
        Person person = getPerson(response);
        checkPerson(person, PERSON_DEFAULT_FIRST_NAME, PERSON_DEFAULT_NAME, PERSON_DEFAULT_SEX, PERSON_DEFAULT_BIRTH_DATE, PERSON_DEFAULT_ADDRESS,
            PERSON_DEFAULT_POSTAL_CODE, PERSON_DEFAULT_CITY, PERSON_DEFAULT_PHONE_NUMBER, PERSON_DEFAULT_EMAIL, PERSON_DEFAULT_NATIONALITY,
            PERSON_DEFAULT_FORMATIONS, PERSON_MEDICAL_CERTIFICATE_VALIDITY, true);
    }

    @Test
    @DisplayName(" unknown person ")
    void unknownPerson() throws Exception {
        Response response = personGet(V1, 999);
        checkResponse(response, PERSON_NOT_FOUND, 999);
    }

}
