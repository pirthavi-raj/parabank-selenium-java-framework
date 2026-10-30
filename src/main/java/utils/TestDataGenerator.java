package utils;

import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    private static final String DEFAULT_PASSWORD = "Test@1234";

    // Example result: pb_1761820894123_482
    public static String uniqueUsername(){
         int random = ThreadLocalRandom.current().nextInt(100, 1000);
         return "pb_" +System.currentTimeMillis() + "_" +random;
    }

    public static String randomSsn(){
        return String.valueOf(ThreadLocalRandom.current().nextLong(100000000L, 999999999L));
    }

    // A complete valid user for the registration form
    public static User createUser(){
        return new User(
                "Test",
                "User",
                "12 Main Street",
                "Wurzburg",
                "Bavaria",
                "97070",
                "0123456789",
                randomSsn(),
                uniqueUsername(),
                DEFAULT_PASSWORD
        );


    }
}
