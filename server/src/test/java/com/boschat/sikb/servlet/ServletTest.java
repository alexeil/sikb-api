package com.boschat.sikb.servlet;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.common.configuration.ApplicationProperties;
import com.boschat.sikb.common.configuration.ConfigLoader;
import com.boschat.sikb.common.configuration.IProperties;
import com.boschat.sikb.common.exceptions.TechnicalException;
import com.boschat.sikb.model.ClubForUpdate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.common.configuration.EnvVar.CONFIG_PATH;
import static com.boschat.sikb.common.configuration.ResponseCode.CONFIG_TECH_LOADING_ERROR;
import static com.boschat.sikb.common.configuration.ResponseCode.INTERNAL_ERROR;
import static com.boschat.sikb.common.configuration.ResponseCode.METHOD_NOT_ALLOWED;
import static com.boschat.sikb.common.configuration.ResponseCode.SERVICE_NOT_FOUND;
import static com.boschat.sikb.servlet.ServletTest.FakeProperties.FAKE_PROPERTY;
import static com.boschat.sikb.utils.HashUtils.basicEncode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Test Servlet")
class ServletTest extends AbstractTest {

    private Response callRequest(String path) {
        return jerseyTest.target(path)
                         .request()
                         .header("Authorization", "Basic " + basicEncode("admin", "admin"))
                         .get();
    }

    @Test
    @DisplayName(" JSON parse Error ")
    void jsonParseError() {
        Entity<ClubForUpdate> entity = Entity.json(new ClubForUpdate().name("Test"));
        Response response = jerseyTest.target("/v1/users/login").register(JacksonJsonProvider.class).request().header("Authorization",
            "Basic " + basicEncode("admin", "admin")).post(entity);
        assertEquals(400, response.getStatus(), "Wrong Status");
    }

    @Test
    @DisplayName(" status ")
    void testStatus() {
        Response response = callRequest("/status/");
        assertEquals(200, response.getStatus(), "Wrong Status");
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws IOException {
        Response response = callRequest("/v1/profiles/aeggae1/toto");
        checkResponse(response, SERVICE_NOT_FOUND);
    }

    @Test
    @DisplayName(" not allowed ")
    void notAllowed() throws IOException {
        Entity<ClubForUpdate> entity = Entity.json(new ClubForUpdate());

        Response response = jerseyTest.target("/v1/clubs/1")
                                      .register(JacksonJsonProvider.class)
                                      .request()
                                      .header("Authorization", "Basic " + basicEncode("admin", "admin"))
                                      .post(entity);

        checkResponse(response, METHOD_NOT_ALLOWED);
    }

    @Test
    @DisplayName(" internal error  ")
    void internalError() throws IOException {
        Response response = callRequest("/v1/clubs/affiliations");
        checkResponse(response, INTERNAL_ERROR, "org.glassfish.jersey.server.ParamException$PathParamException");
    }

    public enum FakeProperties implements IProperties {
        FAKE_PROPERTY("fake.property");

        private String propName;

        FakeProperties(String propName) {
            this.propName = propName;
        }

        public String getPropName() {
            return propName;
        }
    }

    @Nested
    @DisplayName(" check Properties ")
    class checkProperties {

        @AfterEach
        void reset() {
            System.setProperty(CONFIG_PATH.getEnv(), "src/main/resources");
            initServlet.destroy();
            initServlet.init();
        }

        @Test
        @DisplayName(" reload ok ")
        void testReload() {
            Response response = callRequest("/reload/");
            assertEquals(200, response.getStatus(), "Wrong Status");
        }

        @Test
        @DisplayName(" reload with file not found")
        void testReloadFileNotFound() {
            System.setProperty(CONFIG_PATH.getEnv(), "src/main/resources/unknown");
            Response response = callRequest("/reload/");
            assertEquals(500, response.getStatus(), "Wrong Status");
        }

        @Test
        @DisplayName(" initServlet with file not found")
        void initServletWithFileNotFound() {
            try {
                System.setProperty(CONFIG_PATH.getEnv(), "src/main/resources/unknown");
                initServlet.destroy();
                initServlet.init();

                ConfigLoader.getInstance().findProperties(ApplicationProperties.SMTP_PORT);
                fail();
            } catch (TechnicalException e) {
                assertEquals(CONFIG_TECH_LOADING_ERROR, e.getErrorCode(), "Wrong error");
            }
        }

        @Test
        @DisplayName(" find unknown property")
        void findUnknownProperty() {
            try {
                ConfigLoader.getInstance().findProperties(FAKE_PROPERTY);
                fail();
            } catch (TechnicalException e) {
                assertEquals(CONFIG_TECH_LOADING_ERROR, e.getErrorCode(), "Wrong error");
            }
        }

        @Test
        @DisplayName(" find a property but not loaded")
        void findPropertyNotLoaded() {
            try {
                initServlet.destroy();
                ConfigLoader.getInstance().findProperties(ApplicationProperties.SMTP_PORT);
                fail();
            } catch (TechnicalException e) {
                assertEquals(CONFIG_TECH_LOADING_ERROR, e.getErrorCode(), "Wrong error");
            }
        }
    }
}
