package configuration;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.InputStream;
import java.util.Properties;


public class ConfigurationContext {

    private static final Properties properties = new Properties();
    private static final Logger logger = LogManager.getLogger(ConfigurationContext.class);


    //Configuration Keys
    private static final String CONFIG_KEY = "ENV"; // THIS WILL TAKEN FROM MAVEN CONFIG -Denv=env_aurora_sit

    private static final String API_URL = "ui_url"; //this is from environment property files.

    public static final String ENV = readConfigPropertyVar(CONFIG_KEY);
    public static final String HOST_URL = readConfigPropertyFile(API_URL);



    private static void loadProperties(){

        Resource propertyFile = new ClassPathResource(ENV + ".properties");
        try(InputStream inputStream = propertyFile.getInputStream()){
            properties.load(inputStream);
        }catch(Exception FileNotFoundException){
            logger.error("Could not load properties");
        }

    }

    private static String  readConfigPropertyFile(String key){

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

    private static String readConfigPropertyVar(String key){

        String value = System.getProperty(key);
        if(value==null){
            logger.error("");
            throw new IllegalArgumentException("Couldn't find the configuration property value: "+key);
        }

        return value;
    }
}
