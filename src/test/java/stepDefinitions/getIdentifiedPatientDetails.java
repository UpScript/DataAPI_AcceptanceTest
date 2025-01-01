package stepDefinitions;

import apiRequestHelper.GetPatientDetails;
import context.ScenarioContext;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Map;

import static utils.SupportMethod.GetRandomPatientId;
import static utils.SupportMethod.splitStringToList;

public class getIdentifiedPatientDetails {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;

    public static String randomPatientId;
    GetPatientDetails GetPatientDetailsById = new GetPatientDetails();

    @When("the GET identified patient details request is send")
    public void the_get_patient_details_request_is_send() {

        String listOfPatientList = scenarioContext.getContext("listOfPatientList").toString();
        randomPatientId = GetRandomPatientId(splitStringToList(listOfPatientList));

        System.out.println("******************************************************************");
        System.out.println("Selected patient ID is: " + randomPatientId);
        System.out.println("******************************************************************");

        scenarioContext.setContext("randomPatientId", randomPatientId);

        response = GetPatientDetailsById.getPatientDetails(randomPatientId, scenarioContext.getContext("token").toString());

        System.out.println("******************************************************************");
        System.out.println("Response Data API: "+response.getBody().asString());

//        JsonPath jsonPath = response.jsonPath();
//        actualData = jsonPath.getList("data");
//        DataApiResult = new JSONArray(actualData);
//
//        System.out.println("******************************************************************");
//        System.out.println("Response Data API: "+DataApiResult);
    }
}