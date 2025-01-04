package stepDefinitions;

import apiRequestHelper.GetPatientDetails;
import context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static utils.SupportMethod.*;

public class getDeIdentifiedPatientDetails {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;
    public static String randomPatientId;

    GetPatientDetails GetPatientDetailsById = new GetPatientDetails();
    public static List<Map<String, Object>> actualData;
    public static JSONArray DataApiResult;
    public static Map<String, String> patientIdAndCompanyId;

    @When("the GET deIdentified patient details request is send")
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

        JsonPath jsonPath = response.jsonPath();
        actualData = jsonPath.getList("data");
        DataApiResult = new JSONArray(actualData);

        System.out.println("******************************************************************");
        System.out.println("Response Data API: "+DataApiResult);


    }

    @Then("the deIdentified request send successfully with {int} status code")
    public void the_request_send_successfully_status_code(int statusCode) {

        Assert.assertEquals(response.statusCode(), statusCode);
    }

    @Then("the deIdentified patient details should be match with deIdentified table data")
    public void the_deIdentified_patient_details_should_be_match_with_deIdentified_table_data() {

        long usedPatientId = Long.parseLong(randomPatientId);
        int usedCompanyId = Integer.valueOf(patientIdAndCompanyId.get(randomPatientId));
        Assert.assertEquals(getPatientDetailsFromDB(usedCompanyId, usedPatientId), DataApiResult);
    }
}