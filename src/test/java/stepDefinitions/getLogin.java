package stepDefinitions;

import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;

public class getLogin {

    ScenarioContext scenarioContext = ScenarioContext.getInstance();

    @Given("the GetLogin request set with valid details for {string}")
    public void the_GetLogin_request_for_pfizer_is_send(String vendor) {


    }

    @When("the GetLogin request is called")
    public void the_GetLogin_request_is_called() {

    }

    @Then("the GetLogin should return valid JWT token")
    public void the_GetLogin_should_return_valid_JWT_token() {

    }
}
