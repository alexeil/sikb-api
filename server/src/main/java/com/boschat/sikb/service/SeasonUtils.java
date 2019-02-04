package com.boschat.sikb.service;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.context.CreateOrUpdateSeasonContext;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Season;

import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.SEASON_ALREADY_EXISTS;
import static com.boschat.sikb.common.configuration.ResponseCode.SEASON_NOT_FOUND;

public class SeasonUtils {

    private SeasonUtils() {

    }

    public static Season updateSeason() {
        return saveSeason(true);
    }

    public static void deleteSeason() {
        DAOFactory.getInstance().getSeasonDAO().delete(getSeason(true));
    }

    public static Season createSeason() {
        return saveSeason(false);
    }

    private static Season saveSeason(boolean isModification) {
        CreateOrUpdateSeasonContext createContext = MyThreadLocal.get().getCreateOrUpdateSeasonContext();
        Season seasonBean;
        if (isModification) {
            seasonBean = getSeason(true);
        } else {
            String externalId = createContext.getBegin().getYear() + "" + createContext.getEnd().getYear();
            MyThreadLocal.get().setSeasonId(externalId);
            seasonBean = getSeason(false);
            if (seasonBean != null) {
                throw new FunctionalException(SEASON_ALREADY_EXISTS, seasonBean.getId());
            } else {
                seasonBean = new Season();
                seasonBean.setId(externalId);
            }
        }

        if (createContext.getDescription() != null) {
            seasonBean.setDescription(createContext.getDescription());
        }
        if (createContext.getBegin() != null) {
            seasonBean.setBegin(createContext.getBegin());
        }
        if (createContext.getEnd() != null) {
            seasonBean.setEnd(createContext.getEnd());
        }

        if (isModification) {
            DAOFactory.getInstance().getSeasonDAO().update(seasonBean);
        } else {
            DAOFactory.getInstance().getSeasonDAO().insert(seasonBean);
        }

        return seasonBean;
    }

    private static Season getSeason(boolean raiseNotFound) {
        String seasonId = MyThreadLocal.get().getSeasonId();
        Season season = DAOFactory.getInstance().getSeasonDAO().fetchOneById(seasonId);

        if (raiseNotFound && season == null) {
            throw new FunctionalException(SEASON_NOT_FOUND, seasonId);
        }
        return season;
    }

    public static List<Season> findSeasons() {
        return DAOFactory.getInstance().getSeasonDAO().findAll();
    }

}
