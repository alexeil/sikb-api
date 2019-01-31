package com.boschat.sikb;

import com.boschat.sikb.api.CallType;
import com.boschat.sikb.common.configuration.ResponseCode;
import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.common.exceptions.TechnicalException;
import com.boschat.sikb.model.Board;
import com.boschat.sikb.model.Sex;
import com.boschat.sikb.model.ZError;
import com.boschat.sikb.persistence.DAOFactory;
import com.boschat.sikb.tables.pojos.Affiliation;
import com.boschat.sikb.tables.pojos.Club;
import com.boschat.sikb.tables.pojos.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

import static com.boschat.sikb.common.configuration.ResponseCode.INTERNAL_ERROR;
import static com.boschat.sikb.common.configuration.ResponseCode.UNAUTHORIZED;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_ACCESS_TOKEN;
import static com.boschat.sikb.utils.CheckUtils.checkRequestHeader;
import static com.boschat.sikb.utils.DateUtils.getOffsetDateTimeFromTimestamp;

public class Helper {

    private static final Logger LOGGER = LogManager.getLogger(Helper.class);

    private Helper() {

    }

    public static Response runService(CallType callType, String accessToken, SecurityContext securityContext, Object... params) {
        Response response = null;
        try {
            MyThreadLocal.init(callType, accessToken);

            callType.fillContext(params);

            checkAccessToken(callType, accessToken, securityContext);
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

    private static void checkAccessToken(CallType callType, String accessToken, SecurityContext securityContext) {
        if (callType.isCheckAccessToken()) {
            checkRequestHeader(accessToken, HEADER_ACCESS_TOKEN, null);
            List<User> users = DAOFactory.getInstance().getUserDAO().fetchByAccesstoken(accessToken);
            if (CollectionUtils.isNotEmpty(users)) {
                User user = users.get(0);
                MyThreadLocal.get().setCurrentUser(user);
            } else if (!"admin".equalsIgnoreCase(securityContext.getUserPrincipal().getName()) &&
                !"test".equalsIgnoreCase(securityContext.getUserPrincipal().getName())) {
                throw new FunctionalException(UNAUTHORIZED);
            }
        }
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
