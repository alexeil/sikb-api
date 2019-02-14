package com.boschat.sikb;

import com.boschat.sikb.persistence.dao.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Loader;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;

import java.io.IOException;
import java.net.URL;

import static com.boschat.sikb.Sequences.AFFILIATION_ID_SEQ;
import static com.boschat.sikb.Sequences.CLUB_ID_SEQ;
import static com.boschat.sikb.Sequences.LICENCE_ID_SEQ;
import static com.boschat.sikb.Sequences.PERSON_ID_SEQ;
import static com.boschat.sikb.Sequences.USER_ID_SEQ;
import static com.boschat.sikb.Tables.AFFILIATION;
import static com.boschat.sikb.Tables.LICENCE;
import static com.boschat.sikb.Tables.PERSON;
import static com.boschat.sikb.Tables.SEASON;
import static com.boschat.sikb.Tables.USER;
import static com.boschat.sikb.tables.Club.CLUB;

public class PersistenceUtils {

    private static final Logger LOGGER = LogManager.getLogger(PersistenceUtils.class);

    private PersistenceUtils() {

    }

    public static void loadUsers() throws IOException {
        DAOFactory.getInstance().truncateUser();
        DAOFactory.getInstance().getDslContext().alterSequence(USER_ID_SEQ).restart().execute();
        loadDataSuite("sql/insertUser.csv", USER, USER.EMAIL, USER.PASSWORD, USER.SALT, USER.INFORMATION, USER.ACTIVATIONTOKEN,
            USER.ACTIVATIONTOKENEXPIRATIONDATE, USER.ACCESSTOKEN, USER.ENABLED, USER.CREATIONDATE, USER.MODIFICATIONDATE);
    }

    public static void loadClubs(String fileName) throws IOException {
        loadDataSuite(fileName, CLUB, CLUB.NAME, CLUB.SHORTNAME, CLUB.LOGO, CLUB.CREATIONDATE);
    }

    public static void loadLicences(String fileName) throws IOException {
        loadDataSuite(fileName, LICENCE, LICENCE.NUMBER, LICENCE.FORMATIONSNEED, LICENCE.TYPES, LICENCE.SEASON, LICENCE.CLUBID, LICENCE.PERSONID);
    }

    public static void loadClubs() throws IOException {
        DAOFactory.getInstance().truncateClub();
        DAOFactory.getInstance().getDslContext().alterSequence(CLUB_ID_SEQ).restart().execute();
        loadClubs("sql/insertClub.csv");
    }

    public static void loadLicences() throws IOException {
        DAOFactory.getInstance().truncateLicence();
        DAOFactory.getInstance().getDslContext().alterSequence(LICENCE_ID_SEQ).restart().execute();
        loadLicences("sql/insertLicence.csv");
    }

    public static void loadSeasons() throws IOException {
        DAOFactory.getInstance().truncateSeason();
        loadDataSuite("sql/insertSeason.csv", SEASON, SEASON.ID, SEASON.DESCRIPTION, SEASON.BEGIN, SEASON.END);
    }

    public static void loadAffiliations() throws IOException {
        DAOFactory.getInstance().truncateAffiliation();
        DAOFactory.getInstance().getDslContext().alterSequence(AFFILIATION_ID_SEQ).restart().execute();
        loadAffiliations("sql/insertAffiliation.csv");
    }

    public static void loadAffiliations(String fileName) throws IOException {
        loadDataSuite(fileName, AFFILIATION, AFFILIATION.PREFECTURENUMBER, AFFILIATION.PREFECTURECITY,
            AFFILIATION.SIRETNUMBER,
            AFFILIATION.ADDRESS,
            AFFILIATION.POSTALCODE, AFFILIATION.CITY, AFFILIATION.PHONENUMBER, AFFILIATION.EMAIL, AFFILIATION.WEBSITE, AFFILIATION.PRESIDENT,
            AFFILIATION.PRESIDENTSEX, AFFILIATION.SECRETARY, AFFILIATION.SECRETARYSEX, AFFILIATION.TREASURER, AFFILIATION.TREASURERSEX,
            AFFILIATION.MEMBERSNUMBER, AFFILIATION.ELECTEDDATE, AFFILIATION.CREATIONDATE, AFFILIATION.MODIFICATIONDATE, AFFILIATION.SEASON,
            AFFILIATION.CLUBID);

    }

    public static void loadPersons() throws IOException {
        DAOFactory.getInstance().truncatePerson();
        DAOFactory.getInstance().getDslContext().alterSequence(PERSON_ID_SEQ).restart().execute();
        loadPersons("sql/insertPerson.csv");
    }

    public static void loadPersons(String fileName) throws IOException {
        loadDataSuite(fileName, PERSON, PERSON.FIRSTNAME, PERSON.NAME, PERSON.SEX, PERSON.BIRTHDATE, PERSON.ADDRESS, PERSON.POSTALCODE, PERSON.CITY,
            PERSON.PHONENUMBER, PERSON.EMAIL, PERSON.NATIONALITY, PERSON.FORMATIONS, PERSON.PHOTODATA, PERSON.MEDICALCERTIFICATEKEY,
            PERSON.MEDICALCERTIFICATEDATA, PERSON.MEDICALCERTIFICATEBEGINVALIDITYDATE, PERSON.CREATIONDATE,
            PERSON.MODIFICATIONDATE);
    }

    private static <T extends TableImpl> void loadDataSuite(String resourcePath, T clazz, TableField... fields) throws IOException {
        URL url = PersistenceUtils.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            LOGGER.error("resourcePath notFound : " + resourcePath);
        } else {
            Loader loader = DAOFactory.getInstance().getDslContext()
                                      .loadInto(clazz)
                                      .loadCSV(url.openStream())
                                      .fields(fields)
                                      .separator(';')
                                      .execute();

            int processed = loader.processed();
            int stored = loader.stored();
            int ignored = loader.ignored();

            LOGGER.trace(" processed {} - stored {} - ignored {}", processed, stored, ignored);
        }
    }
}
