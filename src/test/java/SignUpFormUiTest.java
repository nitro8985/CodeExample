import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.AlertNotFoundException;
import com.codeborne.selenide.ex.TimeoutException;
import forms.OtpForm;
import forms.SignUpForm;
import lombok.extern.slf4j.Slf4j;
import models.PersonalData;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.DataUtil;
import utils.PersonalDataProvider;

@Slf4j
public class SignUpFormUiTest extends BaseUiTest {

    @Test(description = "Check the form accepts valid data",
            dataProvider = "fakeData",
            dataProviderClass = PersonalDataProvider.class,
            priority = 1)
    public void formAcceptsValidDataTest(PersonalData pd) {
        SignUpForm signUpForm = new SignUpForm();
        fillTheForm(signUpForm, pd);
        signUpForm.submit();
        acceptAlert();
        OtpForm otpForm = new OtpForm();
        otpForm.getSelenideElement().shouldBe(Condition.visible);
    }

    @Test(description = "Check there is validation of form fulfillment on front side",
            dataProvider = "fakeData",
            dataProviderClass = PersonalDataProvider.class,
            priority = 2)
    public void cantSubmitUnfilledTest(PersonalData pd) {
        SignUpForm form = new SignUpForm();
        pd.setUserName("");
        fillTheForm(form, pd);
        Assert.assertFalse(form.isSubmitButtonEnabled(), "Submit button has wrong state");
    }

    @Test(description = "Check there is validation of email on front side",
            dataProvider = "fakeData",
            dataProviderClass = PersonalDataProvider.class,
            priority = 3)
    public void formValidatesEmailTest(PersonalData pd) {
        SignUpForm signUpForm = new SignUpForm();
        pd.setLogin(RandomStringUtils.randomAlphabetic(DataUtil.RANDSTRLEN));
        fillTheForm(signUpForm, pd);
        signUpForm.submit();
        OtpForm otpForm = new OtpForm();
        otpForm.getSelenideElement().shouldNot(Condition.visible);
    }

    private void fillTheForm(SignUpForm form, PersonalData pd) {
        form.putLogin(pd.getUserName())
                .putEmail(pd.getLogin())
                .putPhone(pd.getPhoneNumber())
                .putPassword(pd.getPassword())
                .confirmPassword(pd.getPassword())
                .acceptTerms()
                .givePermission();
    }

    private void acceptAlert() {
        try {
            Selenide.switchTo().alert().accept();
        } catch (TimeoutException | AlertNotFoundException e) {
            log.error("No Alert Appeared: {}", e.getMessage());
        }
    }
}
