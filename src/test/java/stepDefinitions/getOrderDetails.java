package stepDefinitions;

import apiRequestHelper.GetOrders;
import apiResponseHelper.GetOrderDetailsResponse;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;

import java.util.Map;

public class getOrderDetails {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;

    public static Map<String, String> orderIdAndCompanyId;

    GetOrders GetOrdersList = new GetOrders();
    GetOrderDetailsResponse GetOrderResponse = new GetOrderDetailsResponse();

    @Given("the GET all order details request is send")
    public void the_get_all_order_details_request_is_send() throws InterruptedException {

        String jwtToken = scenarioContext.getContext("token").toString();

        response = GetOrdersList.getOrders(jwtToken);
        String listOfOrderId = GetOrderResponse.getListOfOrderIds(response);
        String listOfCompanyId = GetOrderResponse.getListOfCompanyIds(response);
        orderIdAndCompanyId = GetOrderResponse.getOrderAndCompanyIds(response);
        scenarioContext.setContext("OrderList", listOfOrderId);
        scenarioContext.setContext("listOfCompanyId", listOfCompanyId);
    }
}