package apiRequestHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static api.EndPoints.GET_LOGIN;

public class Login {

    public Response getLogin (String username, String password){

        Response response = ApiRequest.get(
                GET_LOGIN,
                username,
                password
        );
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to LogIn" +
                    ", HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }
}
