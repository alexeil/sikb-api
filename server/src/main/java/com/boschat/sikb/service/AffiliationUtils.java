package com.boschat.sikb.service;

import com.boschat.sikb.CreateOrUpdateAffiliationContext;
import com.boschat.sikb.MyThreadLocal;
import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Affiliation;

import static com.boschat.sikb.common.configuration.ResponseCode.AFFILIATION_NOT_FOUND;
import static com.boschat.sikb.common.utils.DateUtils.getDateFromLocalDate;

public class AffiliationUtils {

    private AffiliationUtils() {

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
}
