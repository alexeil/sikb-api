package com.boschat.sikb.service;

import com.boschat.sikb.CreateOrUpdatePersonContext;
import com.boschat.sikb.MyThreadLocal;
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
/*
    public static void resetUserPassword() {
        Reset reset = MyThreadLocal.get().getReset();

        User user = DAOFactory.getInstance().getUserDAO().fetchOneByEmail(reset.getLogin());
        if (user == null) {
            throw new FunctionalException(USER_NOT_FOUND, reset.getLogin());
        }

        user.setResettoken(generateToken());
        user.setResettokenexpirationdate(
            getTimestampFromOffsetDateTime(DateUtils.now().plusDays(RESET_TOKEN_EXPIRATION_DAYS.getIntegerValue())));
        user.setCreationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
        DAOFactory.getInstance().getUserDAO().update(user);

        MailUtils.getInstance().sendResetPasswordEmail(reset.getLogin(), user.getResettoken());
    }

    public static void updateUserPassword() {
        UpdatePassword updatePassword = MyThreadLocal.get().getUpdatePassword();
        User user = MyThreadLocal.get().getCurrentUser();

        String oldPassword = updatePassword.getOldPassword();
        String newPassword = updatePassword.getNewPassword();
        String salt = user.getSalt();

        if (oldPassword.equals(newPassword)) {
            throw new FunctionalException(NEW_PASSWORD_CANNOT_BE_SAME);
        }
        if (HashUtils.isExpectedPassword(oldPassword, salt, user.getPassword())) {
            setSaltAndPassword(user, updatePassword);
            DAOFactory.getInstance().getUserDAO().update(user);
        } else {
            throw new FunctionalException(WRONG_OLD_PASSWORD);
        }
    }

    private static void setSaltAndPassword(User user, UpdatePassword updatePassword) {
        String salt = HashUtils.generateSalt();
        user.setPassword(HashUtils.hash(updatePassword.getNewPassword(), salt));
        user.setSalt(salt);
    }

    public static Session confirmUser() {
        String token = MyThreadLocal.get().getToken();
        UpdatePassword updatePassword = MyThreadLocal.get().getUpdatePassword();
        List<User> users = DAOFactory.getInstance().getUserDAO().fetchByActivationtoken(token);

        if (CollectionUtils.isEmpty(users)) {
            throw new FunctionalException(CONFIRM_TOKEN_NOT_FOUND);
        } else {
            User user = users.get(0);
            boolean isExpired = user.getActivationtokenexpirationdate().before(getTimestampFromOffsetDateTime(DateUtils.now()));

            if (isExpired) {
                throw new FunctionalException(CONFIRM_TOKEN_EXPIRED);
            } else {
                setSaltAndPassword(user, updatePassword);
                user.setEnabled(true);
                user.setModificationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
                user.setActivationtoken(null);
                user.setActivationtokenexpirationdate(null);
                DAOFactory.getInstance().getUserDAO().update(user);
            }
        }
        return null;
    }

    public static void logoutUser() {
        String accessToken = MyThreadLocal.get().getAccessToken();

        List<User> users = DAOFactory.getInstance().getUserDAO().fetchByAccesstoken(accessToken);
        if (CollectionUtils.isNotEmpty(users)) {
            User user = users.get(0);
            user.setActivationtoken(null);
            DAOFactory.getInstance().getUserDAO().update(user);
        }
    }

    public static Session loginUser() {
        Credentials credentials = MyThreadLocal.get().getCredentials();

        User user = DAOFactory.getInstance().getUserDAO().fetchOneByEmail(credentials.getLogin());
        if (user == null || StringUtils.isEmpty(user.getPassword())) {
            throw new FunctionalException(WRONG_LOGIN_OR_PASSWORD);
        }

        boolean isRightPassword = isExpectedPassword(credentials.getPassword(), user.getSalt(), user.getPassword());
        if (isRightPassword) {
            String accessToken = generateToken();
            user.setAccesstoken(accessToken);
            DAOFactory.getInstance().getUserDAO().update(user);
            return new Session().accessToken(accessToken);
        } else {
            throw new FunctionalException(WRONG_LOGIN_OR_PASSWORD);
        }
    }

 



    */
}
