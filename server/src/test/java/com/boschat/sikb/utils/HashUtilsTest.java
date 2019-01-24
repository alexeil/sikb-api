package com.boschat.sikb.utils;

import com.boschat.sikb.model.Credentials;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.boschat.sikb.utils.HashUtils.basicDecode;
import static com.boschat.sikb.utils.HashUtils.basicEncode;
import static com.boschat.sikb.utils.HashUtils.generateSalt;
import static com.boschat.sikb.utils.HashUtils.hash;
import static com.boschat.sikb.utils.HashUtils.isExpectedPassword;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName(" Check hash : ")
class HashUtilsTest {

    private static final String LOGIN = "Admin";

    private static final String PASSWORD = "test";

    private static final String FIRST_SALT = generateSalt();

    private static final String FIRST_HASH = hash(PASSWORD, FIRST_SALT);

    private static final String SECOND_SALT = generateSalt();

    private static final String SECOND_HASH = hash(PASSWORD, SECOND_SALT);

    @Test
    @DisplayName(" generate Hash ")
    void generateHash() {
        final String hash = hash(PASSWORD, FIRST_SALT);
        assertNotNull(hash, "Hash shouldn't be null");
    }

    @Test
    @DisplayName(" expected password ")
    void expectedPassword() {
        assertTrue(isExpectedPassword(PASSWORD, FIRST_SALT, FIRST_HASH), "Wrong password");
        assertFalse(isExpectedPassword(PASSWORD, FIRST_SALT, SECOND_HASH), "Wrong password");
    }

    @Test
    @DisplayName(" basicEncode ")
    void basicTest() {
        final String hash = basicEncode(LOGIN, PASSWORD);
        assertNotNull(hash, "Hash shouldn't be null");

        Credentials login = basicDecode(hash);
        assertNotNull(login, "login shouldn't be null");

        assertEquals(LOGIN, login.getLogin(), "Wrong login");
        assertEquals(PASSWORD, login.getPassword(), "Wrong password");
    }
}
