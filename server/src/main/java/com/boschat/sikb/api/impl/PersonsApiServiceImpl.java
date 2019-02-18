package com.boschat.sikb.api.impl;

import com.boschat.sikb.api.PersonsApiService;
import com.boschat.sikb.model.LicenceForCreation;
import com.boschat.sikb.model.LicenceForUpdate;
import com.boschat.sikb.model.PersonForCreation;
import com.boschat.sikb.model.PersonForUpdate;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.InputStream;

import static com.boschat.sikb.Helper.runService;
import static com.boschat.sikb.api.CallType.LICENCE_CREATE;
import static com.boschat.sikb.api.CallType.LICENCE_DELETE;
import static com.boschat.sikb.api.CallType.LICENCE_UPDATE;
import static com.boschat.sikb.api.CallType.MEDICAL_CERTIFICATE_CREATE;
import static com.boschat.sikb.api.CallType.PERSON_CREATE;
import static com.boschat.sikb.api.CallType.PERSON_DELETE;
import static com.boschat.sikb.api.CallType.PERSON_FIND;
import static com.boschat.sikb.api.CallType.PERSON_GET;
import static com.boschat.sikb.api.CallType.PERSON_UPDATE;
import static com.boschat.sikb.api.CallType.PHOTO_CREATE;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-01-17T10:05:39.503+01:00[Europe/Paris]")
public class PersonsApiServiceImpl extends PersonsApiService {

    @Override
    public Response createPerson(String accessToken, PersonForCreation personForCreation, SecurityContext securityContext) {
        return runService(PERSON_CREATE, accessToken, securityContext, personForCreation);
    }

    @Override
    public Response uploadMedicalCertificate(String accessToken, Integer personId, InputStream medicalCertificateFileNameInputStream,
        FormDataContentDisposition medicalCertificateFileNameDetail, String medicalCertificateBeginValidityDate, SecurityContext securityContext) {
        return runService(MEDICAL_CERTIFICATE_CREATE, accessToken, securityContext, personId, medicalCertificateFileNameInputStream,
            medicalCertificateFileNameDetail,
            medicalCertificateBeginValidityDate);
    }

    @Override
    public Response createPersonLicence(String accessToken, Integer personId, Integer clubId, String season, LicenceForCreation licence,
        SecurityContext securityContext) {
        return runService(LICENCE_CREATE, accessToken, securityContext, personId, clubId, season, licence);
    }

    @Override
    public Response uploadPhoto(String accessToken, Integer personId, InputStream photoFileNameInputStream, FormDataContentDisposition photoFileNameDetail,
        SecurityContext securityContext) {
        return runService(PHOTO_CREATE, accessToken, securityContext, personId, photoFileNameInputStream, photoFileNameDetail);
    }

    @Override
    public Response deletePerson(String accessToken, Integer personId, SecurityContext securityContext) {
        return runService(PERSON_DELETE, accessToken, securityContext, personId);
    }

    @Override
    public Response deletePersonLicence(String accessToken, Integer personId, Integer clubId, String seasonId, String licenceId,
        SecurityContext securityContext) {
        return runService(LICENCE_DELETE, accessToken, securityContext, personId, clubId, seasonId, licenceId);

    }

    @Override
    public Response findPersons(String accessToken, SecurityContext securityContext) {
        return runService(PERSON_FIND, accessToken, securityContext);
    }

    @Override
    public Response getLicences(String accessToken, Integer personId, Integer clubId, String seasonId, SecurityContext securityContext) {
        return null;
    }

    @Override
    public Response getPerson(String accessToken, Integer personId, SecurityContext securityContext) {
        return runService(PERSON_GET, accessToken, securityContext, personId);
    }

    @Override
    public Response updatePerson(String accessToken, Integer personId, PersonForUpdate personForUpdate, SecurityContext securityContext) {
        return runService(PERSON_UPDATE, accessToken, securityContext, personId, personForUpdate);
    }

    @Override
    public Response updatePersonLicence(String accessToken, Integer personId, Integer clubId, String seasonId, String licenceId,
        LicenceForUpdate licenceForUpdate, SecurityContext securityContext) {
        return runService(LICENCE_UPDATE, accessToken, securityContext, personId, clubId, seasonId, licenceId, licenceForUpdate);
    }
}
