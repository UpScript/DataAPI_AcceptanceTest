package apiHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static api.EndPoints.GET_LOGIN;
import static utils.SupportMethod.getAuthPassword;
import static utils.SupportMethod.getAuthUserName;

public class Login {

    public Response getLogin (String vendor){

        return ApiRequest.get(
                getAuthUserName(vendor),
                getAuthPassword(vendor),
                "",
                "",
                GET_LOGIN
        );
    }
}
