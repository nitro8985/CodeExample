import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.PersonalData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.DataUtil;
import utils.PersonalDataProvider;

public class SignUpFormApiTest {

    private final String textOnSuccess = "Сейчас на ваш телефон поступит звонок, последние 4 цифры являются кодом";
    private final String messageOnReject = "не может быть пустым";
    private final String messageOnValidationFault = "must be a valid email";

    @Test(dataProvider = "fakeData", dataProviderClass = PersonalDataProvider.class, priority = 1)
    public void backendAcceptsValidDataTest(PersonalData pd) {
        Response response = postData(pd);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
        Assert.assertTrue(response.body().jsonPath().getBoolean("type"));
        Assert.assertEquals(response.body().jsonPath().getString("text"), textOnSuccess);
    }

    @Test(dataProvider = "fakeData", dataProviderClass = PersonalDataProvider.class, priority = 2)
    public void backendRejectsUnfilledTest(PersonalData pd) {
        pd.setUserName("");
        Response response = postData(pd);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
        Assert.assertFalse(response.body().jsonPath().getBoolean("type"));
        Assert.assertTrue(response.body().jsonPath().getString("message").contains(messageOnReject));
    }

    @Test(dataProvider = "fakeData", dataProviderClass = PersonalDataProvider.class, priority = 3)
    public void backendValidatesEmailTest(PersonalData pd) {
        pd.setLogin(RandomStringUtils.randomAlphabetic(DataUtil.RANDSTRLEN));
        Response response = postData(pd);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
        Assert.assertFalse(response.body().jsonPath().getBoolean("type"));
        Assert.assertTrue(response.body().jsonPath().getString("message").contains(messageOnValidationFault));
    }

    private Response postData(PersonalData pd) {
        return RestAssured.given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(pd)
                .baseUri(DataUtil.getValue("baseUri"))
                .post(DataUtil.getValue("authEndpoint"));
    }
}
