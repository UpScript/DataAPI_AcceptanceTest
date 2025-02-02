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

            List<Map<String, Object>> dataList = response.jsonPath().getList("data");

            for (Map<String, Object> data : dataList) {
                Object patientIdObj = data.get("patient-patient-id");
                Object companyIdObj = data.get("company-id");

                String patientId = (patientIdObj != null) ? patientIdObj.toString() : null;
                String companyId = (companyIdObj != null) ? companyIdObj.toString() : null;

                System.out.println("Extracted patientId: " + patientId);
                System.out.println("Extracted companyId: " + companyId);

                if (patientId != null && companyId != null) {
                    patientIdAndCompanyId.put(patientId, companyId);
                } else {
                    System.out.println("Skipping entry with null patient or company ID: " + data);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Final Map: " + patientIdAndCompanyId);
        patientIdAndCompanyIdDetails = patientIdAndCompanyId;

        return patientIdAndCompanyId;
    }

    public static String getCompanyIdForPatient(Response response, String patientId) throws InterruptedException {

        return patientIdAndCompanyIdDetails.get(patientId);
    }
}
