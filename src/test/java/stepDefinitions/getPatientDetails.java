package stepDefinitions;

import apiRequestHelper.GetPatients;
import apiResponseHelper.GetPatientsDetailsResponse;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;

import java.util.Map;

public class getPatientDetails {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;

    public static Map<String, String> patientIdAndCompanyId;

    GetPatients GetPatientsList = new GetPatients();
    GetPatientsDetailsResponse GetPatientsResponse = new GetPatientsDetailsResponse();

    @Given("the GET all patient details request is send")
    public void the_get_all_patient_details_request_is_send() throws InterruptedException {

        String jwtToken = scenarioContext.getContext("token").toString();

        response = GetPatientsList.getPatients(jwtToken);
        String listOfPatientId = GetPatientsResponse.getListOfPatientsIds(response);
        String listOfCompanyId = GetPatientsResponse.getListOfCompanyIds(response);
        patientIdAndCompanyId = GetPatientsResponse.getPatientAndCompanyIds(response);
        scenarioContext.setContext("listOfPatientList", listOfPatientId);
        scenarioContext.setContext("listOfCompanyId", listOfCompanyId);
    }
}