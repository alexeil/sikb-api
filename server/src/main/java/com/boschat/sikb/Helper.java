package com.boschat.sikb;

import com.boschat.sikb.api.CallType;
import com.boschat.sikb.api.ResponseCode;
import com.boschat.sikb.exceptions.FunctionalException;
import com.boschat.sikb.exceptions.TechnicalException;
import com.boschat.sikb.model.Board;
import com.boschat.sikb.model.Sex;
import com.boschat.sikb.model.ZError;
import com.boschat.sikb.persistence.DAOFactory;
import com.boschat.sikb.tables.pojos.Affiliation;
import com.boschat.sikb.tables.pojos.Club;
import com.boschat.sikb.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static com.boschat.sikb.api.ResponseCode.AFFILIATION_NOT_FOUND;
import static com.boschat.sikb.api.ResponseCode.CLUB_NOT_FOUND;
import static com.boschat.sikb.api.ResponseCode.INTERNAL_ERROR;
import static com.boschat.sikb.utils.DateUtils.getDateFromLocalDate;
import static com.boschat.sikb.utils.DateUtils.getOffsetDateTimeFromTimestamp;
import static com.boschat.sikb.utils.DateUtils.getTimestampFromOffsetDateTime;

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

    public static Affiliation getAffiliation() {
        Integer clubId = MyThreadLocal.get().getClubId();
        String season = MyThreadLocal.get().getSeason();
        Integer affiliationId = MyThreadLocal.get().getAffiliationId();

        Affiliation affiliation = DAOFactory.getInstance().getAffiliationDAO().fetchByIdClubIdSeason(affiliationId, clubId, season);

        if (affiliation == null) {
            throw new FunctionalException(AFFILIATION_NOT_FOUND, affiliationId, clubId, season);
        }
        return affiliation;
    }

    public static Affiliation createAffiliation() {
        CreateOrUpdateAffiliationContext createContext = MyThreadLocal.get().getCreateOrUpdateAffiliationContext();

        Affiliation affiliationBean = new Affiliation();

        affiliationBean.setPrefecturenumber(createContext.getPrefectureNumber());
        affiliationBean.setPrefecturecity(createContext.getPrefectureCity());
        affiliationBean.setSiretnumber(createContext.getSiretNumber());
        affiliationBean.setAddress(createContext.getAddress());
        affiliationBean.setPostalcode(createContext.getPostalCode());
        affiliationBean.setCity(createContext.getCity());
        affiliationBean.setPhonenumber(createContext.getPhoneNumber());
        affiliationBean.setEmail(createContext.getEmail());
        affiliationBean.setWebsite(createContext.getWebSite());

        affiliationBean.setPresident(createContext.getPresident());
        affiliationBean.setPresidentsex(createContext.getPresidentSex().toString());
        affiliationBean.setSecretary(createContext.getSecretary());
        affiliationBean.setSecretarysex(createContext.getSecretarySex().toString());
        affiliationBean.setTreasurer(createContext.getTreasurer());
        affiliationBean.setTreasurersex(createContext.getTreasurerSex().toString());
        affiliationBean.setMembersnumber(createContext.getMembersNumber());
        affiliationBean.setElecteddate(getDateFromLocalDate(createContext.getElectedDate()));

        affiliationBean.setCreationdate(getTimestampFromOffsetDateTime(DateUtils.now()));
        affiliationBean.setSeason(MyThreadLocal.get().getSeason());
        affiliationBean.setClubid(MyThreadLocal.get().getClubId());
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
}
