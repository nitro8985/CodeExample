import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.DataUtil;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class BaseUiTest {

    @BeforeClass
    protected void setupAllure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @BeforeMethod
    protected void setupBrowser() {
        Configuration.browser = DataUtil.getValue("browser");
        Configuration.timeout = Long.parseLong(DataUtil.getValue("timeout"));
        Configuration.reportsFolder = "target/allure-results";
        Configuration.downloadsFolder = "target/downloads";
        open(String.format("%s%s", DataUtil.getValue("baseUri"), DataUtil.getValue("formUri")));
    }

    @AfterMethod
    protected void tearDown() {
        closeWebDriver();
    }

}
