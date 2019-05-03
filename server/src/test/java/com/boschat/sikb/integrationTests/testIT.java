package com.boschat.sikb.integrationTests;

import com.boschat.sikb.common.configuration.ConfigLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.boschat.sikb.AbstractTest.PERSON_PHOTO_KEY;
import static com.boschat.sikb.AbstractTest.reloadEverythingForTests;
import static com.boschat.sikb.AbstractTest.setEnvVariablesTests;
import static com.boschat.sikb.PersistenceUtils.loadPersons;
import static com.boschat.sikb.common.configuration.ApplicationProperties.CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_HEADERS;
import static com.boschat.sikb.common.configuration.ApplicationProperties.CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_METHODS;
import static com.boschat.sikb.common.configuration.ApplicationProperties.CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_ORIGIN;
import static com.boschat.sikb.common.configuration.ApplicationProperties.DOCUMENT_BASE_PATH;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_ACCESS_CONTROL_ALLOW_HEADERS;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_ACCESS_CONTROL_ALLOW_METHODS;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_ACCESS_CONTROL_ALLOW_ORIGIN;
import static com.boschat.sikb.common.configuration.SikbConstants.HEADER_AUTHORIZATION;
import static com.boschat.sikb.model.DocumentType.PHOTO_TYPE;
import static com.boschat.sikb.utils.HashUtils.basicEncode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Integration Tests")
class testIT {

    private static final String BASE_URL = "http://localhost:8889";

    private static final String STATUS_URL = BASE_URL + "/sikb/api/status";

    private static final String POST_URL = BASE_URL + "/sikb/api/v1/persons";

    @BeforeAll
    static void start() throws Exception {
        setEnvVariablesTests();
        reloadEverythingForTests();
        ConfigLoader.getInstance().setProperties(DOCUMENT_BASE_PATH, BASE_URL + "/sikb/document/?type={type}&id={id}");
        loadPersons();

    }

    private HttpURLConnection sendRequest(String method, String path, String body) throws Exception {
        URL obj = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty(HEADER_AUTHORIZATION, "Basic " + basicEncode("admin", "admin"));

        if (HttpMethod.POST.equals(method) && body != null) {
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            os.close();
        }

        return connection;
    }

    private String getBody(InputStream inputStream) throws IOException {
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return response.toString();
    }

    @Nested
    @DisplayName(" CORS ")
    class CORS {

        @Test
        @DisplayName(" Check headers with GET ")
        void checkResponsesHeaders() throws Exception {
            HttpURLConnection con = sendRequest(HttpMethod.GET, STATUS_URL, null);

            assertAll("Check CORS Headers ",
                () -> assertEquals(HttpServletResponse.SC_OK, con.getResponseCode(), "Wrong http Code"),
                () -> assertEquals(con.getHeaderField(HEADER_ACCESS_CONTROL_ALLOW_ORIGIN), CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_ORIGIN.getValue(),
                    "Wrong header value"),
                () -> assertEquals(con.getHeaderField(HEADER_ACCESS_CONTROL_ALLOW_METHODS), CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_METHODS.getValue(),
                    "Wrong header value"),
                () -> assertEquals(con.getHeaderField(HEADER_ACCESS_CONTROL_ALLOW_HEADERS), CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_HEADERS.getValue(),
                    "Wrong header value")
            );
        }

        @Test
        @DisplayName(" Check headers with OPTIONS ")
        void options() throws Exception {
            HttpURLConnection con = sendRequest(HttpMethod.OPTIONS, STATUS_URL, null);

            assertAll("Check CORS Headers ",
                () -> assertEquals(HttpServletResponse.SC_ACCEPTED, con.getResponseCode(), "Wrong http Code"),
                () -> assertEquals(con.getHeaderField(HEADER_ACCESS_CONTROL_ALLOW_ORIGIN), CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_ORIGIN.getValue(),
                    "Wrong header value"),
                () -> assertEquals(con.getHeaderField(HEADER_ACCESS_CONTROL_ALLOW_METHODS), CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_METHODS.getValue(),
                    "Wrong header value"),
                () -> assertEquals(con.getHeaderField(HEADER_ACCESS_CONTROL_ALLOW_HEADERS), CORS_HEADER_ACCESS_CONTROL_HEADER_ALLOW_ALL_HEADERS.getValue(),
                    "Wrong header value")
            );
        }
    }

    @Nested
    @DisplayName(" Document Servlet ")
    class DocumentServlet {

        @Test
        @DisplayName(" GET Photo ")
        void getPhoto() throws Exception {
            HttpURLConnection connection = sendRequest(HttpMethod.GET, PHOTO_TYPE.buildUrl(PERSON_PHOTO_KEY), null);

            assertAll("Check ",
                () -> assertEquals(HttpServletResponse.SC_OK, connection.getResponseCode(), "Wrong http Code"),
                () -> assertNotNull(connection.getInputStream(), "body shouldn't be null")
            );
        }

        @Test
        @DisplayName(" GET unknown Photo ")
        void unknownPhoto() throws Exception {
            HttpURLConnection connection = sendRequest(HttpMethod.GET, PHOTO_TYPE.buildUrl("test"), null);

            assertAll("Check ",
                () -> assertEquals(HttpServletResponse.SC_NOT_FOUND, connection.getResponseCode(), "Wrong http Code"),
                () -> assertEquals("Photo (test) not found", getBody(connection.getErrorStream()), "body shouldn't be null")
            );
        }
    }

    @Nested
    @DisplayName(" POST requests")
    class postRequests {

        @Test
        @DisplayName(" ok ")
        void ok() throws Exception {
            String body = "{\"firstName\": \"testName\", \"name\": \"testFirstName\",\"email\": \"test@kin-ball.fr\"}";
            HttpURLConnection connection = sendRequest(HttpMethod.POST, POST_URL, body);

            assertAll("Check ",
                () -> assertEquals(HttpServletResponse.SC_CREATED, connection.getResponseCode(), "Wrong http Code"),
                () -> assertEquals("{\"firstName\":\"testName\",\"name\":\"testFirstName\",\"email\":\"test@kin-ball.fr\",\"id\":3}",
                    getBody(connection.getInputStream()), "body shouldn't be null")
            );
        }

        @Test
        @DisplayName(" Body Field format error ")
        void bodyFormatError() throws Exception {
            String body = "\"email\": true}";

            HttpURLConnection connection = sendRequest(HttpMethod.POST, POST_URL, body);

            assertAll("Check ",
                () -> assertEquals(HttpServletResponse.SC_BAD_REQUEST, connection.getResponseCode(), "Wrong http Code"),
                () -> assertEquals(
                    "{\"code\":9,\"message\":\"Body Field format error : Cannot construct instance of `com.boschat.sikb.model.PersonForCreation` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('email')\\n at [Source: (org.glassfish.jersey.message.internal.ReaderInterceptorExecutor$UnCloseableInputStream); line: 1, column: 1]\"}",
                    getBody(connection.getErrorStream()), "body shouldn't be null")
            );
        }

        @Test
        @DisplayName(" Body Field format error bis")
        void bodyFormatErrorBis() throws Exception {
            String body = "{\"email\": true \"birthDate\": true}";

            HttpURLConnection connection = sendRequest(HttpMethod.POST, POST_URL, body);

            assertAll("Check ",
                () -> assertEquals(HttpServletResponse.SC_BAD_REQUEST, connection.getResponseCode(), "Wrong http Code"),
                () -> assertEquals("{\"code\":8,\"message\":\"The body is invalid\"}", getBody(connection.getErrorStream()), "body shouldn't be null")
            );
        }

        @Test
        @DisplayName(" Missing required Field ")
        void missingRequiredField() throws Exception {
            String body = "{\"email\": \"toto@gmail.com\"}";
            HttpURLConnection connection = sendRequest(HttpMethod.POST, POST_URL, body);

            assertAll("Check ",
                () -> assertEquals(HttpServletResponse.SC_BAD_REQUEST, connection.getResponseCode(), "Wrong http Code"),
                () -> assertEquals("{\"code\":1,\"message\":\"The body field firstName is missing\"}", getBody(connection.getErrorStream()),
                    "body shouldn't be null")
            );
        }
    }
}
