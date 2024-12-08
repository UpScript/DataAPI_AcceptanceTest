package utils;

import io.restassured.http.Header;
import io.restassured.http.Headers;

import static configuration.TestConstant.*;

public class SupportMethod {

    public static Headers getTokenHeader(String jwtToken){

        Header header = new Header("x-access-token", jwtToken);
        return new Headers(header);
    }

    public static String getPatientId(String patientId){
        return patientId;
    }

    public static String getAuthUserName(String vendor){

        String userName = "";

        switch (vendor){
            case "pfizer":
            userName = PFIZER_AUTH_USERNAME;

            case "lombard":
                userName = LOMBARD_AUTH_USERNAME;

            case "bosley":
                userName = BOSLEY_AUTH_USERNAME;

            case "nerivio":
                userName = NERIVIO_AUTH_USERNAME;

            default:
                userName = vendor+"NOT FOUND";
        }

        return userName;
    }

    public static String getAuthPassword(String vendor){

        String password = "";

        switch (vendor){
            case "pfizer":
                password = PFIZER_AUTH_PASSWORD;

            case "lombard":
                password = LOMBARD_AUTH_PASSWORD;

            case "bosley":
                password = BOSLEY_AUTH_PASSWORD;

            case "nerivio":
                password = NERIVIO_AUTH_PASSWORD;

            default:
                password = vendor+"NOT FOUND";
        }

        return password;
    }
}
