package com.boschat.sikb.service;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.context.CreateOrUpdateAffiliationContext;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Affiliation;

import static com.boschat.sikb.common.configuration.ResponseCode.AFFILIATION_NOT_FOUND;
import static com.boschat.sikb.service.ClubUtils.checkClubExists;
import static com.boschat.sikb.service.SeasonUtils.checkSeasonExists;

public class AffiliationUtils {

    private AffiliationUtils() {

    }

    public static void deleteAffiliation() {
        DAOFactory.getInstance().getAffiliationDAO().delete(getAffiliation());
    }

    public static Affiliation getAffiliation() {
        Integer clubId = MyThreadLocal.get().getClubId();
        String season = MyThreadLocal.get().getSeasonId();

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

            checkSeasonExists();
            checkClubExists();
            affiliationBean.setSeason(MyThreadLocal.get().getSeasonId());
            affiliationBean.setClubid(MyThreadLocal.get().getClubId());

        } else {
            affiliationBean = getAffiliation();
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
            affiliationBean.setPresident(createContext.getPresident().getName());
            if (createContext.getPresident().getSex() != null) {
                affiliationBean.setPresidentsex(createContext.getPresident().getSex().toString());
            }
        }
        if (createContext.getSecretary() != null) {
            affiliationBean.setSecretary(createContext.getSecretary().getName());
            if (createContext.getSecretary().getSex() != null) {
                affiliationBean.setSecretarysex(createContext.getSecretary().getSex().toString());
            }
        }
        if (createContext.getTreasurer() != null) {
            affiliationBean.setTreasurer(createContext.getTreasurer().getName());
            if (createContext.getTreasurer().getSex() != null) {
                affiliationBean.setTreasurersex(createContext.getTreasurer().getSex().toString());
            }
        }
        if (createContext.getMembersNumber() != null) {
            affiliationBean.setMembersnumber(createContext.getMembersNumber());
        }
        if (createContext.getElectedDate() != null) {
            affiliationBean.setElecteddate(createContext.getElectedDate());
        }

        if (isCreation) {
            DAOFactory.getInstance().getAffiliationDAO().insert(affiliationBean);
        } else {
            DAOFactory.getInstance().getAffiliationDAO().update(affiliationBean);
        }

        return affiliationBean;
    }
}
