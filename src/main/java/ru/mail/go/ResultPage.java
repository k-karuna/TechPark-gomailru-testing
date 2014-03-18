package ru.mail.go;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Created by velikolepnii on 18.03.14.
 */
public class ResultPage {
    private WebDriver driver;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public ResultPage enterDollarValue(String value) {
        driver.findElement(By.id("ival")).clear();
        driver.findElement(By.id("ival")).sendKeys(value);
        return this;
    }

    public String getRubblesValue() {
        return driver.findElement(By.id("oval")).getText();
    }

    public boolean checkForIncorrect() {
        return driver.findElement(By.id("ival")).getCssValue("color").equals("rgba(255, 0, 0, 1)");
    }

    public String getCurrencyTitle() {
        return driver.findElement(By.id("icode")).getText();
    }

    public ResultPage clickChangeButton() {
        driver.findElement(By.id("change_conv")).click();
        return this;
    }
}
