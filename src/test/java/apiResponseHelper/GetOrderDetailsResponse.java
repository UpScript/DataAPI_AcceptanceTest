package apiResponseHelper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetOrderDetailsResponse {

    JsonPath jsonPath;
    public static String COMPANY_ID = "";
    static Map<String, String> OrderIdAndCompanyIdDetails = new HashMap<>();

    public String getListOfOrderIds(Response response) {
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

    public Map<String, String> getOrderAndCompanyIds(Response response) throws InterruptedException {

        Map<String, String> orderIdAndCompanyId = new HashMap<>();

        try {

            List<Map<String, Object>> dataList = response.jsonPath().getList("data");

            for (Map<String, Object> data : dataList) {
                Object orderIdObj = data.get("ush-order-id");
                Object companyIdObj = data.get("product-storefront");

                String orderId = (orderIdObj != null) ? orderIdObj.toString() : null;
                String companyId = (companyIdObj != null) ? companyIdObj.toString() : null;

                System.out.println("Extracted orderId: " + orderId);
                System.out.println("Extracted companyId: " + companyId);

                if (orderId != null && companyId != null) {
                    orderIdAndCompanyId.put(orderId, companyId);
                } else {
                    System.out.println("Skipping entry with null patient or company ID: " + data);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Final Map: " + orderIdAndCompanyId);

        OrderIdAndCompanyIdDetails = orderIdAndCompanyId;
        return orderIdAndCompanyId;
    }

    public static String getCompanyIdForOrders(Response response, String patientId) throws InterruptedException {

        return OrderIdAndCompanyIdDetails.get(patientId);
    }
}
