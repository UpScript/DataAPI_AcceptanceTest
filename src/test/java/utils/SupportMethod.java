package utils;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static configuration.TestConstant.*;
import static constant.TestConstant.*;

public class SupportMethod {

    public static Headers getTokenHeader(String jwtToken){

        Header header = new Header("x-access-token", jwtToken);
        return new Headers(header);
    }

    public static String getPatientId(String patientId){
        return patientId;
    }

    public static String getAuthUserName(String vendor){

        return switch (vendor) {
            case "pfizer" -> PFIZER_AUTH_USERNAME;
            case "lombard" -> LOMBARD_AUTH_USERNAME;
            case "bosley" -> BOSLEY_AUTH_USERNAME;
            case "nerivio" -> NERIVIO_AUTH_USERNAME;
            default -> vendor + "NOT FOUND";
        };
    }

    public static String getAuthPassword(String vendor){

        return switch (vendor) {
            case "pfizer" -> PFIZER_AUTH_PASSWORD;
            case "lombard" -> LOMBARD_AUTH_PASSWORD;
            case "bosley" -> BOSLEY_AUTH_PASSWORD;
            case "nerivio" -> NERIVIO_AUTH_PASSWORD;
            default -> vendor + "NOT FOUND";
        };
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
        for (String s : array) {
            resultList.add(ExtractIntegers(s));
        }
        return resultList;
    }

    private static final String DB_URL = DBURL;
    private static final String DB_USER = DBUSER;
    private static final String DB_PASSWORD = DBPASSWORD;

    public static JSONArray getPatientDetailsFromDB(int companyId, long patientId) {
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
