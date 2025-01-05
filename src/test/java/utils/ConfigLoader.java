package utils;

import constant.EnvType;
import context.ScenarioContext;

import java.util.Properties;

public class ConfigLoader {

    private Properties properties;
    private static ConfigLoader configLoader;
    ScenarioContext scenarioContext = ScenarioContext.getInstance();

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
        String prop = properties.getProperty("api_url");
        if(prop != null) return prop;
        else throw new RuntimeException("API HOST URL is not found");
    }
}
