package hooks;

import context.ScenarioContext;
//import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class hook {

//    private WebDriver driver;
//    private final TestContext context;
//
//    public hook(TestContext context) {
//        this.context = context;
//    }

    @Before
    public void before(Scenario scenario) {
//        driver = DriverFactory.initializeDriver(System.getProperty("browser", "chrome"));
//        context.driver = driver;
    }

    @After
    public void after(Scenario scenario) {
        ScenarioContext.getInstance().clear();
//        driver.quit();
    }
}
