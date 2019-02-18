package com.boschat.sikb.service;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.context.CreateOrUpdateLicenceContext;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Licence;

import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.LICENCE_NOT_FOUND;
import static com.boschat.sikb.service.ClubUtils.checkClubExists;
import static com.boschat.sikb.service.PersonUtils.checkPersonExists;
import static com.boschat.sikb.service.SeasonUtils.checkSeasonExists;

public class LicenceUtils {

    private LicenceUtils() {

    }

    public static Licence updateLicence() {
        return saveLicence(true);
    }

    public static Licence createLicence() {
        return saveLicence(false);
    }

    private static Licence saveLicence(boolean isModification) {
        CreateOrUpdateLicenceContext context = MyThreadLocal.get().getCreateOrUpdateLicenceContext();
        Licence licenceBean;
        if (isModification) {
            licenceBean = getLicence();
        } else {
            licenceBean = new Licence();

            String seasonId = MyThreadLocal.get().getSeasonId();
            Integer clubId = MyThreadLocal.get().getClubId();
            Integer personId = MyThreadLocal.get().getPersonId();

            checkSeasonExists(seasonId);
            licenceBean.setSeason(seasonId);

            checkClubExists(clubId);
            licenceBean.setClubid(clubId);

            checkPersonExists(personId);
            licenceBean.setPersonid(personId);

            licenceBean.setNumber(personId + clubId + seasonId);
        }

        if (context.getTypeLicences() != null) {
            licenceBean.setTypes(context.getTypeLicences());
        }
        if (context.getFormationNeed() != null) {
            licenceBean.setFormationsneed(context.getFormationNeed());
        }

        if (isModification) {
            DAOFactory.getInstance().getLicenceDAO().update(licenceBean);
        } else {
            DAOFactory.getInstance().getLicenceDAO().insert(licenceBean);
        }

        return licenceBean;
    }

    public static Licence getLicence() {
        String licenceId = MyThreadLocal.get().getLicenceId();
        Licence licence = DAOFactory.getInstance().getLicenceDAO().fetchOneByNumber(licenceId);

        if (licence == null) {
            throw new FunctionalException(LICENCE_NOT_FOUND, licenceId);
        }
        return licence;
    }

    public static void deleteLicence() {
        DAOFactory.getInstance().getLicenceDAO().delete(getLicence());
    }

    public static List<Licence> findLicences() {
        String seasonId = MyThreadLocal.get().getSeasonId();
        Integer clubId = MyThreadLocal.get().getClubId();
        Integer personId = MyThreadLocal.get().getPersonId();

        checkSeasonExists(seasonId);
        checkClubExists(clubId);
        checkPersonExists(personId);

        return DAOFactory.getInstance().getLicenceDAO().findAllByPersonIdClubIdSeason(personId, clubId, seasonId);
    }

}
