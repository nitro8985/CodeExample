package forms;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

public abstract class BaseForm {
    private final By uniqueFormLocator;
    private final String formName;

    public BaseForm(By locator, String name) {
        formName = name;
        uniqueFormLocator = locator;
    }

    public boolean isOpen() {
        return !$$(uniqueFormLocator).isEmpty();
    }
}

