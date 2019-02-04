package com.boschat.sikb.api.person;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.PersistenceUtils.truncateData;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName(" Find persons ")
class PersonFindTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        truncateData();
        loadPersons();
    }

    @Test
    @DisplayName(" right case ")
    void rightCase() throws Exception {
        Response response = personFind(V1);

        checkResponse(response, OK);
        List<Person> persons = getPersons(response);
        assertAll("Check persons ",
            () -> assertNotNull(persons, " persons shouldn't be null"),
            () -> assertEquals(2, persons.size(), " only 2 persons "),
            () -> checkPerson(persons.get(0), PERSON_DEFAULT_FIRST_NAME, PERSON_DEFAULT_NAME, PERSON_DEFAULT_SEX, PERSON_DEFAULT_BIRTH_DATE,
                PERSON_DEFAULT_ADDRESS,
                PERSON_DEFAULT_POSTAL_CODE, PERSON_DEFAULT_CITY, PERSON_DEFAULT_PHONE_NUMBER, PERSON_DEFAULT_EMAIL, PERSON_DEFAULT_NATIONALITY,
                PERSON_DEFAULT_FORMATIONS));
    }

    @Test
    @DisplayName(" no person ")
    void noPerson() throws Exception {
        truncateData();
        Response response = personFind(V1);

        checkResponse(response, OK);
        List<Person> persons = getPersons(response);
        assertAll("Check persons ",
            () -> assertNotNull(persons, " persons shouldn't be null"),
            () -> assertEquals(0, persons.size(), " only 2 persons "));
    }

}
