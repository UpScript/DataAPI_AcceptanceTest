package apiRequestHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static api.EndPoints.GET_PATIENTS;
import static utils.SupportMethod.getTokenHeader;

public class GetPatients {

    public Response getPatients (String jwtToken){

        return ApiRequest.get(
                GET_PATIENTS, getTokenHeader(jwtToken)
        );
    }
}
