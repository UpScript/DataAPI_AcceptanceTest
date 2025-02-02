package apiRequestHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static api.EndPoints.GET_LOGIN;

public class Login {

    public Response getLogin (String username, String password){

        return ApiRequest.get(
                GET_LOGIN,
                username,
                password
        );
    }
}
