package com.boschat.sikb;

import com.boschat.sikb.common.configuration.ConfigLoader;
import com.boschat.sikb.common.configuration.ResponseCode;
import com.boschat.sikb.common.utils.DateUtils;
import com.boschat.sikb.model.Affiliation;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.AffiliationForUpdate;
import com.boschat.sikb.model.Board;
import com.boschat.sikb.model.Club;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;
import com.boschat.sikb.model.Credentials;
import com.boschat.sikb.model.Formation;
import com.boschat.sikb.model.FormationType;
import com.boschat.sikb.model.LicenceType;
import com.boschat.sikb.model.Person;
import com.boschat.sikb.model.PersonForCreation;
import com.boschat.sikb.model.PersonForUpdate;
import com.boschat.sikb.model.Reset;
import com.boschat.sikb.model.Season;
import com.boschat.sikb.model.SeasonForCreation;
import com.boschat.sikb.model.SeasonForUpdate;
import com.boschat.sikb.model.Session;
import com.boschat.sikb.model.Sex;
import com.boschat.sikb.model.UpdatePassword;
import com.boschat.sikb.model.User;
import com.boschat.sikb.model.UserForCreation;
import com.boschat.sikb.model.UserForUpdate;
import com.boschat.sikb.model.ZError;
import com.boschat.sikb.servlet.InitServlet;
import com.boschat.sikb.servlet.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.subethamail.wiser.Wiser;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static com.boschat.sikb.WiserAssertions.assertReceivedMessage;
import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_DEFAULT_RECIPIENT;
import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_HOST;
import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_LOGIN;
import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_PORT;
import static com.boschat.sikb.common.configuration.EnvVar.CONFIG_PATH;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_DB;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_HOST;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_PASSWORD;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_PORT;
import static com.boschat.sikb.common.configuration.EnvVar.POSTGRES_USER;
import static com.boschat.sikb.common.configuration.ResponseCode.CREATED;
import static com.boschat.sikb.common.configuration.ResponseCode.NO_CONTENT;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_ACCESS_TOKEN;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_AUTHORIZATION;
import static com.boschat.sikb.model.Sex.FEMALE;
import static com.boschat.sikb.model.Sex.MALE;
import static com.boschat.sikb.utils.HashUtils.basicEncode;
import static org.glassfish.jersey.test.TestProperties.CONTAINER_PORT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractTest {

    protected static final String SEASON_DEFAULT_ID = "20182019";

    protected static final String SEASON_DEFAULT_DESCRIPTION = "Saison 2018/2019";

    protected static final LocalDate SEASON_DEFAULT_BEGIN = LocalDate.of(2018, 9, 1);

    protected static final LocalDate SEASON_DEFAULT_END = LocalDate.of(2019, 8, 31);

    protected static final Integer PERSON_DEFAULT_ID = 1;

    protected static final String PERSON_DEFAULT_FIRST_NAME = "MyFirstName";

    protected static final String PERSON_DEFAULT_NAME = "PersonName";

    protected static final Sex PERSON_DEFAULT_SEX = MALE;

    protected static final LocalDate PERSON_DEFAULT_BIRTH_DATE = LocalDate.of(1990, 4, 4);

    protected static final String PERSON_DEFAULT_ADDRESS = "My address";

    protected static final String PERSON_DEFAULT_POSTAL_CODE = "35000";

    protected static final String PERSON_DEFAULT_CITY = "Rennes";

    protected static final String PERSON_DEFAULT_PHONE_NUMBER = "070707070707";

    protected static final String PERSON_DEFAULT_EMAIL = "person@kin-ball.fr";

    protected static final String PERSON_DEFAULT_NATIONALITY = "FRANCE";

    protected static final List<Formation> PERSON_DEFAULT_FORMATIONS = Arrays.asList(
        new Formation().id(1).name("Arbitre Niveau 1").date(LocalDate.of(2015, 4, 4)),
        new Formation().id(2).name("Arbitre Niveau 2").date(LocalDate.of(2016, 4, 4)));

    protected static final String USER_DEFAULT_ACCESS_TOKEN = "YWI1MWZmOTYtMDA3OS00Y2M3LWFhYjEtZWU5OTVkYTRhZjkzMjAxOC0wMS0xOFQxMzoxMSswMTowMA==";

    protected static final Integer USER_DEFAULT_ID = 1;

    protected static final String USER_DEFAULT_EMAIL = "myEmail@kin-ball.fr";

    protected static final String USER_DEFAULT_PASSWORD = "test";

    protected static final String SEASON_DEFAULT_SHORT_NAME = "20182019";

    protected static final Integer CLUB_DEFAULT_ID = 1;

    protected static final String CLUB_DEFAULT_NAME = "Kin-ball Association Rennes";

    protected static final String CLUB_DEFAULT_SHORT_NAME = "KBAR";

    protected static final String CLUB_DEFAULT_LOGO = "https://i1.wp.com/www.kin-ball.fr/wp-content/uploads/2016/11/KBAR-Rennes.jpg?resize=100%2C100&ssl=1";

    protected static final String AFFILIATION_DEFAULT_PREFECTURE_NUMBER = "W333333333";

    protected static final String AFFILIATION_DEFAULT_PREFECTURE_CITY = "Rennes";

    protected static final String AFFILIATION_DEFAULT_SIRET_NUMBER = "786539137163134";

    protected static final String AFFILIATION_DEFAULT_ADDRESS = "My club Address";

    protected static final String AFFILIATION_DEFAULT_POSTAL_CODE = "35000";

    protected static final String AFFILIATION_DEFAULT_CITY = "Rennes";

    protected static final String AFFILIATION_DEFAULT_PHONE_NUMBER = "0709864324";

    protected static final String AFFILIATION_DEFAULT_EMAIL = "myEmail@kin-ball.fr";

    protected static final String AFFILIATION_DEFAULT_WEBSITE = "myWebsite.kin-ball.fr";

    protected static final String AFFILIATION_DEFAULT_PRESIDENT = "Mrs president";

    protected static final Sex AFFILIATION_DEFAULT_PRESIDENT_SEX = FEMALE;

    protected static final String AFFILIATION_DEFAULT_SECRETARY = "Mr Secretary";

    protected static final Sex AFFILIATION_DEFAULT_SECRETARY_SEX = MALE;

    protected static final String AFFILIATION_DEFAULT_TREASURER = "Mrs Treasurer";

    protected static final Sex AFFILIATION_DEFAULT_TREASURER_SEX = FEMALE;

    protected static final Integer AFFILIATION_DEFAULT_MEMBERS_NUMBER = 7;

    protected static final LocalDate AFFILIATION_DEFAULT_ELECTED_DATE = LocalDate.of(2018, 6, 29);

    protected static final OffsetDateTime NOW = OffsetDateTime.of(2018, 1, 18, 13, 11, 0, 0, DateUtils.getCurrentZoneOffSet());

    private static Wiser wiser;

    private static String serverPort;

    protected static JerseyTest jerseyTest;

    protected static InitServlet initServlet;

    /**
     * find a random free port to assign to mock server
     */
    public static Integer findRandomOpenPortOnAllLocalInterfaces() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    private static ZError getZError(Response result) throws IOException {
        return getBody(result, ZError.class);
    }

    protected static Affiliation getAffiliation(Response result) throws IOException {
        return getBody(result, Affiliation.class);
    }

    protected static User getUser(Response result) throws IOException {
        return getBody(result, User.class);
    }

    protected static Person getPerson(Response result) throws IOException {
        return getBody(result, Person.class);
    }

    protected static List<Person> getPersons(Response result) throws IOException {
        return Arrays.asList(getBody(result, Person[].class));
    }

    protected static Session getSession(Response result) throws IOException {
        return getBody(result, Session.class);
    }

    protected static List<User> getUsers(Response result) throws IOException {
        return Arrays.asList(getBody(result, User[].class));
    }

    protected static Season getSeason(Response result) throws IOException {
        return getBody(result, Season.class);
    }

    protected static List<Season> getSeasons(Response result) throws IOException {
        return Arrays.asList(getBody(result, Season[].class));
    }

    protected static List<FormationType> getFormationTypes(Response result) throws IOException {
        return Arrays.asList(getBody(result, FormationType[].class));
    }

    protected static List<LicenceType> getLicenceTypes(Response result) throws IOException {
        return Arrays.asList(getBody(result, LicenceType[].class));
    }

    protected static Club getClub(Response result) throws IOException {
        return getBody(result, Club.class);
    }

    protected static List<Club> getClubs(Response result) throws IOException {
        return Arrays.asList(getBody(result, Club[].class));
    }

    private static <T> T getBody(Response result, Class<T> clazz) throws IOException {
        String body = result.readEntity(String.class);
        return JacksonJsonProvider.getMapper().readValue(body, clazz);
    }

    protected static void checkResponse(Response result, ResponseCode responseCode, Object... params) throws IOException {
        Short code = (short) result.getStatusInfo().getStatusCode();

        if (OK.equals(responseCode) || CREATED.equals(responseCode) || NO_CONTENT.equals(responseCode)) {
            if (!OK.getCodeHttp().equals(code) && !CREATED.getCodeHttp().equals(code) && !NO_CONTENT.getCodeHttp().equals(code)) {
                ZError errorResponse = getZError(result);
                fail("Unexpected Error occurred : " + result.getStatusInfo().getStatusCode() + "/" + errorResponse.getCode() + " : "
                    + errorResponse.getMessage());
            }

            assertEquals(responseCode.getCodeHttp(), code, "ResponseCode incorrect");
        } else {
            ZError errorResponse = getZError(result);
            assertAll(
                () -> assertNotNull(errorResponse, "ZError not found"),
                () -> assertEquals(responseCode.getCode(), errorResponse.getCode().intValue(), "Wrong code expected"),
                () -> assertEquals(String.format(responseCode.getErrorMessage(), params), errorResponse.getMessage(),
                    "Wrong message expected"),
                () -> assertEquals(responseCode.getCodeHttp(), code, "Wrong http code expected")
            );
        }

    }

    @BeforeAll
    public static void setUp() throws Exception {
        initJerseyTest();
        jerseyTest.setUp();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        jerseyTest.tearDown();
    }

    private static void initJerseyTest() {
        jerseyTest = new JerseyTest() {

            @Override
            protected Application configure() {
                if (null == serverPort) {
                    try {
                        serverPort = String.valueOf(findRandomOpenPortOnAllLocalInterfaces());
                        forceSet(CONTAINER_PORT, serverPort);
                    } catch (IOException e) {
                        fail(e);
                    }
                }
                return new ResourceConfig().packages(
                    "com.boschat.sikb.api",
                    "com.boschat.sikb.servlet",
                    "com.boschat.sikb.mapper");
            }
        };
    }

    @BeforeAll
    public static void start() throws IOException {
        initContext();
    }

    @AfterAll
    public static void end() {
        initServlet.destroy();
        wiser.stop();
    }

    private static void initContext() throws IOException {
        DateUtils.useFixedClockAt(NOW);
        System.setProperty(CONFIG_PATH.getEnv(), "src/main/resources");
        System.setProperty(POSTGRES_DB.getEnv(), "sikb");
        System.setProperty(POSTGRES_HOST.getEnv(), "localhost");
        System.setProperty(POSTGRES_PORT.getEnv(), "5432");
        System.setProperty(POSTGRES_USER.getEnv(), "postgres");
        System.setProperty(POSTGRES_PASSWORD.getEnv(), "postgres");

        initServlet = new InitServlet();
        initServlet.init();

        ConfigLoader.getInstance().setProperties(SMTP_HOST, "localhost");
        ConfigLoader.getInstance().setProperties(SMTP_PORT, findRandomOpenPortOnAllLocalInterfaces().toString());
        ConfigLoader.getInstance().setProperties(SMTP_DEFAULT_RECIPIENT, "");

        wiser = new Wiser(SMTP_PORT.getIntegerValue());
        wiser.start();
    }

    protected Response affiliationGet(ApiVersion version, Integer clubId, String season) {
        String path = buildPath(version, clubId, season);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }

    protected Response affiliationDelete(ApiVersion version, Integer clubId, String season) {
        String path = buildPath(version, clubId, season);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).delete();
    }

    protected Response affiliationCreate(ApiVersion version, Integer clubId, String season, AffiliationForCreation affiliationForCreation) {
        Entity<AffiliationForCreation> entity = Entity.json(affiliationForCreation);
        String path = buildPath(version, clubId, season);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).post(entity);
    }

    protected Response affiliationUpdate(ApiVersion version, Integer clubId, String season, AffiliationForUpdate affiliationForUpdate) {
        Entity<AffiliationForCreation> entity = Entity.json(affiliationForUpdate);
        String path = buildPath(version, clubId, season);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).put(entity);
    }

    protected Response personCreate(ApiVersion version, PersonForCreation personForCreation) {
        Entity<PersonForCreation> entity = Entity.json(personForCreation);
        String path = buildPathPerson(version, null);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).post(entity);
    }

    protected Response personUpdate(ApiVersion version, Integer personId, PersonForUpdate personForUpdate) {
        Entity<PersonForUpdate> entity = Entity.json(personForUpdate);
        String path = buildPathPerson(version, personId);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).put(entity);
    }

    protected Response personGet(ApiVersion version, Integer personId) {
        String path = buildPathPerson(version, personId);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }

    protected Response personDelete(ApiVersion version, Integer personId) {
        String path = buildPathPerson(version, personId);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).delete();
    }

    protected Response personFind(ApiVersion version) {
        String path = buildPathPerson(version, null);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }

    protected Response userCreate(ApiVersion version, UserForCreation userForCreation) {
        Entity<UserForCreation> entity = Entity.json(userForCreation);
        String path = buildPathUser(version, null, false, false, false, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).post(entity);
    }

    protected Response userDelete(ApiVersion version, Integer userId) {
        String path = buildPathUser(version, userId, false, false, false, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).delete();
    }

    protected Response userLogout(ApiVersion version, String accessToken) {
        String path = buildPathUser(version, null, false, true, false, false, false);
        return createRequest(path, null, accessToken).get();
    }

    protected Response userLogin(ApiVersion version, Credentials credentials) {
        Entity<Credentials> entity = Entity.json(credentials);
        String path = buildPathUser(version, null, true, false, false, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).post(entity);
    }

    protected static void checkEmail(String recipient, String title) {
        checkEmailWithWiser(wiser, recipient, title);
    }

    protected Response resetUserPassword(ApiVersion version, Reset reset) {
        Entity<Reset> entity = Entity.json(reset);
        String path = buildPathUser(version, null, false, false, true, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).post(entity);
    }

    protected Response userUpdatePassword(ApiVersion version, UpdatePassword credentials, String accessToken) {
        Entity entity = Entity.json(credentials);
        String path = buildPathUser(version, null, false, false, false, false, true);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).post(entity);
    }

    protected Response userGet(ApiVersion version, Integer userId) {
        String path = buildPathUser(version, userId, false, false, false, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }

    protected Response userFind(ApiVersion version) {
        String path = buildPathUser(version, null, false, false, false, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }

    protected Response userUpdate(ApiVersion version, Integer userId, UserForUpdate userForUpdate) {
        Entity<UserForUpdate> entity = Entity.json(userForUpdate);
        String path = buildPathUser(version, userId, false, false, false, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).put(entity);
    }

    protected Response clubCreate(ApiVersion version, ClubForCreation clubForCreation) {
        Entity<ClubForCreation> entity = Entity.json(clubForCreation);
        String path = buildPath(version, null, null);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).post(entity);
    }

    protected Response clubGet(ApiVersion version, Integer clubId) {
        String path = buildPath(version, clubId, null);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }

    protected Response clubFind(ApiVersion version) {
        String path = buildPath(version, null, null);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }

    protected Response clubUpdate(ApiVersion version, Integer clubId, ClubForUpdate clubForUpdate) {
        Entity<ClubForUpdate> entity = Entity.json(clubForUpdate);
        String path = buildPath(version, clubId, null);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).put(entity);
    }

    protected Response clubDelete(ApiVersion version, Integer clubId) {
        String path = buildPath(version, clubId, null);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).delete();
    }

    protected Response licenceTypesFind(ApiVersion version) {
        String path = buildPathConfiguration(version, null, false, false, true);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }

    protected Response formationTypesFind(ApiVersion version) {
        String path = buildPathConfiguration(version, null, false, true, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }
    
    protected Response seasonCreate(ApiVersion version, SeasonForCreation seasonForCreation) {
        Entity<SeasonForCreation> entity = Entity.json(seasonForCreation);
        String path = buildPathConfiguration(version, null, true, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).post(entity);
    }

    protected Response seasonFind(ApiVersion version) {
        String path = buildPathConfiguration(version, null, true, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).get();
    }

    protected Response seasonUpdate(ApiVersion version, String SeasonId, SeasonForUpdate bean) {
        Entity<SeasonForUpdate> entity = Entity.json(bean);
        String path = buildPathConfiguration(version, SeasonId, true, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).put(entity);
    }

    protected Response seasonDelete(ApiVersion version, String seasonId) {
        String path = buildPathConfiguration(version, seasonId, true, false, false);
        return createRequest(path, null, USER_DEFAULT_ACCESS_TOKEN).delete();
    }

    private Invocation.Builder createRequest(String path, String token, String accessToken) {

        WebTarget target = jerseyTest.target(path).register(JacksonJsonProvider.class);

        if (token != null) {
            target = target.queryParam("confirmToken", token);
        }
        Invocation.Builder builder = target.request();

        builder.header(HEADER_ACCESS_TOKEN, accessToken);
        builder.header(HEADER_AUTHORIZATION, "Basic " + basicEncode("admin", "admin"));

        return builder;
    }

    private String buildPathUser(ApiVersion version, Integer userId, boolean login, boolean logout, boolean reset, boolean confirm, boolean updatePassword) {
        StringBuilder path = new StringBuilder("/" + version.getName() + "/users");
        if (userId != null) {
            path.append("/");
            path.append(userId);
        }

        if (login) {
            path.append("/login");
        }
        if (logout) {
            path.append("/logout");
        }
        if (reset) {
            path.append("/reset");
        }
        if (confirm) {
            path.append("/confirm");
        }
        if (updatePassword) {
            path.append("/updatePassword");
        }
        return path.toString();
    }

    private String buildPathPerson(ApiVersion version, Integer personId) {
        StringBuilder path = new StringBuilder("/" + version.getName() + "/persons");
        if (personId != null) {
            path.append("/");
            path.append(personId);
        }
        return path.toString();
    }

    protected String buildPath(ApiVersion version, Integer clubId, String season) {
        StringBuilder path = new StringBuilder("/" + version.getName() + "/clubs");
        if (clubId != null) {
            path.append("/");
            path.append(clubId);
        }
        if (season != null) {
            path.append("/");
            path.append(season);
            path.append("/affiliations");
        }
        return path.toString();
    }

    private String buildPathConfiguration(ApiVersion version, String id, boolean isSeason, boolean isFormationType, boolean isLicenceType) {
        StringBuilder path = new StringBuilder("/" + version.getName() + "/configurations");
        if (isSeason) {
            path.append("/seasons");
        }
        if (isFormationType) {
            path.append("/formationTypes");
        }
        if (isLicenceType) {
            path.append("/licenceTypes");
        }

        if (id != null) {
            path.append("/");
            path.append(id);
        }
        return path.toString();
    }

    protected void checkAffiliation(Affiliation affiliation, String prefectureNumber, String prefectureCity, String siretNumber, String address,
        String postalCode, String city, String phoneNumber, String email, String webSite, OffsetDateTime creationDateTime,
        OffsetDateTime modificationDateTime, String president, Sex presidentSex, String secretary, Sex secretarySex,
        String treasurer, Sex treasurerSex, Integer membersNumber, LocalDate electedDate) {
        assertAll("Check affiliation " + affiliation.getId(),
            () -> assertNotNull(affiliation, " Affiliation shouldn't be null"),
            () -> assertNotNull(affiliation.getId(), "Id shouldn't be null"),

            () -> assertEquals(prefectureNumber, affiliation.getPrefectureNumber(), " prefectureNumber shouldn't be null"),
            () -> assertEquals(prefectureCity, affiliation.getPrefectureCity(), " prefectureCity shouldn't be null"),
            () -> assertEquals(siretNumber, affiliation.getSiretNumber(), " siretNumber incorrect"),
            () -> assertEquals(address, affiliation.getAddress(), " address incorrect"),
            () -> assertEquals(postalCode, affiliation.getPostalCode(), " postalCode incorrect"),
            () -> assertEquals(city, affiliation.getCity(), " city incorrect"),
            () -> assertEquals(phoneNumber, affiliation.getPhoneNumber(), " phoneNumber incorrect"),
            () -> assertEquals(email, affiliation.getEmail(), " email incorrect"),
            () -> assertEquals(webSite, affiliation.getWebSite(), " webSite incorrect"),
            () -> checkBoard(affiliation.getBoard(), president, presidentSex, secretary, secretarySex, treasurer, treasurerSex, membersNumber, electedDate),
            () -> assertEquals(creationDateTime, affiliation.getCreationDateTime(), " creationDateTime incorrect"),
            () -> assertEquals(modificationDateTime, affiliation.getModificationDateTime(), " modificationDateTime incorrect")
        );
    }

    private void checkBoard(Board board, String president, Sex presidentSex, String secretary, Sex secretarySex,
        String treasurer, Sex treasurerSex, Integer membersNumber, LocalDate electedDate) {

        if (president == null) {
            assertNull(board, " Affiliation should be null");
        } else {
            assertAll("Check board ",
                () -> assertNotNull(board, " Affiliation shouldn't be null"),
                () -> assertEquals(president, board.getPresident(), " president incorrect"),
                () -> assertEquals(presidentSex, board.getPresidentSex(), " presidentSex incorrect"),
                () -> assertEquals(secretary, board.getSecretary(), " secretary incorrect"),
                () -> assertEquals(secretarySex, board.getSecretarySex(), " secretarySex incorrect"),
                () -> assertEquals(treasurer, board.getTreasurer(), " treasurer incorrect"),
                () -> assertEquals(treasurerSex, board.getTreasurerSex(), " treasurerSex incorrect"),
                () -> assertEquals(membersNumber, board.getMembersNumber(), " membersNumber incorrect"),
                () -> assertEquals(electedDate, board.getElectedDate(), " electedDate incorrect")
            );
        }
    }

    protected void checkClub(Club club, String name, String shortName, String logo) {
        assertAll("Check Club " + club.getName(),
            () -> assertNotNull(club, " Club shouldn't be null"),
            () -> assertNotNull(club.getId(), "Id shouldn't be null"),
            () -> assertEquals(name, club.getName(), " name incorrect"),
            () -> assertEquals(shortName, club.getShortName(), " shortName incorrect"),
            () -> assertEquals(logo, club.getLogo(), " logo incorrect")
        );
    }

    protected void checkSeason(Season season, String id, String description, LocalDate begin, LocalDate end) {
        assertAll("Check Season " + season.getId(),
            () -> assertNotNull(season, " season shouldn't be null"),
            () -> assertEquals(id, season.getId(), "id incorrect"),
            () -> assertEquals(description, season.getDescription(), " description incorrect"),
            () -> assertEquals(begin, season.getBegin(), " begin incorrect"),
            () -> assertEquals(end, season.getEnd(), " end incorrect")
        );
    }

    protected void checkUser(User user, String email) {
        assertAll("Check User " + user.getEmail(),
            () -> assertNotNull(user, " User shouldn't be null"),
            () -> assertNotNull(user.getId(), "Id shouldn't be null"),
            () -> assertEquals(email, user.getEmail(), " email incorrect")
        );
    }

    protected void checkPerson(Person person, String firstName, String name, Sex sex, LocalDate birthDate, String address, String postalCode, String city,
        String phoneNumber, String email, String nationality, List<Formation> formations) {
        assertAll("Check person " + person.getFirstName(),
            () -> assertNotNull(person, " User shouldn't be null"),
            () -> assertNotNull(person.getId(), "Id shouldn't be null"),
            () -> assertEquals(firstName, person.getFirstName(), " firstName incorrect"),
            () -> assertEquals(name, person.getName(), " name incorrect"),
            () -> assertEquals(sex, person.getSex(), " sex incorrect"),
            () -> assertEquals(birthDate, person.getBirthDate(), " birthDate incorrect"),
            () -> assertEquals(address, person.getAddress(), " address incorrect"),
            () -> assertEquals(postalCode, person.getPostalCode(), " postalCode incorrect"),
            () -> assertEquals(city, person.getCity(), " city incorrect"),
            () -> assertEquals(phoneNumber, person.getPhoneNumber(), " phoneNumber incorrect"),
            () -> assertEquals(email, person.getEmail(), " email incorrect"),
            () -> assertEquals(nationality, person.getNationality(), " nationality incorrect"),
            () -> assertEquals(formations, person.getFormations(), " formations incorrect")
        );
    }

    protected void checkSession(Session session) {
        assertAll("Check Session ",
            () -> assertNotNull(session, " User shouldn't be null"),
            () -> assertNotNull(session.getAccessToken(), "AccessToken  shouldn't be null")
        );
    }

    public static void checkEmailWithWiser(Wiser myWiser, String recipient, String title) {
        assertReceivedMessage(myWiser)
            .from(SMTP_LOGIN.getValue())
            .to(recipient)
            .withSubject(title);
    }

    protected Response userConfirm(ApiVersion version, UpdatePassword credentials, String token) {
        Entity<UpdatePassword> entity = Entity.json(credentials);
        String path = buildPathUser(version, null, false, false, false, true, false);
        return createRequest(path, token, USER_DEFAULT_ACCESS_TOKEN).post(entity);
    }
}
