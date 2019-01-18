package com.boschat.sikb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.boschat.sikb.api.ResponseCode.SERVICE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Servlet")
class ServletTest extends AbstractTest {

    @Test
    @DisplayName(" status ")
    void testStatus() {
        Response response = jerseyTest.target("/status/").request().get();
        assertEquals(200, response.getStatus(), "Wrong Status");
    }

    @Test
    @DisplayName(" reload ")
    void testReload() {
        Response response = jerseyTest.target("/reload/").request().get();
        assertEquals(200, response.getStatus(), "Wrong Status");
    }

    @Test
    @DisplayName(" not found ")
    void notFound() throws IOException {
        Response result = jerseyTest.target("/v1/profiles/aeggae1/toto").request().get();
        checkResponse(result, SERVICE_NOT_FOUND);
    }
}
