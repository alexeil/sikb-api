package com.boschat.sikb.context;

import com.boschat.sikb.common.exceptions.TechnicalException;
import com.boschat.sikb.model.Formation;
import com.boschat.sikb.model.PersonForCreation;
import com.boschat.sikb.model.PersonForUpdate;
import com.boschat.sikb.model.Sex;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static com.boschat.sikb.common.configuration.ApplicationProperties.CHECK_BODY_FIELD_MEDICAL_CERTIFICATE_VALIDITY_REGEXP;
import static com.boschat.sikb.common.configuration.ResponseCode.INVALID_BODY_FIELD;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_EMAIL;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_FIRST_NAME;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_MEDICAL_CERTIFICATE;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_MEDICAL_CERTIFICATE_FILE_NAME;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_MEDICAL_CERTIFICATE_VALIDITY;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_NAME;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_PHOTO;
import static com.boschat.sikb.common.configuration.SikbConstants.BODY_FIELD_PHOTO_FILENAME;
import static com.boschat.sikb.common.utils.DateUtils.parseLocalDate;
import static com.boschat.sikb.model.DocumentType.MEDICAL_CERTIFICATE_TYPE;
import static com.boschat.sikb.model.DocumentType.PHOTO_TYPE;
import static com.boschat.sikb.service.PersonUtils.checkContentType;
import static com.boschat.sikb.utils.CheckUtils.checkRequestBodyField;

public class CreateOrUpdatePersonContext {

    private String firstName;

    private String name;

    private Sex sex = null;

    private LocalDate birthDate;

    private String address;

    private String postalCode;

    private String city;

    private String phoneNumber;

    private String email;

    private String nationality;

    private List<Formation> formations = null;

    private byte[] medicalCertificateFileNameInputStream;

    private LocalDate medicalCertificateBeginValidityDate;

    private byte[] photoFileNameInputStream;

   /* private static CreateOrUpdatePersonContext buildCommon(PersonForUpdate person) {
        CreateOrUpdatePersonContext createOrUpdateContext = new CreateOrUpdatePersonContext();
        createOrUpdateContext.setFirstName(person.getFirstName());
        createOrUpdateContext.setName(person.getName());
        createOrUpdateContext.setSex(person.getSex());
        createOrUpdateContext.setBirthDate(person.getBirthDate());
        createOrUpdateContext.setAddress(person.getAddress());
        createOrUpdateContext.setPostalCode(person.getPostalCode());
        createOrUpdateContext.setCity(person.getCity());
        createOrUpdateContext.setPhoneNumber(person.getPhoneNumber());
        createOrUpdateContext.setEmail(person.getEmail());
        createOrUpdateContext.setNationality(person.getNationality());
        createOrUpdateContext.setFormations(person.getFormations());
        return createOrUpdateContext;
    }*/

    public static CreateOrUpdatePersonContext create(PersonForUpdate person) {
        CreateOrUpdatePersonContext createOrUpdateContext = new CreateOrUpdatePersonContext();
        createOrUpdateContext.setFirstName(person.getFirstName());
        createOrUpdateContext.setName(person.getName());
        createOrUpdateContext.setSex(person.getSex());
        createOrUpdateContext.setBirthDate(person.getBirthDate());
        createOrUpdateContext.setAddress(person.getAddress());
        createOrUpdateContext.setPostalCode(person.getPostalCode());
        createOrUpdateContext.setCity(person.getCity());
        createOrUpdateContext.setPhoneNumber(person.getPhoneNumber());
        createOrUpdateContext.setEmail(person.getEmail());
        createOrUpdateContext.setNationality(person.getNationality());
        createOrUpdateContext.setFormations(person.getFormations());
        return createOrUpdateContext;
    }

    public static CreateOrUpdatePersonContext create(PersonForCreation person) {
        checkRequestBodyField(person.getFirstName(), BODY_FIELD_FIRST_NAME);
        checkRequestBodyField(person.getName(), BODY_FIELD_NAME);
        checkRequestBodyField(person.getEmail(), BODY_FIELD_EMAIL);
        CreateOrUpdatePersonContext createOrUpdateContext = new CreateOrUpdatePersonContext();
        createOrUpdateContext.setFirstName(person.getFirstName());
        createOrUpdateContext.setName(person.getName());
        createOrUpdateContext.setSex(person.getSex());
        createOrUpdateContext.setBirthDate(person.getBirthDate());
        createOrUpdateContext.setAddress(person.getAddress());
        createOrUpdateContext.setPostalCode(person.getPostalCode());
        createOrUpdateContext.setCity(person.getCity());
        createOrUpdateContext.setPhoneNumber(person.getPhoneNumber());
        createOrUpdateContext.setEmail(person.getEmail());
        createOrUpdateContext.setNationality(person.getNationality());
        createOrUpdateContext.setFormations(person.getFormations());
        return createOrUpdateContext;
    }

    public static CreateOrUpdatePersonContext create(InputStream medicalCertificateStream, FormDataContentDisposition medicalCertificateFileNameDetail,
        String validity) {
        checkContentType(MEDICAL_CERTIFICATE_TYPE, medicalCertificateFileNameDetail.getFileName());
        checkRequestBodyField(medicalCertificateStream, BODY_FIELD_MEDICAL_CERTIFICATE_FILE_NAME);
        checkRequestBodyField(validity, BODY_FIELD_MEDICAL_CERTIFICATE_VALIDITY, CHECK_BODY_FIELD_MEDICAL_CERTIFICATE_VALIDITY_REGEXP.getValue());

        CreateOrUpdatePersonContext createOrUpdateContext = new CreateOrUpdatePersonContext();
        createOrUpdateContext.setMedicalCertificateFileNameInputStream(medicalCertificateStream);
        createOrUpdateContext.setMedicalCertificateBeginValidityDate(validity);
        return createOrUpdateContext;
    }

    public static CreateOrUpdatePersonContext create(InputStream photoFileStream, FormDataContentDisposition photoFileNameDetail) {
        checkContentType(PHOTO_TYPE, photoFileNameDetail.getFileName());
        checkRequestBodyField(photoFileStream, BODY_FIELD_PHOTO_FILENAME);
        CreateOrUpdatePersonContext createOrUpdateContext = new CreateOrUpdatePersonContext();
        createOrUpdateContext.setPhotoFileNameInputStream(photoFileStream);
        return createOrUpdateContext;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Formation> getFormations() {
        return formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }

    public byte[] getMedicalCertificateFileNameInputStream() {
        return medicalCertificateFileNameInputStream;
    }

    public void setMedicalCertificateFileNameInputStream(InputStream medicalCertificateFileNameInputStream) {
        try {
            this.medicalCertificateFileNameInputStream = IOUtils.toByteArray(medicalCertificateFileNameInputStream);
        } catch (Exception e) {
            throw new TechnicalException(INVALID_BODY_FIELD, e, BODY_FIELD_MEDICAL_CERTIFICATE, e.getMessage());
        }
    }

    public LocalDate getMedicalCertificateBeginValidityDate() {
        return medicalCertificateBeginValidityDate;
    }

    public void setMedicalCertificateBeginValidityDate(String medicalCertificateBeginValidityDate) {
        this.medicalCertificateBeginValidityDate = parseLocalDate(medicalCertificateBeginValidityDate);
    }

    public byte[] getPhotoFileNameInputStream() {
        return photoFileNameInputStream;
    }

    public void setPhotoFileNameInputStream(InputStream photoFileNameInputStream) {
        try {
            this.photoFileNameInputStream = IOUtils.toByteArray(photoFileNameInputStream);
        } catch (Exception e) {
            throw new TechnicalException(INVALID_BODY_FIELD, e, BODY_FIELD_PHOTO, e.getMessage());
        }
    }
}
