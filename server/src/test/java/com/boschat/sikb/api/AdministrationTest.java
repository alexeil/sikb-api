package com.boschat.sikb.api;

import com.boschat.sikb.AbstractTest;
import com.boschat.sikb.servlet.JacksonJsonProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static com.boschat.sikb.PersistenceUtils.loadAffiliations;
import static com.boschat.sikb.PersistenceUtils.loadClubs;
import static com.boschat.sikb.PersistenceUtils.loadSeasons;
import static com.boschat.sikb.PersistenceUtils.loadUsers;
import static com.boschat.sikb.api.ApiVersion.V1;
import static com.boschat.sikb.common.configuration.ResponseCode.MISSING_HEADER;
import static com.boschat.sikb.common.configuration.ResponseCode.OK;
import static com.boschat.sikb.common.configuration.ResponseCode.UNAUTHORIZED;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_ACCESS_TOKEN;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_AUTHORIZATION;
import static com.boschat.sikb.utils.HashUtils.basicEncode;

@DisplayName(" Test Administration Account ")
class AdministrationTest extends AbstractTest {

    @BeforeEach
    void loadDataSuite() throws Exception {
        loadSeasons();
        loadClubs();
        loadAffiliations();
        loadUsers();
    }

    private Response affiliationGetWithCredentials(String basic, String accessToken) {
        String path = buildPath(V1, CLUB_DEFAULT_ID, SEASON_DEFAULT_ID);
        WebTarget target = jerseyTest.target(path).register(JacksonJsonProvider.class);
        Invocation.Builder builder = target.request();
        if (basic != null) {
            builder.header(HEADER_AUTHORIZATION, "Basic " + basic);
        }
        if (accessToken != null) {
            builder.header(HEADER_ACCESS_TOKEN, accessToken);
        }
        return builder.get();
    }

    @Test
    @DisplayName(" none ")
    void none() throws Exception {
        Response response = affiliationGetWithCredentials(null, null);
        checkResponse(response, UNAUTHORIZED);
    }

    @Test
    @DisplayName(" empty ")
    void empty() throws Exception {
        Response response = affiliationGetWithCredentials("", null);
        checkResponse(response, UNAUTHORIZED);
    }

    @Test
    @DisplayName(" wrong ")
    void wrong() throws Exception {
        Response response = affiliationGetWithCredentials(basicEncode("toto", "toto"), null);
        checkResponse(response, UNAUTHORIZED);
    }

    @Test
    @DisplayName(" website ")
    void website() throws Exception {
        Response response = affiliationGetWithCredentials(basicEncode("website", "website"), USER_DEFAULT_ACCESS_TOKEN);
        checkResponse(response, OK);
    }

    @Test
    @DisplayName(" website with no access token")
    void websiteWithNoToken() throws Exception {
        Response response = affiliationGetWithCredentials(basicEncode("website", "website"), null);
        checkResponse(response, MISSING_HEADER, HEADER_ACCESS_TOKEN);
    }

    @Test
    @DisplayName(" website with a wrong access Token")
    void websiteWithWrongToken() throws Exception {
        Response response = affiliationGetWithCredentials(basicEncode("website", "website"), "wrongAccessToken");
        checkResponse(response, UNAUTHORIZED);
    }

    @Test
    @DisplayName(" admin ")
    void admin() throws Exception {
        Response response = affiliationGetWithCredentials(basicEncode("admin", "admin"), null);
        checkResponse(response, OK);
    }
}
