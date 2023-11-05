package forms;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SignUpForm extends BaseForm {

    private final SelenideElement loginTextBox = $(By.name("login"));
    private final SelenideElement emailTextBox = $(By.name("email"));
    private final SelenideElement phoneTextBox = $(By.name("phone"));
    private final SelenideElement passwordTextBox = $(By.name("password"));
    private final SelenideElement passwordValidationTextBox = $(By.name("passwordValidation"));
    private final SelenideElement acceptTermsCheckbox = $(By.className("signUpForm__checkoboxItem"), 0);
    private final SelenideElement givePermissionCheckbox = $(By.className("signUpForm__checkoboxItem"), 1);
    private final SelenideElement submitButton = $(By.id("submitLogin"));


    public SignUpForm() {
        super(By.name("inputForAuth"), "SignUpForm");
    }

    public SignUpForm putLogin(String login) {
        loginTextBox.setValue(login);
        return this;
    }

    public SignUpForm putEmail(String email) {
        emailTextBox.setValue(email);
        return this;
    }

    public SignUpForm putPhone(String phone) {
        phoneTextBox.setValue(phone);
        return this;
    }

    public SignUpForm putPassword(String pw) {
        passwordTextBox.setValue(pw);
        return this;
    }

    public SignUpForm confirmPassword(String pw) {
        passwordValidationTextBox.setValue(pw);
        return this;
    }

    public SignUpForm acceptTerms() {
        acceptTermsCheckbox.click();
        return this;
    }

    public SignUpForm givePermission() {
        givePermissionCheckbox.click();
        return this;
    }

    public void submit() {
        submitButton.click();
    }

    public boolean isSubmitButtonEnabled() {
        return submitButton.isEnabled();
    }
}
