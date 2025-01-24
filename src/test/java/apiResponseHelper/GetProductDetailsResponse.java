package apiResponseHelper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetProductDetailsResponse {

    JsonPath jsonPath;
    public static String COMPANY_ID = "";
    static Map<String, String> productIdAndCompanyIdDetails = new HashMap<>();

    public String getListOfProductIds(Response response) {
        jsonPath = response.jsonPath();
        return jsonPath.getString("data.ush-order-id");
    }

    public String getListOfCompanyIds(Response response) {
        jsonPath = response.jsonPath();
        return jsonPath.getString("data.product-storefront");
    }

    public List<String> getListOfOrders(Response response) {
        jsonPath = response.jsonPath();
        return jsonPath.getList("data.ush-order-id", String.class);
    }

    public List<String> getListOfCompanies(Response response) {
        jsonPath = response.jsonPath();
        return jsonPath.getList("data.product-storefront", String.class);
    }

    public Map<String, String> getProductAndCompanyIds(Response response) throws InterruptedException {

        Map<String, String> orderIdAndCompanyId = new HashMap<>();

        try {
            for (int i = 0; i < getListOfOrders(response).size(); i++) {
                String patientId = getListOfOrders(response).get(i);
                String companyId = getListOfCompanies(response).get(i);
                orderIdAndCompanyId.put(patientId, companyId);
            }
        } catch (ClassCastException e) {
            System.out.println("Type mismatch detected: " + e.getMessage());
        }

        productIdAndCompanyIdDetails = orderIdAndCompanyId;
        Thread.sleep(60000);
        return orderIdAndCompanyId;
    }

    public static String getCompanyId(Response response, String patientId) throws InterruptedException {

        return productIdAndCompanyIdDetails.get(patientId);
    }
}
