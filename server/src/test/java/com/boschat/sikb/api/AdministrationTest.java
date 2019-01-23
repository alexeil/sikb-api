package com.boschat.sikb.api;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.servlet.JacksonJsonProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static com.boschat.sikb.ApiVersion.V1;
import static com.boschat.sikb.api.ResponseCode.OK;
import static com.boschat.sikb.api.ResponseCode.UNAUTHORIZED;
import static com.boschat.sikb.utils.HashUtils.basicEncode;

@DisplayName(" Test Administration Account ")
class AdministrationTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        truncateData();
        loadClubs();
        loadAffiliations();
    }

    private Response affiliationGetWithCredentials(String basic) {
        String path = buildPath(V1, DEFAULT_CLUB_ID, DEFAULT_SEASON);
        WebTarget target = jerseyTest.target(path).register(JacksonJsonProvider.class);
        Invocation.Builder builder = target.request();
        if (basic != null) {
            builder.header("Authorization", "Basic " + basic);
        }
        return builder.get();
    }

    @Test
    @DisplayName(" none ")
    void none() throws Exception {
        Response response = affiliationGetWithCredentials(null);
        checkResponse(response, UNAUTHORIZED);
    }

    @Test
    @DisplayName(" empty ")
    void empty() throws Exception {
        Response response = affiliationGetWithCredentials("");
        checkResponse(response, UNAUTHORIZED);
    }

    @Test
    @DisplayName(" wrong ")
    void wrong() throws Exception {
        Response response = affiliationGetWithCredentials(basicEncode("toto", "toto"));
        checkResponse(response, UNAUTHORIZED);
    }

    @Test
    @DisplayName(" website ")
    void website() throws Exception {
        Response response = affiliationGetWithCredentials(basicEncode("website", "website"));
        checkResponse(response, OK);
    }

    @Test
    @DisplayName(" admin ")
    void admin() throws Exception {
        Response response = affiliationGetWithCredentials(basicEncode("admin", "admin"));
        checkResponse(response, OK);
    }
}
