package configuration;

import java.util.Map;

public class CredentialProvider {

    public static String getEnv() {
        String env = System.getProperty("env");
        if (env == null || env.isEmpty()) {
            throw new IllegalArgumentException("Environment NOT Specified. Use -Denv=QA/UAT/PROD.");
        }
        return env.toUpperCase();
    }

    public static String getUsername(String partner) {
        String env = getEnv();
        String key = partner.toUpperCase() + "_USERNAME";
        return AuthCredentials.CREDENTIALS.getOrDefault(env, Map.of()).get(key);
    }

    public static String getPassword(String partner) {
        String env = getEnv();
        String key = partner.toUpperCase() + "_PASSWORD";
        return AuthCredentials.CREDENTIALS.getOrDefault(env, Map.of()).get(key);
    }
}
