package apiHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static utils.SupportMethod.getTokenHeader;

public class GetOrderDetails {

    public Response getOrderDetails (String jwtToken){

        return ApiRequest.get(
                "", getTokenHeader(jwtToken)
        );
    }
}
