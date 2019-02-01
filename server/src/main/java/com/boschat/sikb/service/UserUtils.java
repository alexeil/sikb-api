package com.boschat.sikb.service;

import com.boschat.sikb.CreateOrUpdateUserContext;
import com.boschat.sikb.MyThreadLocal;
import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.Reset;
import com.boschat.sikb.model.Session;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.User;
import com.boschat.sikb.utils.HashUtils;
import com.boschat.sikb.utils.MailUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.tools.StringUtils;

import java.util.List;

import static com.boschat.sikb.common.configuration.ApplicationProperties.ACTIVATION_TOKEN_EXPIRATION_DAYS;
import static com.boschat.sikb.common.configuration.ApplicationProperties.RESET_TOKEN_EXPIRATION_DAYS;
import static com.boschat.sikb.common.configuration.ResponseCode.CONFIRM_TOKEN_EXPIRED;
import static com.boschat.sikb.common.configuration.ResponseCode.CONFIRM_TOKEN_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.NEW_PASSWORD_CANNOT_BE_SAME;
import static com.boschat.sikb.common.configuration.ResponseCode.USER_NOT_FOUND;
import static com.boschat.sikb.common.configuration.ResponseCode.WRONG_LOGIN_OR_PASSWORD;
import static com.boschat.sikb.common.configuration.ResponseCode.WRONG_OLD_PASSWORD;
import static com.boschat.sikb.common.utils.DateUtils.now;
import static com.boschat.sikb.common.utils.DateUtils.nowPlusDays;
import static com.boschat.sikb.utils.HashUtils.generateToken;
import static com.boschat.sikb.utils.HashUtils.isExpectedPassword;

public class UserUtils {

    private UserUtils() {

    }

    public static void resetUserPassword() {
        Reset reset = MyThreadLocal.get().getReset();

        User user = DAOFactory.getInstance().getUserDAO().fetchOneByEmail(reset.getLogin());
        if (user == null) {
            throw new FunctionalException(USER_NOT_FOUND, reset.getLogin());
        }

        user.setResettoken(generateToken());
        user.setResettokenexpirationdate(nowPlusDays(RESET_TOKEN_EXPIRATION_DAYS.getIntegerValue()));
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
            boolean isExpired = user.getActivationtokenexpirationdate().isBefore(now());

            if (isExpired) {
                throw new FunctionalException(CONFIRM_TOKEN_EXPIRED);
            } else {
                setSaltAndPassword(user, updatePassword);
                user.setEnabled(true);
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

    public static User getUser() {
        Integer userId = MyThreadLocal.get().getUserId();
        User user = DAOFactory.getInstance().getUserDAO().fetchOneById(userId);

        if (user == null) {
            throw new FunctionalException(USER_NOT_FOUND, userId);
        }
        return user;
    }

    public static List<User> findUsers() {
        return DAOFactory.getInstance().getUserDAO().findAll();
    }

    public static User updateUser() {
        return saveUser(true);
    }

    public static void deleteUser() {
        DAOFactory.getInstance().getUserDAO().delete(getUser());
    }

    public static User createUser() {
        return saveUser(false);
    }

    private static User saveUser(boolean isModification) {
        CreateOrUpdateUserContext createContext = MyThreadLocal.get().getCreateOrUpdateUserContext();
        User userBean;
        if (isModification) {
            userBean = getUser();
        } else {
            userBean = new User();
            userBean.setActivationtoken(generateToken());
            userBean.setActivationtokenexpirationdate(nowPlusDays(ACTIVATION_TOKEN_EXPIRATION_DAYS.getIntegerValue()));
        }

        if (createContext.getEmail() != null) {
            userBean.setEmail(createContext.getEmail());
        }

        if (isModification) {
            DAOFactory.getInstance().getUserDAO().update(userBean);
        } else {
            DAOFactory.getInstance().getUserDAO().insert(userBean);
            MailUtils.getInstance().sendCreateUserEmail(userBean.getEmail(), userBean.getActivationtoken());
        }

        return userBean;
    }
}
