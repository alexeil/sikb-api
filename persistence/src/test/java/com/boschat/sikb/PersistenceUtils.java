package com.boschat.sikb;

import com.boschat.sikb.persistence.dao.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Loader;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static com.boschat.sikb.Sequences.USER_ID_SEQ;
import static com.boschat.sikb.Tables.AFFILIATION;
import static com.boschat.sikb.Tables.LICENCE;
import static com.boschat.sikb.Tables.PERSON;
import static com.boschat.sikb.Tables.SEASON;
import static com.boschat.sikb.Tables.TEAM;
import static com.boschat.sikb.Tables.USER;
import static com.boschat.sikb.common.utils.IntegerUtils.toIntegerArray;
import static com.boschat.sikb.tables.Club.CLUB;
import static java.nio.file.Files.lines;
import static java.util.stream.Collectors.toList;

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
        loadDataSuite(fileName, CLUB, CLUB.NAME, CLUB.SHORTNAME, CLUB.LOGOKEY, CLUB.LOGODATA, CLUB.CREATIONDATE);
    }

    private static Object[] buildObjectFromLine(String[] line) {
        Object[] values = new Object[line.length];
        for (int i = 0; values.length > i; i++) {
            String currentLine = line[i];
            if (line[i].contains(",")) {
                Integer[] numbers = toIntegerArray(Stream.of(currentLine.split(",")).map(Integer::parseInt).collect(toList()));
                values[i] = numbers;
            } else {
                values[i] = currentLine;
            }
        }
        return values;
    }

    public static void loadLicences(String fileName) throws Exception {
        loadCustomDataSuite(fileName, LICENCE, LICENCE.NUMBER, LICENCE.FORMATIONSNEED, LICENCE.TYPES, LICENCE.SEASON, LICENCE.CLUBID, LICENCE.PERSONID);
    }

    public static void loadClubs() throws IOException {
        DAOFactory.getInstance().truncateClub();
        loadClubs("sql/insertClub.csv");
    }

    public static void loadLicences() throws Exception {
        DAOFactory.getInstance().truncateLicence();
        loadLicences("sql/insertLicence.csv");
    }

    public static void loadSeasons() throws IOException {
        DAOFactory.getInstance().truncateSeason();
        loadDataSuite("sql/insertSeason.csv", SEASON, SEASON.ID, SEASON.DESCRIPTION, SEASON.BEGIN, SEASON.END);
    }

    public static void loadTeams() throws IOException {
        DAOFactory.getInstance().truncateTeam();
        loadDataSuite("sql/insertTeam.csv", TEAM, TEAM.NAME, TEAM.SEASON, TEAM.CLUBID, TEAM.TEAMMEMBERS, TEAM.CREATIONDATE, TEAM.MODIFICATIONDATE);
    }

    public static void loadAffiliations() throws IOException {
        DAOFactory.getInstance().truncateAffiliation();
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
        loadPersons("sql/insertPerson.csv");
    }

    public static void loadPersons(String fileName) throws IOException {
        loadDataSuite(fileName, PERSON, PERSON.FIRSTNAME, PERSON.NAME, PERSON.SEX, PERSON.BIRTHDATE, PERSON.ADDRESS, PERSON.POSTALCODE, PERSON.CITY,
            PERSON.PHONENUMBER, PERSON.EMAIL, PERSON.NATIONALITY, PERSON.FORMATIONS, PERSON.PHOTOKEY, PERSON.PHOTODATA, PERSON.MEDICALCERTIFICATEKEY,
            PERSON.MEDICALCERTIFICATEDATA, PERSON.MEDICALCERTIFICATEBEGINVALIDITYDATE, PERSON.CREATIONDATE,
            PERSON.MODIFICATIONDATE);
    }

    private static <T extends TableImpl> void loadCustomDataSuite(String resourcePath, T clazz, TableField... fields) throws IOException, URISyntaxException {
        URL url = PersistenceUtils.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            LOGGER.error("resourcePath notFound : " + resourcePath);
        } else {
            List<String[]> lines = lines(Paths.get(url.toURI())).map(l -> l.split(";")).collect(toList());
            Stream<Object[]> values = lines.stream().skip(1).map(PersistenceUtils::buildObjectFromLine);

            DAOFactory.getInstance().getDslContext()
                      .loadInto(clazz)
                      .loadArrays(values)
                      .fields(fields)
                      .execute();
        }
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
                                      .nullString("")
                                      .execute();

            int processed = loader.processed();
            int stored = loader.stored();
            int ignored = loader.ignored();

            LOGGER.trace(" processed {} - stored {} - ignored {}", processed, stored, ignored);
        }
    }

    public static void executeScript(String resourcePath) throws Exception {
        URL url = PersistenceUtils.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            LOGGER.error("resourcePath notFound : " + resourcePath);
        } else {
            String content = new String(Files.readAllBytes(Paths.get(url.toURI())));
            DAOFactory.getInstance().getDslContext().execute(content);
        }
    }
}
