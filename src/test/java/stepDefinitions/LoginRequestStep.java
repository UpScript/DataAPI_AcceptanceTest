package stepDefinitions;

import apiRequestHelper.Login;
import apiResponseHelper.GetLoginResponse;
import context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import static configuration.CredentialProvider.getPassword;
import static configuration.CredentialProvider.getUsername;

public class LoginRequestStep {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();
    Login login = new Login();
    static Response response;
    GetLoginResponse LoginResponse = new GetLoginResponse();

    @When("the GetLogin request is called {string} with valid credentials")
    public void the_GetLogin_request_is_called(String partner) {

        response = login.getLogin(getUsername(partner), getPassword(partner));
        scenarioContext.setContext("token", LoginResponse.getLoginResponse(response));
    }

    @Then("the login request send successfully with {int} status code")
    public void the_request_send_successfully_status_code(int statusCode) {

        Assert.assertEquals(response.statusCode(), statusCode);
    }
}
