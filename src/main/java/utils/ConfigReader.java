package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();

    static{
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new RuntimeException("config.properties not found on the classpath");
            }

            props.load(in);
        }
           catch (IOException e){
            throw new RuntimeException("Could not read config.properties", e);
        }
    }

    public static String get(String key) {
        String fromCommandLine = System.getProperty(key);
        return fromCommandLine != null ? fromCommandLine : props.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

}
