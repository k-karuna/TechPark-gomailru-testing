package ru.mail.go;

import org.openqa.selenium.WebDriver;

/**
 * Created by velikolepnii on 18.03.14.
 */
public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public SearchFormElement getSearchFormInput() {
        return new SearchFormElement(driver);
    }
}
