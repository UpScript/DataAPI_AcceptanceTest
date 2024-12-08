package apiHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static utils.SupportMethod.getTokenHeader;

public class GetOrders {

    public Response getOrders (String jwtToken){

        return ApiRequest.get(
                "", getTokenHeader(jwtToken)
        );
    }
}
