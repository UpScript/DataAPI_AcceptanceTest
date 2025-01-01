package configuration;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigurationContext {

    private static final Properties properties = new Properties();
    private static final Logger logger = LogManager.getLogger(ConfigurationContext.class);

    private static final String API_URL = "api_url";

    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    public static final String HOST_URL = readConfigProperty(API_URL);

    public static final String AUTH_USERNAME = readConfigProperty(USER_NAME);
    public static final String AUTH_PASSWORD = readConfigProperty(PASSWORD);


    private static void loadProperties(){

        String env = System.getProperty("env", "qa");
        String fileName = "src/test/resources/" + env + ".properties";

        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + fileName, e);
        }

    }

public static String readConfigProperty(String key) {

        if(properties.isEmpty()) {
            loadProperties();
        }

        String value = properties.getProperty(key);

        if(value==null){
            logger.error("");
            throw new IllegalArgumentException("Couldn't find the configuration property value: "+key);
        }

        return value;
    }
}
