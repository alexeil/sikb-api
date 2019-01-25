package com.boschat.sikb;

import com.boschat.sikb.api.CallType;
import com.boschat.sikb.api.ResponseCode;
import com.boschat.sikb.exceptions.FunctionalException;
import com.boschat.sikb.exceptions.TechnicalException;
import com.boschat.sikb.model.Board;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.Session;
import com.boschat.sikb.model.Sex;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.model.ZError;
import com.boschat.sikb.persistence.DAOFactory;
import com.boschat.sikb.tables.pojos.Affiliation;
import com.boschat.sikb.tables.pojos.Club;
import com.boschat.sikb.tables.pojos.User;
import com.boschat.sikb.utils.DateUtils;
import com.boschat.sikb.utils.HashUtils;
import com.boschat.sikb.utils.MailUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.tools.StringUtils;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static com.boschat.sikb.api.ResponseCode.AFFILIATION_NOT_FOUND;
import static com.boschat.sikb.api.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.api.ResponseCode.CONFIRM_TOKEN_EXPIRED;
import static com.boschat.sikb.api.ResponseCode.CONFIRM_TOKEN_NOT_FOUND;
import static com.boschat.sikb.api.ResponseCode.INTERNAL_ERROR;
import static com.boschat.sikb.api.ResponseCode.USER_NOT_FOUND;
import static com.boschat.sikb.api.ResponseCode.WRONG_LOGIN_OR_PASSWORD;
import static com.boschat.sikb.configuration.ApplicationProperties.ACTIVATION_TOKEN_EXPIRATION_DAYS;
import static com.boschat.sikb.utils.DateUtils.getDateFromLocalDate;
import static com.boschat.sikb.utils.DateUtils.getOffsetDateTimeFromTimestamp;
import static com.boschat.sikb.utils.DateUtils.getTimestampFromOffsetDateTime;
import static com.boschat.sikb.utils.HashUtils.generateToken;
import static com.boschat.sikb.utils.HashUtils.isExpectedPassword;

public class Helper {

    private static final Logger LOGGER = LogManager.getLogger(Helper.class);

    private Helper() {

    }

    public static Response runService(CallType callType, String accessToken, Object... params) {
        Response response = null;
        try {
            MyThreadLocal.init(callType, accessToken);

            callType.fillContext(params);
            response = buildResponse(callType.getResponseCode(), callType.call());

        } catch (FunctionalException e) {
            response = logAndBuildFunctionalErrorResponse(e);
        } catch (TechnicalException e) {
            response = logAndBuildTechnicalExceptionErrorResponse(e, e.getErrorCode());
        } catch (Throwable e) {
            response = logAndBuildTechnicalExceptionErrorResponse(e, INTERNAL_ERROR);
        } finally {
            // always log an info log for each service called
            finallyLog(response);
            MyThreadLocal.unset();
        }
        return response;
    }

    private static void finallyLog(Response response) {
        LOGGER.info("Call \"{}\". with response code {}", MyThreadLocal.get().getCallType().getInfoLogMessage(), response.getStatus());
    }

    private static Response logAndBuildFunctionalErrorResponse(FunctionalException e) {
        LOGGER.log(e.getErrorCode().getLevel(), e.getMessage());
        ZError error = new ZError();
        error.setCode(e.getErrorCode().getCode());
        error.setMessage(e.getMessage());
        return buildResponse(e.getErrorCode(), error);
    }

    private static Response logAndBuildTechnicalExceptionErrorResponse(Throwable e, ResponseCode errorCode) {
        LOGGER.log(errorCode.getLevel(), e.getMessage(), e);
        ZError error = new ZError();
        error.setCode(errorCode.getCode());
        error.setMessage(e.getMessage());
        return buildResponse(errorCode, error);
    }

    private static Response buildResponse(ResponseCode code, Object entity) {
        Response.ResponseBuilder responseBuilder = Response.status(code.getCodeHttp());
        if (null != entity) {
            responseBuilder.entity(entity);
        }
        return responseBuilder.build();
    }

    public static void deleteAffiliation() {
        DAOFactory.getInstance().getAffiliationDAO().delete(getAffiliation());
    }

    public static Affiliation getAffiliation() {
        Integer clubId = MyThreadLocal.get().getClubId();
        String season = MyThreadLocal.get().getSeason();

        Affiliation affiliation = DAOFactory.getInstance().getAffiliationDAO().fetchByIdClubIdSeason(clubId, season);

        if (affiliation == null) {
            throw new FunctionalException(AFFILIATION_NOT_FOUND, clubId, season);
        }
        return affiliation;
    }

    public static Affiliation updateAffiliation() {
        return createOrUpdateAffiliation(false);
    }

    public static Affiliation createAffiliation() {
        return createOrUpdateAffiliation(true);
    }

    private static Affiliation createOrUpdateAffiliation(boolean isCreation) {
        CreateOrUpdateAffiliationContext createContext = MyThreadLocal.get().getCreateOrUpdateAffiliationContext();

        Affiliation affiliationBean;
        if (isCreation) {
            affiliationBean = new Affiliation();
            affiliationBean.setSeason(MyThreadLocal.get().getSeason());
            affiliationBean.setClubid(MyThreadLocal.get().getClubId());
            affiliationBean.setCreationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
        } else {
            affiliationBean = getAffiliation();
            affiliationBean.setModificationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
        }

        if (createContext.getPrefectureNumber() != null) {
            affiliationBean.setPrefecturenumber(createContext.getPrefectureNumber());
        }
        if (createContext.getPrefectureCity() != null) {
            affiliationBean.setPrefecturecity(createContext.getPrefectureCity());
        }
        if (createContext.getSiretNumber() != null) {
            affiliationBean.setSiretnumber(createContext.getSiretNumber());
        }
        if (createContext.getAddress() != null) {
            affiliationBean.setAddress(createContext.getAddress());
        }
        if (createContext.getPostalCode() != null) {
            affiliationBean.setPostalcode(createContext.getPostalCode());
        }
        if (createContext.getCity() != null) {
            affiliationBean.setCity(createContext.getCity());
        }
        if (createContext.getPhoneNumber() != null) {
            affiliationBean.setPhonenumber(createContext.getPhoneNumber());
        }
        if (createContext.getEmail() != null) {
            affiliationBean.setEmail(createContext.getEmail());
        }
        if (createContext.getWebSite() != null) {
            affiliationBean.setWebsite(createContext.getWebSite());
        }

        if (createContext.getPresident() != null) {
            affiliationBean.setPresident(createContext.getPresident());
        }
        if (createContext.getPresidentSex() != null) {
            affiliationBean.setPresidentsex(createContext.getPresidentSex().toString());
        }
        if (createContext.getSecretary() != null) {
            affiliationBean.setSecretary(createContext.getSecretary());
        }
        if (createContext.getSecretarySex() != null) {
            affiliationBean.setSecretarysex(createContext.getSecretarySex().toString());
        }
        if (createContext.getTreasurer() != null) {
            affiliationBean.setTreasurer(createContext.getTreasurer());
        }
        if (createContext.getTreasurerSex() != null) {
            affiliationBean.setTreasurersex(createContext.getTreasurerSex().toString());
        }
        if (createContext.getMembersNumber() != null) {
            affiliationBean.setMembersnumber(createContext.getMembersNumber());
        }
        if (createContext.getElectedDate() != null) {
            affiliationBean.setElecteddate(getDateFromLocalDate(createContext.getElectedDate()));
        }

        if (isCreation) {
            DAOFactory.getInstance().getAffiliationDAO().insert(affiliationBean);
        } else {
            DAOFactory.getInstance().getAffiliationDAO().update(affiliationBean);
        }

        return affiliationBean;
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
                String salt = HashUtils.generateSalt();
                user.setPassword(HashUtils.hash(updatePassword.getNewPassword(), salt));
                user.setSalt(salt);
                user.setEnabled(true);
                user.setModificationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
                user.setActivationtoken(null);
                user.setActivationtokenexpirationdate(null);
                DAOFactory.getInstance().getUserDAO().update(user);
            }
        }
        return null;
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
            userBean.setModificationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
        } else {
            userBean = new User();
            userBean.setActivationtoken(generateToken());
            userBean.setActivationtokenexpirationdate(
                getTimestampFromOffsetDateTime(DateUtils.now().plusDays(ACTIVATION_TOKEN_EXPIRATION_DAYS.getIntegerValue())));
            userBean.setCreationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
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

    public static Club getClub() {
        Integer clubId = MyThreadLocal.get().getClubId();
        Club club = DAOFactory.getInstance().getClubDAO().fetchOneById(clubId);

        if (club == null) {
            throw new FunctionalException(CLUB_NOT_FOUND, clubId);
        }
        return club;
    }

    public static List<Club> findClubs() {
        return DAOFactory.getInstance().getClubDAO().findAll();
    }

    public static Club updateClub() {
        return saveClub(false);
    }

    public static void deleteClub() {
        DAOFactory.getInstance().getClubDAO().delete(getClub());
    }

    public static Club createClub() {
        return saveClub(true);
    }

    private static Club saveClub(boolean isModification) {
        CreateOrUpdateClubContext createContext = MyThreadLocal.get().getCreateOrUpdateClubContext();
        Club clubBean;
        if (isModification) {
            clubBean = new Club();
            clubBean.setCreationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
        } else {
            clubBean = getClub();
            clubBean.setModificationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
        }

        if (createContext.getName() != null) {
            clubBean.setName(createContext.getName());
        }
        if (createContext.getShortName() != null) {
            clubBean.setShortname(createContext.getShortName());
        }
        if (createContext.getLogo() != null) {
            clubBean.setLogo(createContext.getLogo());
        }

        if (isModification) {
            DAOFactory.getInstance().getClubDAO().insert(clubBean);
        } else {
            DAOFactory.getInstance().getClubDAO().update(clubBean);
        }

        return clubBean;
    }

    public static com.boschat.sikb.model.Affiliation convertBeanToModel(Affiliation affiliationBean) {
        com.boschat.sikb.model.Affiliation affiliation = new com.boschat.sikb.model.Affiliation();
        affiliation.setId(affiliationBean.getId());

        affiliation.setPrefectureNumber(affiliationBean.getPrefecturenumber());
        affiliation.setPrefectureCity(affiliationBean.getPrefecturecity());
        affiliation.setSiretNumber(affiliationBean.getSiretnumber());
        affiliation.setAddress(affiliationBean.getAddress());
        affiliation.setPostalCode(affiliationBean.getPostalcode());
        affiliation.setCity(affiliationBean.getCity());
        affiliation.setPhoneNumber(affiliationBean.getPhonenumber());
        affiliation.setEmail(affiliationBean.getEmail());
        affiliation.setWebSite(affiliationBean.getWebsite());

        if (affiliationBean.getPresident() != null) {
            Board board = new Board();
            board.setPresident(affiliationBean.getPresident());
            board.setPresidentSex(Sex.fromValue(affiliationBean.getPresidentsex()));
            board.setSecretary(affiliationBean.getSecretary());
            board.setSecretarySex(Sex.fromValue(affiliationBean.getSecretarysex()));
            board.setTreasurer(affiliationBean.getTreasurer());
            board.setTreasurerSex(Sex.fromValue(affiliationBean.getTreasurersex()));
            board.setMembersNumber(affiliationBean.getMembersnumber());
            board.setElectedDate(affiliationBean.getElecteddate().toLocalDate());
            affiliation.setBoard(board);
        }
        affiliation.setCreationDateTime(getOffsetDateTimeFromTimestamp(affiliationBean.getCreationdate()));
        affiliation.setModificationDateTime(getOffsetDateTimeFromTimestamp(affiliationBean.getModificationdate()));

        return affiliation;
    }

    public static List<com.boschat.sikb.model.Club> convertBeansToModels(List<Club> clubBeans) {
        return clubBeans.stream().map(Helper::convertBeanToModel).collect(Collectors.toList());
    }

    public static com.boschat.sikb.model.Club convertBeanToModel(Club clubBean) {
        com.boschat.sikb.model.Club club = new com.boschat.sikb.model.Club();
        club.setId(clubBean.getId());
        club.setName(clubBean.getName());
        club.setShortName(clubBean.getShortname());
        club.setLogo(clubBean.getLogo());
        return club;
    }

    public static List<com.boschat.sikb.model.User> convertUserBeansToModels(List<User> userBeans) {
        return userBeans.stream().map(Helper::convertBeanToModel).collect(Collectors.toList());
    }

    public static com.boschat.sikb.model.User convertBeanToModel(User userBean) {
        com.boschat.sikb.model.User user = new com.boschat.sikb.model.User();
        user.setId(userBean.getId());
        user.setEmail(userBean.getEmail());
        return user;
    }
}
