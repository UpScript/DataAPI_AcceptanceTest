package hooks;

import context.ScenarioContext;
//import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class hook {

//TODO These lines are disabled by default and will be enabled when we implement the UI tests

//    private WebDriver driver;
//    private final TestContext context;
//
//    public hook(TestContext context) {
//        this.context = context;
//    }

    @Before
    public void before(Scenario scenario) {
        //TODO These lines are disabled by default and will be enabled when we implement the UI tests
//        driver = DriverFactory.initializeDriver(System.getProperty("browser", "chrome"));
//        context.driver = driver;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @After
    public void after(Scenario scenario) {
        ScenarioContext.getInstance().clear();
        //TODO These lines are disabled by default and will be enabled when we implement the UI tests
//        driver.quit();
    }
}
