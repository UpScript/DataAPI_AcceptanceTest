package stepDefinitions;

import apiRequestHelper.GetOrderDetails;
import apiRequestHelper.GetProductDetails;
import context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.Assert;

import static apiResponseHelper.GetPatientsDetailsResponse.getCompanyId;
import static utils.SupportMethod.*;

public class getProductDetailsById {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;
    public static String randomProductId;
    GetProductDetails GetProductDetailsById = new GetProductDetails();
    public static JSONArray DataApiResult;

    @When("the GET GetProductById product details request is send")
    public void the_GetProductById_get_order_details_request_is_send() {

        String listOfProductList = scenarioContext.getContext("ProductList").toString();
        randomProductId = GetRandomOrderId(splitStringToList(listOfProductList));

        scenarioContext.setContext("randomPatientId", randomProductId);
        response = GetProductDetailsById.getProductDetails(randomProductId, scenarioContext.getContext("token").toString());

        DataApiResult = getPatientJsonPathDetails(response);
    }

    @Then("the GetProductById request send successfully with {int} status code")
    public void the_GetPatientById_request_send_successfully_status_code(int statusCode) {

        Assert.assertEquals(response.statusCode(), statusCode);
    }

    @Then("the product details should be match with product table data")
    public void the_patient_details_should_be_match_with_patient_table_data() throws InterruptedException {

        long usedOrderId = Long.parseLong(randomProductId);
        int usedCompanyId = Integer.parseInt(getCompanyId(response, randomProductId));
        Assert.assertTrue(CompareDataAPIResponseAndDBTableData(getOrderDetailsFromDB(usedCompanyId, usedOrderId), DataApiResult));
    }
}