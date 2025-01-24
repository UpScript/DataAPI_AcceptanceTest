package apiRequestHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static api.EndPoints.GET_ORDERS;
import static utils.SupportMethod.*;

public class GetOrderDetails {

    public Response getOrderDetails (String orderId,String jwtToken){

        return ApiRequest.get(
                GET_ORDERS+"/"+getOrderId(orderId), getTokenHeader(jwtToken)
        );
    }
}
