package com.boschat.sikb.persistence;

import com.boschat.sikb.common.exceptions.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_DB;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_HOST;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_PASSWORD;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_PORT;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_USER;
import static com.boschat.sikb.common.configuration.ResponseCode.DATABASE_ERROR;

public class DAOFactory {

    private static final Logger LOGGER = LogManager.getLogger(DAOFactory.class);

    private static DAOFactory instance = null;

    private AffiliationDAOExtended affiliationDAO = null;

    private ClubDAOExtended clubDAO = null;

    private UserDAOExtended userDAO = null;

    private ApplicationDAOExtended applicationDAO = null;

    private Configuration configuration;

    private DSLContext dslContext;

    private DAOFactory() throws Throwable {
        String URL = "jdbc:postgresql://" + POSTGRES_HOST.getValue() + ':' + POSTGRES_PORT.getValue() + "/" + POSTGRES_DB.getValue();
        LOGGER.trace(URL);
        Connection connection = DriverManager.getConnection(URL, POSTGRES_USER.getValue(), POSTGRES_PASSWORD.getValue());
        configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES);
        dslContext = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public static DAOFactory getInstance() {
        if (instance == null) {
            try {
                instance = new DAOFactory();
            } catch (Throwable e) {
                throw new TechnicalException(DATABASE_ERROR, e);
            }
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public AffiliationDAOExtended getAffiliationDAO() {
        if (affiliationDAO == null) {
            affiliationDAO = new AffiliationDAOExtended(configuration);
        }
        return affiliationDAO;
    }

    public ClubDAOExtended getClubDAO() {
        if (clubDAO == null) {
            clubDAO = new ClubDAOExtended(configuration);
        }
        return clubDAO;
    }

    public UserDAOExtended getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOExtended(configuration);
        }
        return userDAO;
    }

    public ApplicationDAOExtended getApplicationDAO() {
        if (applicationDAO == null) {
            applicationDAO = new ApplicationDAOExtended(configuration);
        }
        return applicationDAO;
    }

    public DSLContext getDslContext() {
        return dslContext;
    }
}
