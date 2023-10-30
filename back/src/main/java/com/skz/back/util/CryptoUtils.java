package com.skz.back.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.StringJoiner;

public final class CryptoUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtils.class);

    private static final String PEPPER = "CkVlrpZbGYTvZ5X6v0hVuoGKunKiTnU2OCuNbBuJsFmMqjfEMpKbp1J0KXBm5/kT0/x7keTvRmW2w3u7aNgeew==";

    private CryptoUtils() {
    }

    public static String hashWithPepper(final String... keys) {
        String[] stringsWithPrefix = new String[keys.length + 1];
        stringsWithPrefix[0] = PEPPER;
        System.arraycopy(keys, 0, stringsWithPrefix, 1, keys.length);
        return hash(stringsWithPrefix);
    }

    public static String hash(final String... keys) {
        final String key = Arrays.stream(keys)
                .filter(Objects::nonNull)
                .reduce("", (acc, s) -> acc + s);

        String digest;
        byte[] digestKey;
        MessageDigest sha;
        try {
            digestKey = key.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-512");
            digestKey = sha.digest(digestKey);
            digest = Base64.getEncoder().encodeToString(digestKey);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info(String.format("Error while hashing key %s, %s", key, e.getMessage()));
            digest = key;
        }
        return digest;
    }

    public static String generateRandomSalt(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[length];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
