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

    private static final String TEXT_ON_SUCCESS = "Сейчас на ваш телефон поступит звонок, последние 4 цифры являются кодом";
    private static final String MESSAGE_ON_REJECT = "не может быть пустым";
    private static final String MESSAGE_ON_VALIDATION_FAULT = "must be a valid email";

    private static final String TYPE_PATH = "type";
    private static final String TEXT_PATH = "text";
    private static final String MESSAGE_PATH = "message";

    @Test(description = "Check that backend accepts valid data",
            dataProvider = "fakeData",
            dataProviderClass = PersonalDataProvider.class,
            priority = 1)
    public void backendAcceptsValidDataTest(PersonalData pd) {
        Response response = postData(pd);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Status code mismatch");
        Assert.assertTrue(response.body().jsonPath().getBoolean(TYPE_PATH), "Wrong type");
        Assert.assertEquals(response.body().jsonPath().getString(TEXT_PATH), TEXT_ON_SUCCESS, "Text in body mismatch");
    }

    @Test(description = "Check there is validation of form fulfillment on front side",
            dataProvider = "fakeData",
            dataProviderClass = PersonalDataProvider.class,
            priority = 2)
    public void backendRejectsUnfilledTest(PersonalData pd) {
        pd.setUserName("");
        Response response = postData(pd);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Status code mismatch");
        Assert.assertFalse(response.body().jsonPath().getBoolean(TYPE_PATH), "Wrong type");
        Assert.assertTrue(response.body().jsonPath().getString(MESSAGE_PATH).contains(MESSAGE_ON_REJECT),
                "Message in body mismatch");
    }

    @Test(description = "Check there is validation of email on front side",
            dataProvider = "fakeData",
            dataProviderClass = PersonalDataProvider.class,
            priority = 3)
    public void backendValidatesEmailTest(PersonalData pd) {
        pd.setLogin(RandomStringUtils.randomAlphabetic(DataUtil.RANDSTRLEN));
        Response response = postData(pd);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "Status code mismatch");
        Assert.assertFalse(response.body().jsonPath().getBoolean(TYPE_PATH), "Wrong type");
        Assert.assertTrue(response.body().jsonPath().getString(MESSAGE_PATH).contains(MESSAGE_ON_VALIDATION_FAULT),
                "Message in body mismatch");
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
