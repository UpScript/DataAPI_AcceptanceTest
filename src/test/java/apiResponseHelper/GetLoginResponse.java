package apiResponseHelper;

import apiResources.response.TokenResponse;
import io.restassured.response.Response;

public class GetLoginResponse {

    public GetLoginResponse(){}

    public String getLoginResponse(Response response) {

        String jwtToken = null;
        if (response.getStatusCode() == 200) {

            TokenResponse tokenResponse = response.as(TokenResponse.class);
            jwtToken = tokenResponse.getToken();
            System.out.println("Token: " + jwtToken);
        } else {
            System.out.println("Login failed with status code: " + response.getStatusCode());
        }

        return jwtToken;
    }
}
