package com.boschat.sikb.utils;

import com.boschat.sikb.configuration.ConfigLoader;
import com.boschat.sikb.exceptions.TechnicalException;
import com.boschat.sikb.servlet.ReloadServlet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.subethamail.wiser.Wiser;

import java.io.IOException;

import static com.boschat.sikb.AbstractTest.checkEmailWithWiser;
import static com.boschat.sikb.AbstractTest.findRandomOpenPortOnAllLocalInterfaces;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_DEBUG;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_DEFAULT_RECIPIENT;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_HOST;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_PORT;
import static com.boschat.sikb.configuration.EnvVar.CONFIG_TECH_PATH;
import static com.boschat.sikb.utils.MailUtils.EMAIL_TITLE_CREATE_USER;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName(" email service ")
class MailUtilsTest {

    private static final String DEFAULT_RECIPIENT = "defaultRecipient@kin-ball.fr";

    private static final String RECIPIENT = "test@kin-ball.fr";

    private static final String TOKEN = "MyFakeToken";

    private static Wiser wiser;

    @BeforeAll
    static void beforeAll() throws IOException {
        System.setProperty(CONFIG_TECH_PATH.getEnv(), "src/main/resources");
        ReloadServlet.reloadProperties();
        ConfigLoader.getInstance().setProperties(SMTP_PORT, findRandomOpenPortOnAllLocalInterfaces().toString());
        wiser = new Wiser(SMTP_PORT.getIntegerValue());
        wiser.start();
    }

    @AfterAll
    static void afterAll() {
        wiser.stop();
    }

    @AfterEach
    void reset() {
        MailUtils.reset();
    }

    @Test
    @DisplayName(" send an email for a user creation ")
    void sendEmailCreateUserWithoutDefaultRecipient() {
        ConfigLoader.getInstance().setProperties(SMTP_DEBUG, "true");
        ConfigLoader.getInstance().setProperties(SMTP_DEFAULT_RECIPIENT, "");
        ConfigLoader.getInstance().setProperties(SMTP_HOST, "localhost");
        MailUtils.getInstance().sendCreateUserEmail(RECIPIENT, TOKEN);
        checkEmailWithWiser(wiser, RECIPIENT, EMAIL_TITLE_CREATE_USER);
    }

    @Test
    @DisplayName(" send an email for a user creation ")
    void sendEmailCreateUserWithDefaultRecipient() {
        ConfigLoader.getInstance().setProperties(SMTP_DEBUG, "false");
        ConfigLoader.getInstance().setProperties(SMTP_DEFAULT_RECIPIENT, DEFAULT_RECIPIENT);
        ConfigLoader.getInstance().setProperties(SMTP_HOST, "localhost");
        MailUtils.getInstance().sendCreateUserEmail(RECIPIENT, TOKEN);
        checkEmailWithWiser(wiser, DEFAULT_RECIPIENT, EMAIL_TITLE_CREATE_USER);
    }

    @Test
    @DisplayName(" unknown host ")
    void unknownHost() {
        ConfigLoader.getInstance().setProperties(SMTP_HOST, "fooTheSecretHost");
        Executable sendMail = () -> MailUtils.getInstance().sendCreateUserEmail(RECIPIENT, TOKEN);
        assertThrows(TechnicalException.class, sendMail, "a TechnicalException should have been thrown");
    }
}
