package stepDefinitions;

import apiRequestHelper.GetOrders;
import apiRequestHelper.GetProducts;
import apiResponseHelper.GetOrderDetailsResponse;
import apiResponseHelper.GetProductDetailsResponse;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;

import java.util.Map;

public class getProductDetails {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;

    public static Map<String, String> productIdAndCompanyId;

    GetProducts GetProductList = new GetProducts();
    GetProductDetailsResponse GetProductResponse = new GetProductDetailsResponse();

    @Given("the GET all product details request is send")
    public void the_get_all_product_details_request_is_send() throws InterruptedException {

        String jwtToken = scenarioContext.getContext("token").toString();

        response = GetProductList.getProducts(jwtToken);
        String listOfProductId = GetProductResponse.getListOfProductIds(response);
        String listOfCompanyId = GetProductResponse.getListOfCompanyIds(response);
        productIdAndCompanyId = GetProductResponse.getProductAndCompanyIds(response);
        scenarioContext.setContext("ProductList", listOfProductId);
        scenarioContext.setContext("listOfCompanyId", listOfCompanyId);
    }
}