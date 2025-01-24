package stepDefinitions;

import apiRequestHelper.GetOrderDetails;
import context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.Assert;

import static apiResponseHelper.GetPatientsDetailsResponse.getCompanyId;
import static utils.SupportMethod.*;

public class getOrderDetailsById {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;
    public static String randomOrderId;
    GetOrderDetails GetOrderDetailsById = new GetOrderDetails();
    public static JSONArray DataApiResult;

    @When("the GET GetOrderById order details request is send")
    public void the_GetOrderById_get_order_details_request_is_send() {

        String listOfOrderList = scenarioContext.getContext("OrderList").toString();
        randomOrderId = GetRandomOrderId(splitStringToList(listOfOrderList));

        scenarioContext.setContext("randomPatientId", randomOrderId);
        response = GetOrderDetailsById.getOrderDetails(randomOrderId, scenarioContext.getContext("token").toString());

        DataApiResult = getPatientJsonPathDetails(response);
    }

    @Then("the GetOrderById request send successfully with {int} status code")
    public void the_GetPatientById_request_send_successfully_status_code(int statusCode) {

        Assert.assertEquals(response.statusCode(), statusCode);
    }

    @Then("the order details should be match with order table data")
    public void the_patient_details_should_be_match_with_patient_table_data() throws InterruptedException {

        long usedOrderId = Long.parseLong(randomOrderId);
        int usedCompanyId = Integer.parseInt(getCompanyId(response, randomOrderId));
        Assert.assertTrue(CompareDataAPIResponseAndDBTableData(getOrderDetailsFromDB(usedCompanyId, usedOrderId), DataApiResult));
    }
}