package com.boschat.sikb.service;

import com.boschat.sikb.persistence.dao.DAOFactory;
import com.boschat.sikb.tables.pojos.Formationtype;
import com.boschat.sikb.tables.pojos.Licencetype;
import com.boschat.sikb.tables.pojos.Profiletype;

import java.util.List;

public class ConfigurationUtils {

    private ConfigurationUtils() {

    }

    public static List<Formationtype> findFormationTypes() {
        return DAOFactory.getInstance().getFormationTypeDAO().findAll();
    }

    public static List<Licencetype> findLicenceTypes() {
        return DAOFactory.getInstance().getLicenceTypeDAO().findAll();
    }

    public static List<Profiletype> findProfileTypes() {
        return DAOFactory.getInstance().getProfileTypeDAO().findAll();
    }
}
