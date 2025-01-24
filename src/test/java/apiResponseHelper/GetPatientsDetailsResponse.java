package apiResponseHelper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetPatientsDetailsResponse {

    JsonPath jsonPath;
    public static String COMPANY_ID = "";
    static Map<String, String> patientIdAndCompanyIdDetails = new HashMap<>();

    public String getListOfPatientsIds(Response response) {
        jsonPath = response.jsonPath();
        return jsonPath.getString("data.patient-patient-id");
    }

    public String getListOfCompanyIds(Response response) {
        jsonPath = response.jsonPath();
        return jsonPath.getString("data.company-id");
    }

    public List<String> getListOfPatients(Response response) {
        jsonPath = response.jsonPath();
        return jsonPath.getList("data.patient-patient-id", String.class);
    }

    public List<String> getListOfCompanies(Response response) {
        jsonPath = response.jsonPath();
        return jsonPath.getList("data.company-id", String.class);
    }

    public Map<String, String> getPatientAndCompanyIds(Response response) throws InterruptedException {

        Map<String, String> patientIdAndCompanyId = new HashMap<>();

        try {
            for (int i = 0; i < getListOfPatients(response).size(); i++) {
                String patientId = getListOfPatients(response).get(i);
                String companyId = getListOfCompanies(response).get(i);
                patientIdAndCompanyId.put(patientId, companyId);
            }
        } catch (ClassCastException e) {
            System.out.println("Type mismatch detected: " + e.getMessage());
        }

        patientIdAndCompanyIdDetails = patientIdAndCompanyId;
        Thread.sleep(60000);
        return patientIdAndCompanyId;
    }

    public static String getCompanyId(Response response, String patientId) throws InterruptedException {

        return patientIdAndCompanyIdDetails.get(patientId);
    }
}
