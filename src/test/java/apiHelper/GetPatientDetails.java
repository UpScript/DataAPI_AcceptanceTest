package apiHelper;

import api.ApiRequest;
import io.restassured.response.Response;

import static api.EndPoints.GET_PATIENTS;
import static utils.SupportMethod.getPatientId;
import static utils.SupportMethod.getTokenHeader;

public class GetPatientDetails {

    public Response getPatientDetails (String patientId, String jwtToken){

        return ApiRequest.get(
                GET_PATIENTS+"/"+getPatientId(patientId), getTokenHeader(jwtToken)
        );
    }
}
