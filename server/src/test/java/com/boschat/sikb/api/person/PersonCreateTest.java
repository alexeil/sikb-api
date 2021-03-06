package com.boschat.sikb.api.person;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.JerseyTestExtension;
import com.boschat.sikb.model.MedicalCertificate;
import com.boschat.sikb.model.MedicalCertificateForCreation;
import com.boschat.sikb.model.Person;
import com.boschat.sikb.model.PersonForCreation;
import com.boschat.sikb.model.Photo;
import com.boschat.sikb.model.PhotoForCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.FILE_EXTENSION_NOT_AUTHORIZED;
import static com.boschat.sikb.common.configuration.ResponseCode.FILE_EXTENSION_NOT_SUPPORTED;
import static com.boschat.sikb.common.configuration.ResponseCode.INVALID_BODY_FIELD;
import static com.boschat.sikb.common.configuration.ResponseCode.MISSING_BODY_FIELD;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_FIRST_NAME;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_MEDICAL_CERTIFICATE_VALIDITY;

@DisplayName(" Create a person ")
@ExtendWith(JerseyTestExtension.class)
class PersonCreateTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws IOException {
        loadPersons();
    }

    @Test
    @DisplayName(" with only a required fields ")
    void withRequiredFields() throws Exception {
        PersonForCreation personForCreation = new PersonForCreation();
        personForCreation.setFirstName(PERSON_DEFAULT_FIRST_NAME);
        personForCreation.setName(PERSON_DEFAULT_NAME);
        personForCreation.setEmail(PERSON_DEFAULT_EMAIL);
        Response response = personCreate(V1, personForCreation);

        checkResponse(response, CREATED);
        Person person = getPerson(response);
        checkPerson(person, PERSON_DEFAULT_FIRST_NAME, PERSON_DEFAULT_NAME, null, null, null, null, null, null, PERSON_DEFAULT_EMAIL, null, null, null, false);
    }

    @Test
    @DisplayName(" with no field ")
    void withNoFields() throws Exception {
        PersonForCreation personForCreation = new PersonForCreation();
        Response response = personCreate(V1, personForCreation);
        checkResponse(response, MISSING_BODY_FIELD, BODY_FIELD_FIRST_NAME);
    }

    @Nested
    @DisplayName(" medical certificate")
    class MedicalCertificateTests {

        @Test
        @DisplayName(" right case")
        void medicalCertificate() throws Exception {
            MedicalCertificateForCreation medicalCertificateForCreation = new MedicalCertificateForCreation();
            medicalCertificateForCreation.setMedicalCertificateBeginValidityDate("2018-01-02");
            medicalCertificateForCreation.setMedicalCertificateFileName(new File(PATH_DOCUMENT_CERTIFICATE));
            Response response = medicalCertificateUpload(V1, PERSON_DEFAULT_ID, medicalCertificateForCreation);

            checkResponse(response, OK);
            MedicalCertificate medicalCertificate = getMedicalCertificate(response);
            checkMedicalCertificate(medicalCertificate, LocalDate.of(2018, 1, 2));
        }

        @Test
        @DisplayName(" no invalid validity date")
        void noInvalidValidityDate() throws Exception {
            MedicalCertificateForCreation medicalCertificateForCreation = new MedicalCertificateForCreation();
            medicalCertificateForCreation.setMedicalCertificateBeginValidityDate("2018/01/02");
            medicalCertificateForCreation.setMedicalCertificateFileName(new File(PATH_DOCUMENT_CERTIFICATE));
            Response response = medicalCertificateUpload(V1, PERSON_DEFAULT_ID, medicalCertificateForCreation);

            checkResponse(response, INVALID_BODY_FIELD, BODY_FIELD_MEDICAL_CERTIFICATE_VALIDITY, "2018/01/02");
        }

        @Test
        @DisplayName(" File extension not supported")
        void fileExtensionNotSupported() throws Exception {
            MedicalCertificateForCreation medicalCertificateForCreation = new MedicalCertificateForCreation();
            medicalCertificateForCreation.setMedicalCertificateBeginValidityDate("2018-01-02");
            medicalCertificateForCreation.setMedicalCertificateFileName(new File("src/test/resources/documents/certificate.toto"));
            Response response = medicalCertificateUpload(V1, PERSON_DEFAULT_ID, medicalCertificateForCreation);

            checkResponse(response, FILE_EXTENSION_NOT_SUPPORTED, "certificate.toto");
        }

        @Test
        @DisplayName(" File extension not authorized")
        void fileExtensionNotAuthorized() throws Exception {
            MedicalCertificateForCreation medicalCertificateForCreation = new MedicalCertificateForCreation();
            medicalCertificateForCreation.setMedicalCertificateBeginValidityDate("2018-01-02");
            medicalCertificateForCreation.setMedicalCertificateFileName(new File("src/test/resources/documents/certificate.txt"));
            Response response = medicalCertificateUpload(V1, PERSON_DEFAULT_ID, medicalCertificateForCreation);

            checkResponse(response, FILE_EXTENSION_NOT_AUTHORIZED, "text/plain", "certificate.txt");
        }
    }

    @Nested
    @DisplayName(" photo")
    class PhotoTests {

        @Test
        @DisplayName(" right Case ")
        void rightCase() throws Exception {
            PhotoForCreation photoForCreation = new PhotoForCreation();
            photoForCreation.setPhotoFileName(new File(PATH_DOCUMENT_PHOTO));
            Response response = photoUpload(V1, PERSON_DEFAULT_ID, photoForCreation);

            checkResponse(response, OK);
            Photo photo = getPhoto(response);
            checkPhoto(photo, true);
        }
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
            PERSON_DEFAULT_FORMATIONS, null, false);
    }
}
