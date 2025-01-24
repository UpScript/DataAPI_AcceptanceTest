package apiRequestHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static api.EndPoints.GET_PRODUCTS;
import static utils.SupportMethod.*;

public class GetProductDetails {

    public Response getProductDetails (String productId,String jwtToken){

        return ApiRequest.get(
                GET_PRODUCTS+"/"+getProductId(productId), getTokenHeader(jwtToken)
        );
    }
}
