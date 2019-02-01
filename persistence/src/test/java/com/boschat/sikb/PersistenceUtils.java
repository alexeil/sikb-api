package com.boschat.sikb;

import com.boschat.sikb.persistence.dao.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Loader;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;

import java.io.IOException;
import java.net.URL;

import static com.boschat.sikb.Tables.AFFILIATION;
import static com.boschat.sikb.Tables.USER;
import static com.boschat.sikb.tables.Club.CLUB;

public class PersistenceUtils {

    private static final Logger LOGGER = LogManager.getLogger(PersistenceUtils.class);

    private PersistenceUtils() {

    }

    public static void loadUsers() throws IOException {
        loadDataSuite("sql/insertUser.csv", USER, USER.ID, USER.EMAIL, USER.PASSWORD, USER.SALT, USER.INFORMATION, USER.ACTIVATIONTOKEN,
            USER.ACTIVATIONTOKENEXPIRATIONDATE, USER.ACCESSTOKEN, USER.ENABLED, USER.CREATIONDATE, USER.MODIFICATIONDATE);
    }

    public static void loadClubs(String fileName) throws IOException {
        loadDataSuite(fileName, CLUB, CLUB.ID, CLUB.NAME, CLUB.SHORTNAME, CLUB.LOGO, CLUB.CREATIONDATE);

    }

    public static void loadClubs() throws IOException {
        loadClubs("sql/insertClub.csv");
    }

    public static void loadAffiliations() throws IOException {
        loadAffiliations("sql/insertAffiliation.csv");
    }

    public static void loadAffiliations(String fileName) throws IOException {
        loadDataSuite(fileName, AFFILIATION, AFFILIATION.ID, AFFILIATION.PREFECTURENUMBER, AFFILIATION.PREFECTURECITY,
            AFFILIATION.SIRETNUMBER,
            AFFILIATION.ADDRESS,
            AFFILIATION.POSTALCODE, AFFILIATION.CITY, AFFILIATION.PHONENUMBER, AFFILIATION.EMAIL, AFFILIATION.WEBSITE, AFFILIATION.PRESIDENT,
            AFFILIATION.PRESIDENTSEX, AFFILIATION.SECRETARY, AFFILIATION.SECRETARYSEX, AFFILIATION.TREASURER, AFFILIATION.TREASURERSEX,
            AFFILIATION.MEMBERSNUMBER, AFFILIATION.ELECTEDDATE, AFFILIATION.CREATIONDATE, AFFILIATION.MODIFICATIONDATE, AFFILIATION.SEASON,
            AFFILIATION.CLUBID);

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

            LOGGER.info(" processed {} - stored {} - ignored {}", processed, stored, ignored);
        }
    }

    public static void truncateData() {
        DAOFactory.getInstance().getAffiliationDAO().truncate();
        DAOFactory.getInstance().getClubDAO().truncate();
        DAOFactory.getInstance().getUserDAO().truncate();
    }
}
