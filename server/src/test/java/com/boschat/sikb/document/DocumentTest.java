package com.boschat.sikb.document;

import com.boschat.sikb.common.configuration.ConfigLoader;
import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.common.exceptions.TechnicalException;
import com.boschat.sikb.model.DocumentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.boschat.sikb.AbstractTest.CLUB_LOGO_KEY;
import static com.boschat.sikb.AbstractTest.PERSON_MEDICAL_CERTIFICATE_KEY;
import static com.boschat.sikb.AbstractTest.PERSON_PHOTO_KEY;
import static com.boschat.sikb.AbstractTest.reloadEverythingForTests;
import static com.boschat.sikb.AbstractTest.setEnvVariablesTests;
import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadLicences;
import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.common.configuration.ApplicationProperties.JASPER_TEMPLATE_LICENCE_COLORS_BY_LICENCE_TYPE;
import static com.boschat.sikb.common.configuration.ApplicationProperties.JASPER_TEMPLATE_LICENCE_LOGO;
import static com.boschat.sikb.common.configuration.ResponseCode.DOCUMENT_TYPE_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.EXPORT_PDF_ERROR;
import static com.boschat.sikb.common.configuration.ResponseCode.JASPER_TEMPLATE_ERROR;
import static com.boschat.sikb.common.configuration.ResponseCode.LICENCE_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.LOGO_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.MEDICAL_CERTIFICATE_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.PHOTO_NOT_FOUND;
import static com.boschat.sikb.model.DocumentType.LICENCE_TYPE;
import static com.boschat.sikb.model.DocumentType.LOGO_TYPE;
import static com.boschat.sikb.model.DocumentType.MEDICAL_CERTIFICATE_TYPE;
import static com.boschat.sikb.model.DocumentType.PHOTO_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Document Servlet")
class DocumentTest {

    private static final String UNKNOWN_ID = "AAAAA";

    @BeforeAll
    static void start() throws Exception {
        setEnvVariablesTests();
        reloadEverythingForTests();
        loadPersons();
        loadSeasons();
        loadClubs();
        loadLicences();
    }

    private void callDocument(String documentType, String id, Throwable exception) {
        try {
            //FileOutputStream out = new FileOutputStream("test.pdf");
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000000000);
            DocumentType.fromValue(documentType).writeDocument(id, out);
            assertTrue(out.size() > 0, "output shouldn't be null or empty");

            if (exception != null) {
                fail("an exception should have been raised");
            }
        } catch (Exception e) {
            if (exception == null) {
                fail("an exception shouldn't have been raised", e);
            } else {
                assertEquals(e.getMessage(), exception.getMessage(), "wrong exception");
            }
        }
    }

    @Test
    @DisplayName(" Generate unknown Type ")
    void unknownType() {
        callDocument("unknownType", UNKNOWN_ID, new FunctionalException(DOCUMENT_TYPE_NOT_FOUND, "unknownType"));
    }

    @Nested
    @DisplayName(" Generate medical Certificate ")
    class MedicalCertificateTests {

        @Test
        @DisplayName(" right case ")
        void rightCase() {
            callDocument(MEDICAL_CERTIFICATE_TYPE.getKey(), PERSON_MEDICAL_CERTIFICATE_KEY, null);
        }

        @Test
        @DisplayName(" unknown medical Certificate ")
        void unknownMedicalCertificate() {
            callDocument(MEDICAL_CERTIFICATE_TYPE.getKey(), UNKNOWN_ID, new FunctionalException(MEDICAL_CERTIFICATE_NOT_FOUND, UNKNOWN_ID));
        }
    }

    @Nested
    @DisplayName(" Generate Licence ")
    class Licence {

        @Test
        @DisplayName(" right case ")
        void rightCase() {
            callDocument(LICENCE_TYPE.getKey(), "1234KBAR20182019", null);
        }

        @Test
        @DisplayName(" withoutPhoto ")
        void withoutPhoto() {
            callDocument(LICENCE_TYPE.getKey(), "1666KBAR20182019", null);
        }

        @Test
        @DisplayName(" unknown Licence ")
        void unknownLicence() {
            callDocument(LICENCE_TYPE.getKey(), UNKNOWN_ID, new FunctionalException(LICENCE_NOT_FOUND, UNKNOWN_ID));
        }

        @Test
        @DisplayName(" two licences in a row ")
        void twoLicencesInARow() {
            callDocument(LICENCE_TYPE.getKey(), "1666KBAR20182019", null);
            callDocument(LICENCE_TYPE.getKey(), "1234KBAR20182019", null);
        }

        @Nested
        @DisplayName(" with errors ")
        class errors {

            @BeforeEach
            void init() throws IOException {
                reloadEverythingForTests();
            }

            @Test
            @DisplayName(" logo not found ")
            void logoNotFound() {
                ConfigLoader.getInstance().setProperties(JASPER_TEMPLATE_LICENCE_LOGO, "test.jpg");
                callDocument(LICENCE_TYPE.getKey(), "1234KBAR20182019", new TechnicalException(EXPORT_PDF_ERROR));
            }

            @Test
            @DisplayName(" licence types with wrong format ")
            void licenceTypesWrongFormat() {
                ConfigLoader.getInstance().setProperties(JASPER_TEMPLATE_LICENCE_COLORS_BY_LICENCE_TYPE, "one;blue.png;two;blue.png");
                callDocument(LICENCE_TYPE.getKey(), "1234KBAR20182019",
                    new TechnicalException(JASPER_TEMPLATE_ERROR, "src/main/resources/templates/licence/licence.jrxml"));
            }
        }
    }

    @Nested
    @DisplayName(" Generate Photo ")
    class Photo {

        @Test
        @DisplayName(" right case ")
        void rightCase() {
            callDocument(PHOTO_TYPE.getKey(), PERSON_PHOTO_KEY, null);
        }

        @Test
        @DisplayName(" unknown photo ")
        void unknownPhoto() {
            callDocument(PHOTO_TYPE.getKey(), UNKNOWN_ID, new FunctionalException(PHOTO_NOT_FOUND, UNKNOWN_ID));
        }

    }

    @Nested
    @DisplayName(" Generate Logo ")
    class Logo {

        @Test
        @DisplayName(" right case ")
        void rightCase() {
            callDocument(LOGO_TYPE.getKey(), CLUB_LOGO_KEY, null);
        }

        @Test
        @DisplayName(" unknown Logo ")
        void unknownPhoto() {
            callDocument(LOGO_TYPE.getKey(), UNKNOWN_ID, new FunctionalException(LOGO_NOT_FOUND, UNKNOWN_ID));
        }

    }
}
