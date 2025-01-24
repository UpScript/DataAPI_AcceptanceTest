package stepDefinitions;

import apiRequestHelper.GetPatientDetails;
import apiResponseHelper.GetPatientsDetailsResponse;
import context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.Assert;

import static apiResponseHelper.GetPatientsDetailsResponse.COMPANY_ID;
import static apiResponseHelper.GetPatientsDetailsResponse.getCompanyId;
import static utils.SupportMethod.*;

public class getPatientDetailsById {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;
    public static String randomPatientId;
    GetPatientDetails GetPatientDetailsById = new GetPatientDetails();
    public static JSONArray DataApiResult;

    @When("the GET GetPatientById patient details request is send")
    public void the_GetPatientById_get_patient_details_request_is_send() {

        String listOfPatientList = scenarioContext.getContext("listOfPatientList").toString();
        randomPatientId = GetRandomPatientId(splitStringToList(listOfPatientList));

        scenarioContext.setContext("randomPatientId", randomPatientId);
        response = GetPatientDetailsById.getPatientDetails(randomPatientId, scenarioContext.getContext("token").toString());

        DataApiResult = getPatientJsonPathDetails(response);
    }

    @Then("the GetPatientById request send successfully with {int} status code")
    public void the_GetPatientById_request_send_successfully_status_code(int statusCode) {

        Assert.assertEquals(response.statusCode(), statusCode);
    }

    @Then("the patient details should be match with patient table data")
    public void the_patient_details_should_be_match_with_patient_table_data() throws InterruptedException {

        long usedPatientId = Long.parseLong(randomPatientId);
        int usedCompanyId = Integer.parseInt(getCompanyId(response, randomPatientId));
        Assert.assertTrue(CompareDataAPIResponseAndDBTableData(getPatientDetailsFromDB(usedCompanyId, usedPatientId), DataApiResult));
    }
}