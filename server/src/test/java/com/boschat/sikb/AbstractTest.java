package com.boschat.sikb;

import com.boschat.sikb.api.ResponseCode;
import com.boschat.sikb.model.Affiliation;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.AffiliationForUpdate;
import com.boschat.sikb.model.Board;
import com.boschat.sikb.model.Club;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ClubForUpdate;
import com.boschat.sikb.model.Sex;
import com.boschat.sikb.model.User;
import com.boschat.sikb.model.UserForCreation;
import com.boschat.sikb.model.UserForUpdate;
import com.boschat.sikb.model.ZError;
import com.boschat.sikb.persistence.DAOFactory;
import com.boschat.sikb.servlet.JacksonJsonProvider;
import com.boschat.sikb.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.jooq.Loader;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static com.boschat.sikb.Tables.AFFILIATION;
import static com.boschat.sikb.Tables.USER;
import static com.boschat.sikb.api.ResponseCode.CREATED;
import static com.boschat.sikb.api.ResponseCode.DELETED;
import static com.boschat.sikb.api.ResponseCode.OK;
import static com.boschat.sikb.model.Sex.FEMALE;
import static com.boschat.sikb.model.Sex.MALE;
import static com.boschat.sikb.tables.Club.CLUB;
import static org.glassfish.jersey.test.TestProperties.CONTAINER_PORT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractTest {

    protected static final Integer DEFAULT_CLUB_ID = 1;

    protected static final Integer DEFAULT_USER_ID = 1;

    protected static final String DEFAULT_USER_EMAIL = "myEmail@kin-ball.fr";


    protected static final String DEFAULT_SEASON = "20182019";

    protected static final String DEFAULT_CLUB_NAME = "Kin-ball Association Rennes";

    protected static final String DEFAULT_CLUB_SHORT_NAME = "KBAR";

    protected static final String DEFAULT_CLUB_LOGO = "https://i1.wp.com/www.kin-ball.fr/wp-content/uploads/2016/11/KBAR-Rennes.jpg?resize=100%2C100&ssl=1";

    protected static final Integer DEFAULT_AFFILIATION_ID = 1;

    protected static final String DEFAULT_AFFILIATION_PREFECTURE_NUMBER = "W333333333";

    protected static final String DEFAULT_AFFILIATION_PREFECTURE_CITY = "Rennes";

    protected static final String DEFAULT_AFFILIATION_SIRET_NUMBER = "786539137163134";

    protected static final String DEFAULT_AFFILIATION_ADDRESS = "My club Address";

    protected static final String DEFAULT_AFFILIATION_POSTAL_CODE = "35000";

    protected static final String DEFAULT_AFFILIATION_CITY = "Rennes";

    protected static final String DEFAULT_AFFILIATION_PHONE_NUMBER = "0709864324";

    protected static final String DEFAULT_AFFILIATION_EMAIL = "myEmail@kin-ball.fr";

    protected static final String DEFAULT_AFFILIATION_WEBSITE = "myWebsite.kin-ball.fr";

    protected static final String DEFAULT_AFFILIATION_PRESIDENT = "Mrs president";

    protected static final Sex DEFAULT_AFFILIATION_PRESIDENT_SEX = FEMALE;

    protected static final String DEFAULT_AFFILIATION_SECRETARY = "Mr Secretary";

    protected static final Sex DEFAULT_AFFILIATION_SECRETARY_SEX = MALE;

    protected static final String DEFAULT_AFFILIATION_TREASURER = "Mrs Treasurer";

    protected static final Sex DEFAULT_AFFILIATION_TREASURER_SEX = FEMALE;

    protected static final Integer DEFAULT_AFFILIATION_MEMBERS_NUMBER = 7;

    protected static final LocalDate DEFAULT_AFFILIATION_ELECTED_DATE = LocalDate.of(2018, 6, 29);

    protected static final OffsetDateTime NOW = OffsetDateTime.of(2018, 1, 18, 13, 11, 0, 0, OffsetDateTime.now().getOffset());

    private static final Logger LOGGER = LogManager.getLogger(AbstractTest.class);

    private static String proffamPort;

    protected static JerseyTest jerseyTest;

    /**
     * find a random free port to assign to mock server
     *
     * @return
     * @throws IOException
     */
    private static Integer findRandomOpenPortOnAllLocalInterfaces() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    protected static void loadUsers(String resourcePath) throws IOException {
        loadDataSuite(resourcePath, USER, USER.ID, USER.EMAIL, USER.PASSWORD, USER.INFORMATION, USER.CREATIONDATE, USER.MODIFICATIONDATE);
    }

    protected static void loadClubs(String resourcePath) throws IOException {
        loadDataSuite(resourcePath, CLUB, CLUB.ID, CLUB.NAME, CLUB.SHORTNAME, CLUB.LOGO);
    }

    protected static void loadAffiliations(String resourcePath) throws IOException {
        loadDataSuite(resourcePath, AFFILIATION, AFFILIATION.ID, AFFILIATION.PREFECTURENUMBER, AFFILIATION.PREFECTURECITY, AFFILIATION.SIRETNUMBER,
                AFFILIATION.ADDRESS,
                AFFILIATION.POSTALCODE, AFFILIATION.CITY, AFFILIATION.PHONENUMBER, AFFILIATION.EMAIL, AFFILIATION.WEBSITE, AFFILIATION.PRESIDENT,
                AFFILIATION.PRESIDENTSEX, AFFILIATION.SECRETARY, AFFILIATION.SECRETARYSEX, AFFILIATION.TREASURER, AFFILIATION.TREASURERSEX,
                AFFILIATION.MEMBERSNUMBER, AFFILIATION.ELECTEDDATE, AFFILIATION.CREATIONDATE, AFFILIATION.MODIFICATIONDATE, AFFILIATION.SEASON,
                AFFILIATION.CLUBID);

    }

    private static <T extends TableImpl> void loadDataSuite(String resourcePath, T clazz, TableField... fields) throws IOException {
        URL url = AbstractTest.class.getClassLoader().getResource(resourcePath);
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

    protected static void truncateData() {
        DAOFactory.getInstance().getAffiliationDAO().truncate();
        DAOFactory.getInstance().getClubDAO().truncate();
        DAOFactory.getInstance().getUserDAO().truncate();
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

    protected static List<User> getUsers(Response result) throws IOException {
        return Arrays.asList(getBody(result, User[].class));
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

        if (OK.equals(responseCode) || CREATED.equals(responseCode) || DELETED.equals(responseCode)) {
            if (!OK.getCodeHttp().equals(code) && !CREATED.getCodeHttp().equals(code) && !DELETED.getCodeHttp().equals(code)) {
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
                if (null == proffamPort) {
                    try {
                        proffamPort = String.valueOf(findRandomOpenPortOnAllLocalInterfaces());
                        forceSet(CONTAINER_PORT, proffamPort);
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

    @BeforeEach
    public void start() throws IOException {
        //reinitMockServer();
        initContext();
    }

    public void initContext() {
        DateUtils.useFixedClockAt(NOW);
 /*       System.setProperty(ERABLE_SERVICE.getEnv(), "proffam");
        System.setProperty(ERABLE_CONFIG_TECH_DIR.getEnv(), "../../server/proffamww/conf/usine/technical");
        System.setProperty(ERABLE_CONFIG_FUNC_DIR.getEnv(), "../../server/proffamww/conf/usine/functionnal");
        ConfigLoader.getInstance().loadAndCheckTechnicalConfig(TechnicalPropertiesWW.class, ERABLE_CONFIG_TECH_DIR.getValue(), WW.getId());

        System.setProperty(ERABLE_CONFIG_TECH_DIR.getEnv(), "../../server/proffamrs/conf/usine/technical");
        System.setProperty(ERABLE_CONFIG_FUNC_DIR.getEnv(), "../../server/proffamrs/conf/usine/functionnal");
        ConfigLoader.getInstance().loadAndCheckTechnicalConfig(TechnicalPropertiesWW.class, ERABLE_CONFIG_TECH_DIR.getValue(), RS.getId());
*/
    }

    protected Response affiliationGet(ApiVersion version, Integer clubId, String season) {
        String path = buildPath(version, clubId, season);
        return createRequest(path).get();
    }

    protected Response affiliationDelete(ApiVersion version, Integer clubId, String season) {
        String path = buildPath(version, clubId, season);
        return createRequest(path).delete();
    }

    protected Response affiliationCreate(ApiVersion version, Integer clubId, String season, AffiliationForCreation affiliationForCreation) {
        Entity<AffiliationForCreation> entity = Entity.json(affiliationForCreation);
        String path = buildPath(version, clubId, season);
        return createRequest(path).post(entity);
    }

    protected Response affiliationUpdate(ApiVersion version, Integer clubId, String season, AffiliationForUpdate affiliationForUpdate) {
        Entity<AffiliationForCreation> entity = Entity.json(affiliationForUpdate);
        String path = buildPath(version, clubId, season);
        return createRequest(path).put(entity);
    }

    protected Response userCreate(ApiVersion version, UserForCreation userForCreation) {
        Entity<UserForCreation> entity = Entity.json(userForCreation);
        String path = buildPathUser(version, null);
        return createRequest(path).post(entity);
    }

    protected Response clubCreate(ApiVersion version, ClubForCreation clubForCreation) {
        Entity<ClubForCreation> entity = Entity.json(clubForCreation);
        String path = buildPath(version, null, null);
        return createRequest(path).post(entity);
    }

    protected Response clubGet(ApiVersion version, Integer clubId) {
        String path = buildPath(version, clubId, null);
        return createRequest(path).get();
    }

    protected Response userGet(ApiVersion version, Integer userId) {
        String path = buildPathUser(version, userId);
        return createRequest(path).get();
    }

    protected Response clubFind(ApiVersion version) {
        String path = buildPath(version, null, null);
        return createRequest(path).get();
    }

    protected Response userFind(ApiVersion version) {
        String path = buildPathUser(version, null);
        return createRequest(path).get();
    }

    protected Response userUpdate(ApiVersion version, Integer userId, UserForUpdate userForUpdate) {
        Entity<UserForUpdate> entity = Entity.json(userForUpdate);
        String path = buildPathUser(version, userId);
        return createRequest(path).put(entity);
    }

    protected Response clubUpdate(ApiVersion version, Integer clubId, ClubForUpdate clubForUpdate) {
        Entity<ClubForUpdate> entity = Entity.json(clubForUpdate);
        String path = buildPath(version, clubId, null);
        return createRequest(path).put(entity);
    }

    protected Response clubDelete(ApiVersion version, Integer clubId) {
        String path = buildPath(version, clubId, null);
        return createRequest(path).delete();
    }

    protected Response userDelete(ApiVersion version, Integer userId) {
        String path = buildPathUser(version, userId);
        return createRequest(path).delete();
    }

    private Invocation.Builder createRequest(String path) {

        WebTarget target = jerseyTest.target(path)
                                     //.register(JacksonFeature.class)
                                     .register(JacksonJsonProvider.class);
        /*if (profileIdType != null) {
            target = target.queryParam(PROFILE_ID_TYPE_PARAM_NAME, profileIdType);
        }*/

        Invocation.Builder builder = target.request();
        /*if (xORANGEPartnerId != null) {
            builder = builder.header(X_ORANGE_PARTNER_ID_PARAM_NAME, xORANGEPartnerId);
        }
        if (xORANGECallerId != null) {
            builder = builder.header(X_ORANGE_CALLER_ID_PARAM_NAME, xORANGECallerId);
        }
        if (xORANGECallerVersion != null) {
            builder = builder.header(X_ORANGE_CALLER_VERSION_PARAM_NAME, xORANGECallerVersion);
        }
        if (xORANGEOrigin != null) {
            builder = builder.header(X_ORANGE_ORIGIN_PARAM_NAME, xORANGEOrigin);
        }
        if (xORANGEUserId != null) {
            builder = builder.header(X_ORANGE_USER_ID_PARAM_NAME, xORANGEUserId);
        }
        if (erableRequestId != null) {
            builder = builder.header(ERABLE_REQUEST_ID, erableRequestId);
        }
        if (erableServiceId != null) {
            builder = builder.header(ERABLE_SERVICE_ID, erableServiceId);
        }
        if (xORANGEServiceInstanceId != null) {
            builder = builder.header(X_ORANGE_SERVICE_INSTANCE_ID_PARAM_NAME, xORANGEServiceInstanceId);
        }
        if (xORANGEChannel != null) {
            builder = builder.header(X_ORANGE_CHANNEL_PARAM_NAME, xORANGEChannel);
        }*/
        return builder;
    }

    private String buildPathUser(ApiVersion version, Integer userId) {
        StringBuilder path = new StringBuilder("/" + version.getName() + "/users");
        if (userId != null) {
            path.append("/");
            path.append(userId);
        }
        return path.toString();
    }

    private String buildPath(ApiVersion version, Integer clubId, String season) {
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

    protected void checkUser(User user, String email) {
        assertAll("Check User " + user.getEmail(),
                () -> assertNotNull(user, " User shouldn't be null"),
                () -> assertNotNull(user.getId(), "Id shouldn't be null"),
                () -> assertEquals(email, user.getEmail(), " email incorrect")
        );
    }
}
