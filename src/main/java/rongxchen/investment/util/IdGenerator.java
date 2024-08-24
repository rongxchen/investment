package rongxchen.investment.util;

import java.util.UUID;

public class IdGenerator {

    private IdGenerator() {}

    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    public static String UUID(String prefix) {
        return prefix + "-" + UUID();
    }

}
