package com.boschat.sikb.api.person;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.Person;
import com.boschat.sikb.model.PersonForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.PersistenceUtils.truncateData;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;

@DisplayName(" Create a person ")
class PersonCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() {
        truncateData();
    }

    @Test
    @DisplayName(" with only a firstName ")
    void withFirstNameOnly() throws Exception {
        PersonForCreation personForCreation = new PersonForCreation();
        personForCreation.setFirstName(PERSON_DEFAULT_FIRST_NAME);
        Response response = personCreate(V1, personForCreation);

        checkResponse(response, CREATED);
        Person person = getPerson(response);
        checkPerson(person, PERSON_DEFAULT_FIRST_NAME, null, null, null, null, null, null, null, null, null, null);
    }

    @Test
    @DisplayName(" with everything ")
    void withEverything() throws Exception {
        PersonForCreation personForCreation = new PersonForCreation();
        personForCreation.setFirstName(PERSON_DEFAULT_FIRST_NAME);
        personForCreation.setName(PERSON_DEFAULT_NAME);
        personForCreation.setSex(PERSON_DEFAULT_SEX);
        personForCreation.setBirthDate(PERSON_DEFAULT_BIRTH_DATE);
        personForCreation.setAddress(PERSON_DEFAULT_ADDRESS);
        personForCreation.setPostalCode(PERSON_DEFAULT_POSTAL_CODE);
        personForCreation.setCity(PERSON_DEFAULT_CITY);
        personForCreation.setPhoneNumber(PERSON_DEFAULT_PHONE_NUMBER);
        personForCreation.setEmail(PERSON_DEFAULT_EMAIL);
        personForCreation.setNationality(PERSON_DEFAULT_NATIONALITY);
        personForCreation.setFormations(PERSON_DEFAULT_FORMATIONS);
        Response response = personCreate(V1, personForCreation);

        checkResponse(response, CREATED);
        Person person = getPerson(response);
        checkPerson(person, PERSON_DEFAULT_FIRST_NAME, PERSON_DEFAULT_NAME, PERSON_DEFAULT_SEX, PERSON_DEFAULT_BIRTH_DATE, PERSON_DEFAULT_ADDRESS,
            PERSON_DEFAULT_POSTAL_CODE, PERSON_DEFAULT_CITY, PERSON_DEFAULT_PHONE_NUMBER, PERSON_DEFAULT_EMAIL, PERSON_DEFAULT_NATIONALITY,
            PERSON_DEFAULT_FORMATIONS);
    }
}
