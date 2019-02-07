package com.boschat.sikb.api.person;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.model.MedicalCertificate;
import com.boschat.sikb.model.MedicalCertificateForCreation;
import com.boschat.sikb.model.Person;
import com.boschat.sikb.model.PersonForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.PersistenceUtils.truncateData;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;

@DisplayName(" Create a person ")
class PersonCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        truncateData();
        loadPersons();
    }

    @Test
    @DisplayName(" medical certificate")
    void medicalCertificate() throws Exception {

        MedicalCertificateForCreation medicalCertificateForCreation = new MedicalCertificateForCreation();
        medicalCertificateForCreation.setMedicalCertificateBeginValidityDate("2018-01-02");
        medicalCertificateForCreation.setMedicalCertificateFileName(new File("src/test/resources/certificates/certificate.jpg"));
        Response response = medicalCertificateCreate(V1, PERSON_DEFAULT_ID, medicalCertificateForCreation);

        checkResponse(response, CREATED);
        MedicalCertificate medicalCertificate = getMedicalCertificate(response);
        checkMedicalCertificate(medicalCertificate, LocalDate.of(2018, 1, 2));
    }

    @Test
    @DisplayName(" with only a firstName ")
    void withFirstNameOnly() throws Exception {
        PersonForCreation personForCreation = new PersonForCreation();
        personForCreation.setFirstName(PERSON_DEFAULT_FIRST_NAME);

        MedicalCertificate medicalCertificate = new MedicalCertificate();
        medicalCertificate.setMedicalCertificateBeginValidityDate(LocalDate.of(1990, 4, 4));
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
