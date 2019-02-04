package com.boschat.sikb.service;

import com.boschat.sikb.context.CreateOrUpdatePersonContext;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Person;

import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.PERSON_NOT_FOUND;
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
}
