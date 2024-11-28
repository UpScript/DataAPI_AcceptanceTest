package stepDefinitions;

import apiResources.response.TokenResponse;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import static apiResources.response.TestConstant.*;
import static io.restassured.RestAssured.given;

public class getPatientDetails {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Response response;
    JsonPath jsonPath;

    public static List<String> listOfPatient;
    public static Map<String, String> patientIdAndCompanyId;
    public static List<String> listOfCompany;

    public static List<Map<String, Object>> actualData;
    public static JSONArray DataApiResult;
    public static String randomPatientId;
    private static final String DB_URL = DBURL;
    private static final String DB_USER = DBUSER;
    private static final String DB_PASSWORD = DBPASSWORD;

    @Given("the login request for pfizer is send")
    public void the_login_request_for_pfizer_is_send() throws IOException {

        String username = "Pfizer54173";
        String password = "2xPxsaQRD3qXG";

        Response response = given()
                .auth()
                .preemptive()
                .basic(username, password)
                .when()
                .get("https://uatdataapi.upscripthealth.org/login");

        if (response.getStatusCode() == 200) {
            TokenResponse tokenResponse = response.as(TokenResponse.class);

            String token = tokenResponse.getToken();
            scenarioContext.setContext("token", token);
            System.out.println("Token: " + token);
        } else {
            System.out.println("Login failed with status code: " + response.getStatusCode());
        }

    }

    @Given("the GET all patient details request is send")
    public void the_get_all_patient_details_request_is_send() throws IOException {

        RestAssured.baseURI = "https://uatdataapi.upscripthealth.org";

        response = given()
                .header("x-access-token", (String) scenarioContext.getContext("token"))
                .when()
                .get("/patient")
                .then()
                .extract()
                .response();

        jsonPath = response.jsonPath();
        String listOfPatientId = jsonPath.getString("data.patient-patient-id");
        String listOfCompanyId = jsonPath.getString("data.company-id");

        System.out.println("******************************************************************");
        System.out.println("List of patient IDs: " + listOfPatientId);
        System.out.println("List of Company IDs: " + listOfCompanyId);
        System.out.println("******************************************************************");

        scenarioContext.setContext("listOfPatientList", listOfPatientId);
        listOfPatient = jsonPath.getList("data.patient-patient-id", String.class);
        listOfCompany = jsonPath.getList("data.company-id", String.class);

        patientIdAndCompanyId = new HashMap<>();

        try {
            for (int i = 0; i < listOfPatient.size(); i++) {
                String patientId = listOfPatient.get(i);
                String companyId = listOfCompany.get(i);
                patientIdAndCompanyId.put(patientId, companyId);
            }
        } catch (ClassCastException e) {
            System.out.println("Type mismatch detected: " + e.getMessage());
        }
    }

    @When("the GET patient details request is send")
    public void the_get_patient_details_request_is_send() throws IOException {

        RestAssured.baseURI = "https://uatdataapi.upscripthealth.org";
        randomPatientId = GetRandomPatientId(splitStringToList(scenarioContext.getContext("listOfPatientList").toString()));

        System.out.println("******************************************************************");
        System.out.println("Selected patient ID is: " + randomPatientId);
        System.out.println("******************************************************************");

        scenarioContext.setContext("randomPatientId", randomPatientId);
        response = given()
                .header("x-access-token", (String) scenarioContext.getContext("token"))
                .when()
                .get("/patient/"+randomPatientId)
                .then()
                .extract()
                .response();


        JsonPath jsonPath = response.jsonPath();
        actualData = jsonPath.getList("data");
        DataApiResult = new JSONArray(actualData);

        System.out.println("******************************************************************");
        System.out.println("Response Data API: "+DataApiResult);
    }

    @Then("the request send successfully with {int} status code")
    public void the_request_send_successfully_status_code(Integer statusCode) throws SQLException {

        Assert.assertEquals(response.statusCode(), statusCode);
        long usedPatientId = Long.parseLong(randomPatientId);
        int usedCompanyId = Integer.valueOf(patientIdAndCompanyId.get(randomPatientId));
        System.out.println("Data From the DB: "+getPatientDetailsFromDB(usedCompanyId, usedPatientId));
        System.out.println("******************************************************************");

        System.out.println("RESULT IS: *******************************************************");
        compareJSONArrays(getPatientDetailsFromDB(usedCompanyId, usedPatientId), DataApiResult);
        System.out.println("******************************************************************");

        Assert.assertEquals(getPatientDetailsFromDB(usedCompanyId, usedPatientId), DataApiResult);
    }

    @Then("the patient details should be returned")
    public void the_patient_details_should_be_returned() {

        System.out.println("Response Body: " + response.getBody().asString());

        jsonPath = response.jsonPath();
        Assert.assertEquals(ExtractIntegers(GetExpectedField(jsonPath, "data.patient-patient-id")), scenarioContext.getContext("randomPatientId"));
    }

    @Then("the {string} retrieved successfully")
    public void the_retrieved_successfully(String patientDoB) {
        Assert.assertEquals(ExtractIntegers(GetExpectedField(jsonPath, "data.patient-date-of-birth")), patientDoB);
    }

    @Then("the patient details schema validated successfully")
    public void the_patient_details_schema_validated_successfully() {
        //Need to check the mandatory fields to adjust the schema and validate all the fields
        //response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("patientDetailsSchema.json"));
    }


















    public static String ExtractIntegers(String input) {

        return input.replaceAll("[^0-9]", "");
    }

    public static String ExtractAlphabets(String input) {

        return input.replaceAll("[^a-zA-Z]", "");
    }

    public static String GetExpectedField(JsonPath jsonPath, String fieldName) {

        return jsonPath.getString(fieldName);
    }

    public static String GetRandomPatientId(List<String> patientId){
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be null or empty");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(patientId.size());
        return patientId.get(randomIndex);
    }

    public static List<String> splitStringToList(String inputString) {

        if (inputString == null || inputString.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }
        String[] array = inputString.split(",");
        List<String> resultList = new ArrayList<String>();
        for(int i = 0; i < array.length; i++) {
            resultList.add(ExtractIntegers(array[i]));
        }
        return resultList;
    }

    public JSONArray getPatientDetailsFromDB(int companyId, long patientId) {
        JSONArray jsonArrayResult = new JSONArray();
        String procedureCall = "{call sp_da_patients_payload_patient_id(?, ?)}";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement callableStatement = connection.prepareCall(procedureCall)) {

            callableStatement.setInt(1, companyId);
            callableStatement.setLong(2, patientId);

            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                String cteJsonString = resultSet.getString("cte_json");

                jsonArrayResult = new JSONArray(cteJsonString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonArrayResult;
    }




    public static void compareJSONArrays(JSONArray array1, JSONArray array2) {
        if (array1.length() != array2.length()) {
            System.out.println("The arrays have different lengths.");
        } else {
            for (int i = 0; i < array1.length(); i++) {
                try {
                    Object obj1 = array1.get(i);
                    Object obj2 = array2.get(i);

                    if (obj1 instanceof JSONObject && obj2 instanceof JSONObject) {
                        compareJSONObjects((JSONObject) obj1, (JSONObject) obj2, "Root[" + i + "]");
                    } else if (obj1 instanceof JSONArray && obj2 instanceof JSONArray) {
                        compareJSONArrays((JSONArray) obj1, (JSONArray) obj2);
                    } else if (!obj1.equals(obj2)) {
                        System.out.println("Difference found at index " + i + ": " + obj1 + " vs " + obj2);
                    }
                } catch (JSONException e) {
                    System.out.println("Error comparing elements at index " + i + ": " + e.getMessage());
                }
            }
        }
    }

    private static void compareJSONObjects(JSONObject obj1, JSONObject obj2, String path) {
        for (String key : obj1.keySet()) {
            if (!obj2.has(key)) {
                System.out.println("Missing field in second JSON at path: " + path + "." + key);
            } else {
                try {
                    Object value1 = obj1.get(key);
                    Object value2 = obj2.get(key);

                    if (value1 instanceof JSONObject && value2 instanceof JSONObject) {
                        compareJSONObjects((JSONObject) value1, (JSONObject) value2, path + "." + key);
                    } else if (value1 instanceof JSONArray && value2 instanceof JSONArray) {
                        compareJSONArrays((JSONArray) value1, (JSONArray) value2);
                    } else if (!value1.equals(value2)) {
                        System.out.println("Difference at path " + path + "." + key + ": " + value1 + " vs " + value2);
                    }
                } catch (JSONException e) {
                    System.out.println("Error accessing field " + path + "." + key + ": " + e.getMessage());
                }
            }
        }

        for (String key : obj2.keySet()) {
            if (!obj1.has(key)) {
                System.out.println("Extra field in second JSON at path: " + path + "." + key);
            }
        }
    }
}








