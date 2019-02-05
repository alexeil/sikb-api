package com.boschat.sikb.service;

import com.boschat.sikb.common.exceptions.FunctionalException;
import com.boschat.sikb.context.MyThreadLocal;
import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Licence;

import java.util.List;

import static com.boschat.sikb.common.configuration.ResponseCode.LICENCE_NOT_FOUND;
import static com.boschat.sikb.service.ClubUtils.checkClubExists;
import static com.boschat.sikb.service.PersonUtils.checkPersonExists;
import static com.boschat.sikb.service.SeasonUtils.checkSeasonExists;
import static com.boschat.sikb.utils.JsonUtils.formationsNeedToJsonNode;
import static com.boschat.sikb.utils.JsonUtils.licenceTypesToJsonNode;

public class LicenceUtils {

    private LicenceUtils() {

    }

    public static Licence updatePerson() {
        return saveLicence(true);
    }

    public static Licence createLicence() {
        return saveLicence(false);
    }

    private static Licence saveLicence(boolean isModification) {
        com.boschat.sikb.model.LicenceForCreation licenceForCreation = MyThreadLocal.get().getLicenceForCreation();
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
        }

        if (licenceForCreation.getTypeLicences() != null) {
            licenceBean.setTypes(licenceTypesToJsonNode(licenceForCreation.getTypeLicences()));
        }
        if (licenceForCreation.getFormationNeed() != null) {
            licenceBean.setFormationsneed(formationsNeedToJsonNode(licenceForCreation.getFormationNeed()));
        }
        if (licenceForCreation.getMedicalCertificate() != null) {
            licenceBean.setMedicalcertificate(licenceForCreation.getMedicalCertificate());
        }

        if (isModification) {
            DAOFactory.getInstance().getLicenceDAO().update(licenceBean);
        } else {
            DAOFactory.getInstance().getLicenceDAO().insert(licenceBean);
        }

        return licenceBean;
    }

    public static Licence getLicence() {
        Integer licenceId = MyThreadLocal.get().getLicenceId();
        Licence licence = DAOFactory.getInstance().getLicenceDAO().fetchOneById(licenceId);

        if (licence == null) {
            throw new FunctionalException(LICENCE_NOT_FOUND, licenceId);
        }
        return licence;
    }

    public static void deleteLicence() {
        DAOFactory.getInstance().getLicenceDAO().delete(getLicence());
    }

    public static List<Licence> findLicences() {
        return DAOFactory.getInstance().getLicenceDAO().findAll();
    }
}
