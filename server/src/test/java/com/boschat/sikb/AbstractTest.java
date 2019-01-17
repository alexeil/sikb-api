package com.boschat.sikb;

import com.boschat.sikb.api.ResponseCode;
import com.boschat.sikb.model.Affiliation;
import com.boschat.sikb.model.AffiliationForCreation;
import com.boschat.sikb.model.Club;
import com.boschat.sikb.model.ClubForCreation;
import com.boschat.sikb.model.ZError;
import com.boschat.sikb.persistence.DAOFactory;
import com.boschat.sikb.servlet.JacksonJsonProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.jooq.Loader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.boschat.sikb.api.ResponseCode.CREATED;
import static com.boschat.sikb.api.ResponseCode.DELETED;
import static com.boschat.sikb.api.ResponseCode.OK;
import static com.boschat.sikb.tables.Club.CLUB;
import static org.glassfish.jersey.test.TestProperties.CONTAINER_PORT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractTest extends JerseyTest {

    protected static final Integer DEFAULT_CLUB_ID = 1;

    protected static final String DEFAULT_CLUB_NAME = "Kin-ball Association Rennes";

    protected static final String DEFAULT_CLUB_SHORT_NAME = "KBAR";

    protected static final String DEFAULT_CLUB_LOGO = "https://i1.wp.com/www.kin-ball.fr/wp-content/uploads/2016/11/KBAR-Rennes.jpg?resize=100%2C100&ssl=1";

    private static final Logger LOGGER = LogManager.getLogger(AbstractTest.class);

    private static String proffamPort;

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

    protected static void loadDataSuite(String resourcePath) throws IOException {
        URL url = AbstractTest.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            LOGGER.error("resourcePath notFound : " + resourcePath);
        } else {
            Loader loader = DAOFactory.getInstance().getDslContext()
                                      .loadInto(CLUB)
                                      .loadCSV(url.openStream())
                                      .fields(CLUB.ID, CLUB.NAME, CLUB.SHORTNAME, CLUB.LOGO)
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
    }

    private static void executeScript(String path) throws Exception {
        //FIXME Doesn't execute script so far
        URL url = AbstractTest.class.getClassLoader().getResource(path);
        if (url == null) {
            //LOGGER.error(new LogMetadata("resourcePath notFound : " + resourcePath));
        } else {
            DAOFactory.getInstance().getAffiliationDAO().truncate();
        }
    }

    private static ZError getZError(Response result) throws IOException {
        return getBody(result, ZError.class);
    }

    protected static Affiliation getAffiliation(Response result) throws IOException {
        return getBody(result, Affiliation.class);
    }

    protected static Club getClub(Response result) throws IOException {
        return getBody(result, Club.class);
    }

    protected static List<Club> getClubs(Response result) throws IOException {
        return Arrays.asList(getBody(result, Club[].class));
    }

    private static <T> T getBody(Response result, Class<T> clazz) throws IOException {
        String body = result.readEntity(String.class);
        return com.boschat.sikb.servlet.JacksonJsonProvider.getMapper().readValue(body, clazz);
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
                    () -> Assertions.assertNotNull(errorResponse, "ZError not found"),
                    () -> assertEquals(responseCode.getCode(), errorResponse.getCode().intValue(), "Wrong code expected"),
                    () -> assertEquals(String.format(responseCode.getErrorMessage(), params), errorResponse.getMessage(),
                            "Wrong message expected"),
                    () -> assertEquals(responseCode.getCodeHttp(), code, "Wrong http code expected")
            );
        }

    }

    // do not name this setup()
    @BeforeEach
    public void before() throws Exception {
        super.setUp();
    }

    // do not name this tearDown()
    @AfterEach
    public void after() throws Exception {
        super.tearDown();
    }

    @BeforeEach
    public void start() throws IOException {
        //reinitMockServer();
        initContext();
    }

    public void initContext() {
 /*       System.setProperty(ERABLE_SERVICE.getEnv(), "proffam");
        System.setProperty(ERABLE_CONFIG_TECH_DIR.getEnv(), "../../server/proffamww/conf/usine/technical");
        System.setProperty(ERABLE_CONFIG_FUNC_DIR.getEnv(), "../../server/proffamww/conf/usine/functionnal");
        ConfigLoader.getInstance().loadAndCheckTechnicalConfig(TechnicalPropertiesWW.class, ERABLE_CONFIG_TECH_DIR.getValue(), WW.getId());

        System.setProperty(ERABLE_CONFIG_TECH_DIR.getEnv(), "../../server/proffamrs/conf/usine/technical");
        System.setProperty(ERABLE_CONFIG_FUNC_DIR.getEnv(), "../../server/proffamrs/conf/usine/functionnal");
        ConfigLoader.getInstance().loadAndCheckTechnicalConfig(TechnicalPropertiesWW.class, ERABLE_CONFIG_TECH_DIR.getValue(), RS.getId());
*/
    }

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
        return getResourceConfig();
    }

    public Application getResourceConfig() {
        return new ResourceConfig().packages(
                "com.boschat.sikb.api",
                "com.boschat.sikb.servlet",
                "com.boschat.sikb.mapper");

    }

    protected Response affiliationCreate(ApiVersion version, AffiliationForCreation affiliationForCreation) {
        Entity<AffiliationForCreation> entity = Entity.json(affiliationForCreation);
        String path = buildPath(version, null);
        return createRequest(path).post(entity);
    }

    protected Response clubCreate(ApiVersion version, ClubForCreation clubForCreation) {
        Entity<ClubForCreation> entity = Entity.json(clubForCreation);
        String path = buildPath(version, null);
        return createRequest(path).post(entity);
    }

    protected Response clubGetById(ApiVersion version, Integer clubId) {
        String path = buildPath(version, clubId);
        return createRequest(path).get();
    }

    protected Response clubFind(ApiVersion version) {
        String path = buildPath(version, null);
        return createRequest(path).get();
    }

    private Invocation.Builder createRequest(String path) {

        WebTarget target = target(path)
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

    private String buildPath(ApiVersion version, Integer clubId) {
        StringBuilder path = new StringBuilder("/" + version.getName() + "/clubs");
        if (clubId != null) {
            path.append("/");
            path.append(clubId);
        }
        return path.toString();
    }

    protected void checkAffiliation(Affiliation affiliation, String name) {
        assertAll("Check profile " + affiliation.getAssociationName(),
                () -> assertNotNull(affiliation, " Affiliation shouldn't be null"),
                () -> assertNotNull(affiliation.getId(), "Id shouldn't be null"),
                () -> assertEquals(name, affiliation.getAssociationName(), " name incorrect")
        );
    }

    protected void checkClub(Club club, String name, String shortName, String logo) {
        assertAll("Check profile " + club.getName(),
                () -> assertNotNull(club, " Affiliation shouldn't be null"),
                () -> assertNotNull(club.getId(), "Id shouldn't be null"),
                () -> assertEquals(name, club.getName(), " name incorrect"),
                () -> assertEquals(shortName, club.getShortName(), " shortName incorrect"),
                () -> assertEquals(logo, club.getLogo(), " logo incorrect")
        );
    }
}
