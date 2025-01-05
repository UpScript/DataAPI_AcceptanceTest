package stepDefinitions;

import apiRequestHelper.GetPatientDetails;
import apiResponseHelper.GetPatientsDetailsResponse;
import context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static utils.SupportMethod.*;

public class getDeIdentifiedPatientDetails {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;
    public static String randomPatientId;
    public static GetPatientsDetailsResponse getPatientsDetailsResponse = new GetPatientsDetailsResponse();
    GetPatientDetails GetPatientDetailsById = new GetPatientDetails();
    public static List<Map<String, Object>> actualData;
    public static JSONArray DataApiResult;
    public static Map<String, String> patientIdAndCompanyId;

    @When("the GET deIdentified patient details request is send")
    public void the_deIdentified_get_patient_details_request_is_send() {

        String listOfPatientList = scenarioContext.getContext("listOfPatientList").toString();
        String listOfCompanyList = scenarioContext.getContext("listOfCompanyId").toString();

        randomPatientId = GetRandomPatientId(splitStringToList(listOfPatientList));

        System.out.println("******************************************************************");
        System.out.println("Selected patient ID is: " + randomPatientId);
        System.out.println("******************************************************************");

        scenarioContext.setContext("randomPatientId", randomPatientId);

        response = GetPatientDetailsById.getPatientDetails(randomPatientId, scenarioContext.getContext("token").toString());

        JsonPath jsonPath = response.jsonPath();
        Map<String, Object> dataMap = jsonPath.getMap("data");
        JSONObject dataObject = new JSONObject();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            dataObject.put(entry.getKey(), entry.getValue() != null ? entry.getValue() : JSONObject.NULL);
        }
        JSONArray dataArray = new JSONArray();
        dataArray.put(dataObject);
        DataApiResult = dataArray;
    }

    @Then("the deIdentified request send successfully with {int} status code")
    public void the_request_send_successfully_status_code(int statusCode) {

        Assert.assertEquals(response.statusCode(), statusCode);
    }

    @Then("the deIdentified patient details should be match with deIdentified table data")
    public void the_deIdentified_patient_details_should_be_match_with_deIdentified_table_data() {

        long usedPatientId = Long.parseLong(randomPatientId);
//        int usedCompanyId = Integer.parseInt(getPatientsDetailsResponse.getPatientAndCompanyIds(response).get(randomPatientId));
        Assert.assertTrue(CompareDataAPIResponseAndDBTableData(getPatientDetailsFromDB(4826978, usedPatientId), DataApiResult));
    }
}