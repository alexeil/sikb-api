package com.boschat.sikb.persistence;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {

    private static final String USERNAME = "postgres";

    private static final String PASSWORD = "postgres";

    private static final String URL = "jdbc:postgresql://localhost:5432/sikb";

    private static AffiliationDAOExtended affiliationDAO = null;

    private static ClubDAOExtended clubDAO = null;

    private static UserDAOExtended userDAO = null;

    private static ApplicationDAOExtended applicationDAO = null;

    private static DAOFactory daoFactory = null;

    private Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

    private Configuration configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES);

    private DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);

    private DAOFactory() throws SQLException {
    }

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            try {
                daoFactory = new DAOFactory();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return daoFactory;
    }

    public Connection getConnection() {
        return connection;
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
