package rongxchen.investment.util;

import lombok.extern.java.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log
public class EncUtil {

    private EncUtil() {}

    public static String calculateSHA512(String input) {
        return calculate(input, "SHA-512");
    }

    public static String calculateSHA256(String input) {
        return calculate(input, "SHA-256");
    }

    public static String calculateMD5(String input) {
        return calculate(input, "MD5");
    }

    private static String calculate(String input, String method) {
        try {
            MessageDigest digest = MessageDigest.getInstance(method);
            byte[] hash = digest.digest(input.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}
