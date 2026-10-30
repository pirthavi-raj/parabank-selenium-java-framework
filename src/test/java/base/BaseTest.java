package base;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {

   protected WebDriver driver;

   //Temporary: I'm moving this config.properties in the next task
    private static final String BASE_URL = "https://parabank.parasoft.com/parabank/index.htm";

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("Chrome") String browser,
                      @Optional("false") String headless){

        driver = DriverFactory.createDriver(browser, Boolean.parseBoolean(headless));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get(BASE_URL);
    }

      @AfterMethod(alwaysRun = true)
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
      }

}
