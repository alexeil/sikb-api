package com.boschat.sikb.service;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.context.CreateOrUpdateClubContext;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Club;

import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.CLUB_NOT_FOUND;

public class ClubUtils {

    private ClubUtils() {

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
        } else {
            clubBean = getClub();
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

    public static void checkClubExists() {
        if (!DAOFactory.getInstance().getClubDAO().existsById(MyThreadLocal.get().getClubId())) {
            throw new FunctionalException(CLUB_NOT_FOUND, MyThreadLocal.get().getClubId());
        }
    }

}
