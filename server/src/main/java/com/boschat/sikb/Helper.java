package com.boschat.sikb;

import com.boschat.sikb.api.CallType;
import com.boschat.sikb.api.ResponseCode;
import com.boschat.sikb.exceptions.FunctionalException;
import com.boschat.sikb.exceptions.TechnicalException;
import com.boschat.sikb.model.ZError;
import com.boschat.sikb.persistence.DAOFactory;
import com.boschat.sikb.tables.pojos.Affiliation;
import com.boschat.sikb.tables.pojos.Club;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static com.boschat.sikb.api.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.api.ResponseCode.INTERNAL_ERROR;

public class Helper {

    private static final Logger LOGGER = LogManager.getLogger(Helper.class);

    public static Response runService(CallType callType, Object... params) {
        Response response = null;
        try {
            MyThreadLocal.init(callType);

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
        }
        return response;
    }

    private static void finallyLog(Response response) {
        LOGGER.info("Call \"{}\". with response code {}", MyThreadLocal.get().getCallType().getInfoLogMessage(), response.getStatus());
    }

    private static Response logAndBuildFunctionalErrorResponse(FunctionalException e) {
        LOGGER.error(e.getMessage());
        ZError error = new ZError();
        error.setCode(e.getErrorCode().getCode());
        error.setMessage(e.getMessage());
        return buildResponse(e.getErrorCode(), error);
    }

    private static Response logAndBuildTechnicalExceptionErrorResponse(Throwable e, ResponseCode errorCode) {
        LOGGER.error(e.getMessage(), e);
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

    public static Affiliation createAffiliation() {
        CreateOrUpdateAffiliationContext createContext = MyThreadLocal.get().getCreateOrUpdateAffiliationContext();

        Affiliation affiliationBean = new Affiliation();
        affiliationBean.setAssociationname(createContext.getAssociationName());
        DAOFactory.getInstance().getAffiliationDAO().insert(affiliationBean);
        return affiliationBean;
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

    public static Club UpdateClub() {
        CreateOrUpdateClubContext createContext = MyThreadLocal.get().getCreateOrUpdateClubContext();

        Club clubToUpdate = getClub();

        if (createContext.getName() != null) {
            clubToUpdate.setName(createContext.getName());
        }
        if (createContext.getShortName() != null) {
            clubToUpdate.setShortname(createContext.getShortName());
        }
        if (createContext.getLogo() != null) {
            clubToUpdate.setLogo(createContext.getLogo());
        }

        DAOFactory.getInstance().getClubDAO().update(clubToUpdate);

        return clubToUpdate;
    }

    public static void deleteClub() {
        DAOFactory.getInstance().getClubDAO().delete(getClub());
    }

    public static Club createClub() {
        CreateOrUpdateClubContext createContext = MyThreadLocal.get().getCreateOrUpdateClubContext();

        Club clubBean = new Club();
        clubBean.setName(createContext.getName());
        clubBean.setShortname(createContext.getShortName());
        clubBean.setLogo(createContext.getLogo());
        DAOFactory.getInstance().getClubDAO().insert(clubBean);
        return clubBean;
    }

    public static com.boschat.sikb.model.Affiliation convertBeanToModel(Affiliation affiliationBean) {
        com.boschat.sikb.model.Affiliation affiliation = new com.boschat.sikb.model.Affiliation();
        affiliation.setId(affiliationBean.getId());
        affiliation.setAssociationName(affiliationBean.getAssociationname());
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
}
