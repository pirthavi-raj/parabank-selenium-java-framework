package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.get("browser");
        boolean headless = ConfigReader.getBoolean("headless");

        driver = DriverFactory.createDriver(browser, headless);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get(ConfigReader.get("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}