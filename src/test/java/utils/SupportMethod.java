package utils;

import configuration.DBConfigManager;
import configuration.DBCredentials;
import context.ScenarioContext;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

public class SupportMethod {

    static ScenarioContext scenarioContext = ScenarioContext.getInstance();

    public static Headers getTokenHeader(String jwtToken) {

        Header header = new Header("x-access-token", jwtToken);
        return new Headers(header);
    }

    public static String getPatientId(String patientId) {
        return patientId;
    }

    public static String getOrderId(String orderId) {
        return orderId;
    }

    public static String getProductId(String productId) {
        return productId;
    }

    public String getPartnerUserName(String partner) {
        String userName = "";
        if (Objects.equals(scenarioContext.getContext("token").toString(), "QA")) {

        } else if (Objects.equals(scenarioContext.getContext("token").toString(), "UAT")) {
        } else {

        }
        return userName;
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

    public static String GetRandomPatientId(List<String> patientId) {

        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be null or empty");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(patientId.size());
        String randomPatientId = patientId.get(randomIndex);
        System.out.println("Random Patient ID is: " + randomPatientId);
        return randomPatientId;
    }

    public static String GetRandomOrderId(List<String> orderId) {

        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be null or empty");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(orderId.size());
        String randomOrderId = orderId.get(randomIndex);
        System.out.println("Random Order ID is: " + randomOrderId);
        return randomOrderId;
    }

    public static String GetRandomProductId(List<String> productId) {

        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be null or empty");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(productId.size());
        String randomOrderId = productId.get(randomIndex);
        System.out.println("Random Order ID is: " + randomOrderId);
        return randomOrderId;
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

    private static String DB_URL = "";
    private static String DB_USER = "";
    private static String DB_PASSWORD = "";

    static DBConfigManager configManager = new DBConfigManager();

    public static void setDataBaseCredentials() {

        String partner = scenarioContext.getContext("partner").toString();
        String env = System.getProperty("env");
        DBCredentials partner1Credentials = configManager.getCredentials(env, partner);

        if (env == null || env.isEmpty()) {
            throw new IllegalArgumentException("Environment NOT Specified. Use -Denv=QA/UAT/PROD.");
        } else {
            if (Objects.equals(env.toUpperCase(), env)) {
                DB_URL = partner1Credentials.getDbUrl();
                DB_USER = partner1Credentials.getDbUser();
                DB_PASSWORD = partner1Credentials.getDbPassword();
            } else {
                throw new IllegalArgumentException("Invalid environment specified. Use QA/UAT/PROD.");
            }
        }
    }

    public static JSONArray getPatientDetailsFromDB(int companyId, long patientId) {

        setDataBaseCredentials();

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

    public static JSONArray getOrderDetailsFromDB(int companyId, long orderId) {

        setDataBaseCredentials();

        JSONArray jsonArrayResult = new JSONArray();
        String procedureCall = "{call sp_da_orders_payload_by_id(?, ?)}";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement callableStatement = connection.prepareCall(procedureCall)) {

            callableStatement.setInt(1, companyId);
            callableStatement.setLong(2, orderId);

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

        normalizeDatesInJson(array1);
        normalizeDatesInJson(array2);

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

    private static String normalizeDate(String date) {
        try {

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = inputFormat.parse(date);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            return date;
        }
    }

    private static boolean isDate(String value) {
        String datePattern = "^(\\d{4})-(\\d{1,2})-(\\d{1,2}) (\\d{2}):(\\d{2}):(\\d{2})$";
        return Pattern.matches(datePattern, value);
    }

    private static void normalizeDatesInJson(Object json) {
        if (json instanceof JSONObject jsonObject) {

            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);

                if (value instanceof String && isDate((String) value)) {
                    jsonObject.put(key, normalizeDate((String) value));
                }
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    normalizeDatesInJson(value);
                }
            }
        } else if (json instanceof JSONArray jsonArray) {

            for (int i = 0; i < jsonArray.length(); i++) {
                normalizeDatesInJson(jsonArray.get(i));
            }
        }
    }

    public static boolean CompareDataAPIResponseAndDBTableData(JSONArray dbTableData, JSONArray dataAPIResponse) {

        normalizeDatesInJson(dbTableData);
        normalizeDatesInJson(dataAPIResponse);

        System.out.println("Normalized Response Data: " + dataAPIResponse);
        System.out.println("Normalized DB Data: " + dbTableData);

        if (dbTableData.length() != dataAPIResponse.length()) {
            return false;
        }

        JSONArray sortedArray1 = sortJsonArray(dataAPIResponse);
        JSONArray sortedArray2 = sortJsonArray(dbTableData);

        for (int i = 0; i < sortedArray1.length(); i++) {
            Object obj1 = sortedArray1.get(i);
            Object obj2 = sortedArray2.get(i);

            if (!deepEquals(obj1, obj2)) {
                return false;
            }
        }

        return true;
    }

    private static JSONArray sortJsonArray(JSONArray jsonArray) {
        List<Object> sortedList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                sortedList.add(sortJsonObject((JSONObject) value));
            } else {
                sortedList.add(value);
            }
        }
        sortedList.sort(Comparator.comparing(Object::toString));
        return new JSONArray(sortedList);
    }

    private static JSONObject sortJsonObject(JSONObject jsonObject) {
        TreeMap<String, Object> sortedMap = new TreeMap<>();
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                sortedMap.put(key, sortJsonObject((JSONObject) value));
            } else if (value instanceof JSONArray) {
                sortedMap.put(key, sortJsonArray((JSONArray) value));
            } else {
                sortedMap.put(key, value);
            }
        }
        return new JSONObject(sortedMap);
    }

    private static boolean deepEquals(Object obj1, Object obj2) {
        if (obj1 instanceof JSONObject && obj2 instanceof JSONObject) {
            JSONObject json1 = sortJsonObject((JSONObject) obj1);
            JSONObject json2 = sortJsonObject((JSONObject) obj2);

            if (json1.keySet().equals(json2.keySet())) {
                for (String key : json1.keySet()) {
                    if (!deepEquals(json1.get(key), json2.get(key))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        if (obj1 instanceof JSONArray && obj2 instanceof JSONArray) {
            JSONArray array1 = sortJsonArray((JSONArray) obj1);
            JSONArray array2 = sortJsonArray((JSONArray) obj2);

            if (array1.length() != array2.length()) {
                return false;
            }

            for (int i = 0; i < array1.length(); i++) {
                if (!deepEquals(array1.get(i), array2.get(i))) {
                    return false;
                }
            }
            return true;
        }

        return Objects.equals(obj1, obj2);
    }


    public static JSONArray getPatientJsonPathDetails(Response response) {

        JsonPath jsonPath = response.jsonPath();
        Map<String, Object> dataMap = jsonPath.getMap("data");

        JSONObject jsonObject = mapToJsonObject(dataMap);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        return jsonArray;
    }

    private static JSONObject mapToJsonObject(Map<String, Object> dataMap) {
        JSONObject jsonObject = new JSONObject();

        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            Object value = entry.getValue();

            if (value == null) {
                jsonObject.put(entry.getKey(), JSONObject.NULL);
            } else if (value instanceof String && ((String) value).isEmpty()) {
                jsonObject.put(entry.getKey(), JSONObject.NULL);
            } else if (value instanceof Map) {
                jsonObject.put(entry.getKey(), mapToJsonObject((Map<String, Object>) value));
            } else if (value instanceof List) {
                JSONArray jsonArray = listToJsonArray((List<Object>) value);
                jsonObject.put(entry.getKey(), jsonArray);
            } else {
                jsonObject.put(entry.getKey(), value);
            }
        }

        return jsonObject;
    }

    private static JSONArray listToJsonArray(List<Object> list) {
        JSONArray jsonArray = new JSONArray();

        for (Object item : list) {
            if (item instanceof Map) {
                jsonArray.put(mapToJsonObject((Map<String, Object>) item));
            } else if (item == null) {
                jsonArray.put(JSONObject.NULL);
            } else {
                jsonArray.put(item);
            }
        }

        return jsonArray;
    }
}
