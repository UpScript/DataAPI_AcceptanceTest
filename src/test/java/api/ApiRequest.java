package api;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static utils.SupportMethod.getAuthPassword;
import static utils.SupportMethod.getAuthUserName;

public class ApiRequest extends SpecBuilder{

    public static Response post(String endPoint, Headers headers,
                                HashMap<String, Object> formParams, Cookies cookies){
        return given(getRequestSpec()).
                headers(headers).
                formParams(formParams).
                cookies(cookies).
                when().
                post(endPoint).
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response get(String endPoint, Headers accessToken){
        return given(getRequestSpec()).
                headers(accessToken).
                when().
                get(endPoint).
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response get(String endPoint, String userName, String password){
        return given(getRequestSpec())
                .auth().basic(userName, password)
                .contentType(ContentType.URLENC)
                .header("Content-type", "application/json")
                .when()
                .get(endPoint)
                .then().spec(getResponseSpec()).
                extract().
                response();
    }
}
