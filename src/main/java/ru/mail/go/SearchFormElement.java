package ru.mail.go;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by velikolepnii on 18.03.14.
 */
public class SearchFormElement {
    private WebDriver driver;

    public SearchFormElement(WebDriver driver) {
        this.driver = driver;
    }

    public SearchFormElement enterText (String query) {
        driver.findElement(By.id("q")).sendKeys(query);
        return this;
    }

    public ResultPage clickGoButton() {
        driver.findElement(By.className("go-form__submit")).click();
        return new ResultPage(this.driver);
    }

    public ResultPage search(String query) {
        this.enterText(query);
        return this.clickGoButton();
    }
}
