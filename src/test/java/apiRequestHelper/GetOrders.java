package apiRequestHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static api.EndPoints.GET_ORDERS;
import static utils.SupportMethod.getTokenHeader;

public class GetOrders {

    public Response getOrders (String jwtToken){

        return ApiRequest.get(
                GET_ORDERS, getTokenHeader(jwtToken)
        );
    }
}
