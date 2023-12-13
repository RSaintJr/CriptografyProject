package DTO;

import java.security.SecureRandom;
import java.util.Arrays;

public class KeyGenerator {

    public static byte[] generateAesKey(int length) {
        if (length != 16 && length != 24 && length != 32) {
            throw new IllegalArgumentException("Invalid AES key length");
        }

        byte[] key = new byte[length];
        new SecureRandom().nextBytes(key);
        return key;
    }
}
