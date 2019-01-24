package com.boschat.sikb;

import com.boschat.sikb.model.ClubForUpdate;
import com.boschat.sikb.servlet.JacksonJsonProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.api.ResponseCode.SERVICE_NOT_FOUND;
import static com.boschat.sikb.utils.HashUtils.basicEncode;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Servlet")
class ServletTest extends AbstractTest {

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
        Response response = jerseyTest.target("/status/").request().header("Authorization", "Basic " + basicEncode("admin", "admin")).get();

        assertEquals(200, response.getStatus(), "Wrong Status");
    }

    @Test
    @DisplayName(" reload ")
    void testReload() {
        Response response = jerseyTest.target("/reload/").request().header("Authorization", "Basic " + basicEncode("admin", "admin")).get();
        assertEquals(200, response.getStatus(), "Wrong Status");
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws IOException {
        Response result = jerseyTest.target("/v1/profiles/aeggae1/toto").request().header("Authorization", "Basic " + basicEncode("admin", "admin")).get();
        checkResponse(result, SERVICE_NOT_FOUND);
    }
}
