package com.boschat.sikb.service;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.context.CreateOrUpdatePersonContext;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.model.DocumentType;
import com.boschat.sikb.model.MedicalCertificate;
import com.boschat.sikb.model.Photo;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Person;
import org.apache.commons.lang3.StringUtils;

import java.net.URLConnection;
import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.FILE_EXTENSION_NOT_AUTHORIZED;
import static com.boschat.sikb.common.configuration.ResponseCode.FILE_EXTENSION_NOT_SUPPORTED;
import static com.boschat.sikb.common.configuration.ResponseCode.PERSON_NOT_FOUND;
import static com.boschat.sikb.model.DocumentType.MEDICAL_CERTIFICATE_TYPE;
import static com.boschat.sikb.model.DocumentType.PHOTO_TYPE;
import static com.boschat.sikb.utils.HashUtils.generateToken;
import static com.boschat.sikb.utils.JsonUtils.formationsToJsonNode;

public class PersonUtils {

    private PersonUtils() {

    }

    public static Person updatePerson() {
        return savePerson(true);
    }

    public static void deletePerson() {
        DAOFactory.getInstance().getPersonDAO().delete(getPerson());
    }

    public static Person createPerson() {
        return savePerson(false);
    }

    private static Person savePerson(boolean isModification) {
        CreateOrUpdatePersonContext createContext = MyThreadLocal.get().getCreateOrUpdatePersonContext();
        Person personBean;
        if (isModification) {
            personBean = getPerson();
        } else {
            personBean = new Person();
        }

        if (createContext.getFirstName() != null) {
            personBean.setFirstname(createContext.getFirstName());
        }
        if (createContext.getName() != null) {
            personBean.setName(createContext.getName());
        }
        if (createContext.getSex() != null) {
            personBean.setSex(createContext.getSex().toString());
        }
        if (createContext.getBirthDate() != null) {
            personBean.setBirthdate(createContext.getBirthDate());
        }
        if (createContext.getAddress() != null) {
            personBean.setAddress(createContext.getAddress());
        }
        if (createContext.getPostalCode() != null) {
            personBean.setPostalcode(createContext.getPostalCode());
        }
        if (createContext.getCity() != null) {
            personBean.setCity(createContext.getCity());
        }
        if (createContext.getPhoneNumber() != null) {
            personBean.setPhonenumber(createContext.getPhoneNumber());
        }
        if (createContext.getEmail() != null) {
            personBean.setEmail(createContext.getEmail());
        }
        if (createContext.getNationality() != null) {
            personBean.setNationality(createContext.getNationality());
        }
        if (createContext.getFormations() != null) {
            personBean.setFormations(formationsToJsonNode(createContext.getFormations()));
        }

        if (isModification) {
            DAOFactory.getInstance().getPersonDAO().update(personBean);
        } else {
            DAOFactory.getInstance().getPersonDAO().insert(personBean);
        }

        return personBean;
    }

    public static Person getPerson() {
        Integer personId = MyThreadLocal.get().getPersonId();
        Person person = DAOFactory.getInstance().getPersonDAO().fetchOneById(personId);

        if (person == null) {
            throw new FunctionalException(PERSON_NOT_FOUND, personId);
        }
        return person;
    }

    public static List<Person> findPersons() {
        return DAOFactory.getInstance().getPersonDAO().findAll();
    }

    public static void checkPersonExists(Integer personId) {
        if (!DAOFactory.getInstance().getPersonDAO().existsById(personId)) {
            throw new FunctionalException(PERSON_NOT_FOUND, personId);
        }
    }

    public static MedicalCertificate createMedicalCertificate() {
        Person person = getPerson();
        CreateOrUpdatePersonContext context = MyThreadLocal.get().getCreateOrUpdatePersonContext();
        person.setMedicalcertificatedata(context.getMedicalCertificateFileNameInputStream());
        person.setMedicalcertificatebeginvaliditydate(context.getMedicalCertificateBeginValidityDate());
        person.setMedicalcertificatekey(generateToken());

        DAOFactory.getInstance().getPersonDAO().update(person);

        MedicalCertificate medicalCertificate = new MedicalCertificate();
        medicalCertificate.setLocation(MEDICAL_CERTIFICATE_TYPE.buildUrl(person.getMedicalcertificatekey()));
        medicalCertificate.setBeginValidityDate(person.getMedicalcertificatebeginvaliditydate());
        return medicalCertificate;
    }

    public static Photo createPhoto() {
        Person person = getPerson();

        person.setPhotodata(MyThreadLocal.get().getCreateOrUpdatePersonContext().getPhotoFileNameInputStream());
        person.setPhotokey(generateToken());
        DAOFactory.getInstance().getPersonDAO().update(person);

        Photo photo = new Photo();
        photo.setLocation(PHOTO_TYPE.buildUrl(person.getPhotokey()));
        return photo;
    }

    public static void checkContentType(DocumentType documentType, String fileName) {
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
        if (StringUtils.isEmpty(contentType)) {
            throw new FunctionalException(FILE_EXTENSION_NOT_SUPPORTED, fileName);
        }
        if (!documentType.isAuthorized(contentType)) {
            throw new FunctionalException(FILE_EXTENSION_NOT_AUTHORIZED, contentType, fileName);
        }
    }
}
