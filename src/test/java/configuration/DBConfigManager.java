package configuration;

import java.util.HashMap;
import java.util.Map;

import static constant.DataBaseCredentials.*;

public class DBConfigManager {

    private final Map<String, Map<String, DBCredentials>> credentialsMap = new HashMap<>();

    public DBConfigManager() {

        initializeCredentials();
    }

    private void initializeCredentials() {

        Map<String, DBCredentials> qaCredentials = new HashMap<>();
        qaCredentials.put("pfizer", new DBCredentials(DEIDENTIFIED_DB_URL_UAT, DEIDENTIFIED_DB_USER_UAT, DEIDENTIFIED_DB_PASSWORD_UAT));
        qaCredentials.put("lombard", new DBCredentials("qa-identified-db-url", "qa-identified-user", "qa-identified-pass"));
        qaCredentials.put("bosley", new DBCredentials("qa-identified-db-url", "qa-identified-user", "qa-identified-pass"));
        qaCredentials.put("nerivio", new DBCredentials("qa-identified-db-url", "qa-identified-user", "qa-identified-pass"));

        credentialsMap.put("QA", qaCredentials);

        Map<String, DBCredentials> uatCredentials = new HashMap<>();
        uatCredentials.put("pfizer", new DBCredentials(DEIDENTIFIED_DB_URL_UAT, DEIDENTIFIED_DB_USER_UAT, DEIDENTIFIED_DB_PASSWORD_UAT));
        uatCredentials.put("lombard", new DBCredentials(IDENTIFIED_DB_URL_UAT, IDENTIFIED_DB_USER_UAT, IDENTIFIED_DB_PASSWORD_UAT));
        uatCredentials.put("bosley", new DBCredentials(IDENTIFIED_DB_URL_UAT, IDENTIFIED_DB_USER_UAT, IDENTIFIED_DB_PASSWORD_UAT));
        uatCredentials.put("nerivio", new DBCredentials(IDENTIFIED_DB_URL_UAT, IDENTIFIED_DB_USER_UAT, IDENTIFIED_DB_PASSWORD_UAT));

        credentialsMap.put("UAT", uatCredentials);
    }

    public DBCredentials getCredentials(String environment, String partner) {
        Map<String, DBCredentials> envCredentials = credentialsMap.get(environment);
        if (envCredentials == null) {
            throw new IllegalArgumentException("Invalid environment: " + environment);
        }

        DBCredentials credentials = envCredentials.get(partner);
        if (credentials == null) {
            throw new IllegalArgumentException("No credentials found for partner: " + partner + " in environment: " + environment);
        }

        return credentials;
    }
}
