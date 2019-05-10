package com.boschat.sikb.persistence.dao;

import com.boschat.sikb.common.exceptions.TechnicalException;
import com.boschat.sikb.persistence.listener.AuditRecordListener;
import com.boschat.sikb.tables.daos.ClubDao;
import com.boschat.sikb.tables.daos.FormationtypeDao;
import com.boschat.sikb.tables.daos.LicencetypeDao;
import com.boschat.sikb.tables.daos.PersonDao;
import com.boschat.sikb.tables.daos.ProfiletypeDao;
import com.boschat.sikb.tables.daos.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultRecordListenerProvider;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.boschat.sikb.Sequences.AFFILIATION_ID_SEQ;
import static com.boschat.sikb.Sequences.CLUB_ID_SEQ;
import static com.boschat.sikb.Sequences.LICENCE_ID_SEQ;
import static com.boschat.sikb.Sequences.PERSON_ID_SEQ;
import static com.boschat.sikb.Sequences.TEAM_ID_SEQ;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_DB;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_HOST;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_PASSWORD;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_PORT;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_USER;
import static com.boschat.sikb.common.configuration.ResponseCode.DATABASE_ERROR;
import static com.boschat.sikb.tables.Affiliation.AFFILIATION;
import static com.boschat.sikb.tables.Club.CLUB;
import static com.boschat.sikb.tables.Licence.LICENCE;
import static com.boschat.sikb.tables.Person.PERSON;
import static com.boschat.sikb.tables.Season.SEASON;
import static com.boschat.sikb.tables.Team.TEAM;
import static com.boschat.sikb.tables.User.USER;

public class DAOFactory {

    private static final Logger LOGGER = LogManager.getLogger(DAOFactory.class);

    private static DAOFactory instance = null;

    private AffiliationDAOExtended affiliationDAO = null;

    private ClubDao clubDAO = null;

    private TeamDAOExtended teamDAO = null;

    private UserDao userDAO = null;

    private PersonDao personDAO = null;

    private LicenceDAOExtended licenceDAO = null;

    private SeasonDAOExtended seasonDAO = null;

    private FormationtypeDao formationtypeDao = null;

    private LicencetypeDao licencetypeDao = null;

    private ProfiletypeDao profiletypeDao = null;

    private ApplicationDAOExtended applicationDAO = null;

    private Configuration configuration;

    private DSLContext dslContext;

    private DAOFactory() throws Throwable {
        String URL = "jdbc:postgresql://" + POSTGRES_HOST.getValue() + ':' + POSTGRES_PORT.getValue() + "/" + POSTGRES_DB.getValue();
        LOGGER.trace(URL);
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(URL, POSTGRES_USER.getValue(), POSTGRES_PASSWORD.getValue());
        configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES);
        configuration.set(new DefaultRecordListenerProvider(new AuditRecordListener()));
        dslContext = DSL.using(connection, SQLDialect.POSTGRES);
    }

    public static DAOFactory getInstance() {
        if (instance == null) {
            try {
                instance = new DAOFactory();
            } catch (Throwable e) {
                throw new TechnicalException(DATABASE_ERROR, e, e.getMessage());
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

    public ClubDao getClubDAO() {
        if (clubDAO == null) {
            clubDAO = new ClubDao(configuration);
        }
        return clubDAO;
    }

    public TeamDAOExtended getTeamDAO() {
        if (teamDAO == null) {
            teamDAO = new TeamDAOExtended(configuration);
        }
        return teamDAO;
    }

    public SeasonDAOExtended getSeasonDAO() {
        if (seasonDAO == null) {
            seasonDAO = new SeasonDAOExtended(configuration);
        }
        return seasonDAO;
    }

    public LicenceDAOExtended getLicenceDAO() {
        if (licenceDAO == null) {
            licenceDAO = new LicenceDAOExtended(configuration);
        }
        return licenceDAO;
    }

    public UserDao getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDao(configuration);
        }
        return userDAO;
    }

    public PersonDao getPersonDAO() {
        if (personDAO == null) {
            personDAO = new PersonDao(configuration);
        }
        return personDAO;
    }

    public FormationtypeDao getFormationTypeDAO() {
        if (formationtypeDao == null) {
            formationtypeDao = new FormationtypeDao(configuration);
        }
        return formationtypeDao;
    }

    public LicencetypeDao getLicenceTypeDAO() {
        if (licencetypeDao == null) {
            licencetypeDao = new LicencetypeDao(configuration);
        }
        return licencetypeDao;
    }

    public ProfiletypeDao getProfileTypeDAO() {
        if (profiletypeDao == null) {
            profiletypeDao = new ProfiletypeDao(configuration);
        }
        return profiletypeDao;
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

    private <R extends Record> void truncate(Table<R> table) {
        getDslContext().truncate(table).cascade().execute();
    }

    public void truncateClub() {
        truncate(CLUB);
        DAOFactory.getInstance().getDslContext().alterSequence(CLUB_ID_SEQ).restart().execute();

    }

    public void truncateLicence() {
        truncate(LICENCE);
        DAOFactory.getInstance().getDslContext().alterSequence(LICENCE_ID_SEQ).restart().execute();
    }

    public void truncateAffiliation() {
        truncate(AFFILIATION);
        DAOFactory.getInstance().getDslContext().alterSequence(AFFILIATION_ID_SEQ).restart().execute();
    }

    public void truncateUser() {
        truncate(USER);
    }

    public void truncateSeason() {
        truncate(SEASON);
    }

    public void truncateTeam() {
        truncate(TEAM);
        DAOFactory.getInstance().getDslContext().alterSequence(TEAM_ID_SEQ).restart().execute();
    }

    public void truncatePerson() {
        truncate(PERSON);
        DAOFactory.getInstance().getDslContext().alterSequence(PERSON_ID_SEQ).restart().execute();
    }
}