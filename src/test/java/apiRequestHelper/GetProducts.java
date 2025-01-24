package apiRequestHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static api.EndPoints.GET_PRODUCTS;
import static utils.SupportMethod.getTokenHeader;

public class GetProducts {

    public Response getProducts (String jwtToken){

        return ApiRequest.get(
                GET_PRODUCTS, getTokenHeader(jwtToken)
        );
    }
}
