package utils;

import constant.EnvType;

import java.util.Properties;

public class ConfigLoader {

    private Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        String env = System.getProperty("env", String.valueOf(EnvType.QA));
        switch(EnvType.valueOf(env)){
            case QA -> properties = PropertyUtils.propertyLoader("src/test/resources/qa.properties");
            case UAT -> properties = PropertyUtils.propertyLoader("src/test/resources/uat.properties");
            case PROD -> properties = PropertyUtils.propertyLoader("src/test/resources/prod.properties");
        }
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getBaseUrl(){
        String prop = properties.getProperty("baseUrl");
        if(prop != null) return prop;
        else throw new RuntimeException("BASE URL is not found");
    }
}
