package com.boschat.sikb.utils;

import com.boschat.sikb.model.Credentials;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HashUtils {

    public static final int SALT_SIZE = 64;

    public static final String BASIC_SEPARATOR = ":";

    private static final Logger LOGGER = LogManager.getLogger(MapperUtils.class);

    private static final Random RANDOM = new SecureRandom();

    private HashUtils() {

    }

    public static String generateToken() {
        return new String(Base64.getEncoder().encode((UUID.randomUUID().toString() + DateUtils.now().toString()).getBytes()));
    }

    public static String generateSalt() {
        byte[] salt = new byte[SALT_SIZE];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hash(String password, String salt) {
        final byte[] passwordBytes = password.getBytes(UTF_8);
        final byte[] all = ArrayUtils.addAll(passwordBytes, salt.getBytes());
        SHA3.DigestSHA3 md = new SHA3.Digest512();
        md.update(all);
        return Base64.getEncoder().encodeToString(md.digest());
    }

    public static boolean isExpectedPassword(final String password, final String salt, final String currentPassword) {
        final String hashedPassword = hash(password, salt);
        return Objects.equals(hashedPassword, currentPassword);
    }

    public static String basicEncode(String userName, String password) {
        return Base64.getEncoder().encodeToString((userName + BASIC_SEPARATOR + password).getBytes());
    }

    public static Credentials basicDecode(String accessToken) {
        try {
            String decoded = new String(Base64.getDecoder().decode(accessToken));
            String[] values = decoded.split(BASIC_SEPARATOR);
            return new Credentials().login(values[0]).password(values[1]);
        } catch (Throwable e) {
            LOGGER.warn("Error while decoding accessToken : " + accessToken);
        }
        return null;
    }
}
