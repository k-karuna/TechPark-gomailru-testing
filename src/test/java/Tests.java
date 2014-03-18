import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mail.go.MainPage;
import ru.mail.go.ResultPage;

/**
 * Created by velikolepnii on 18.03.14.
 */

public class Tests {

    private WebDriver driver;
    private final String DOLLAR_CONVERTER_DATA = new String("конвертер валют");
    private final String TARGET_URL = new String("http://go.mail.ru");
    private final Float dollarCourse = new Float(36.6505);

    @BeforeMethod
    public void setUp() {
        driver = new FirefoxDriver();
        driver.navigate().to(TARGET_URL);
    }

    @Test
    public void testDollarConverter() {
        ResultPage resultPage = new MainPage(this.driver).getSearchFormInput().search(DOLLAR_CONVERTER_DATA);
        String rubbleResult = "";
        Integer resLength = 0;
        for (Integer dollarsValue = 0; dollarsValue < 25; dollarsValue++) {
            rubbleResult = resultPage.enterDollarValue(dollarsValue.toString()).getRubblesValue();
            resLength = rubbleResult.length();
            Assert.assertEquals(new Float(dollarCourse * dollarsValue).toString().replace(".", ",").substring(0, resLength), rubbleResult);
        }

    }

    @Test
    public void testIncorrectHighlights() {
        String[] incorrectInputs = {"lhjkhjkkjr", "-23", "2s", "3 9", "23,2,2", "56$"};
        String[] correctInputs = {"7", "2.0", "3,456", "0"};
        ResultPage resultPage = new MainPage(this.driver).getSearchFormInput().search(DOLLAR_CONVERTER_DATA);
        for(int i = 1; i < incorrectInputs.length; i++) {
            Assert.assertTrue(resultPage.enterDollarValue(incorrectInputs[i]).checkForIncorrect());
        }
        for(int i = 1; i < correctInputs.length; i++) {
            Assert.assertTrue(!resultPage.enterDollarValue(correctInputs[i]).checkForIncorrect());
        }
    }

    @Test
    public void testBigValue() {
        String bigValue = "9";
        for (int i = 0; i < 310; i++) bigValue += "9";
        String rubbleResult = new MainPage(this.driver).getSearchFormInput().search(DOLLAR_CONVERTER_DATA).enterDollarValue(bigValue).getRubblesValue();
        Assert.assertEquals("Infinity", rubbleResult);
    }

    @Test
    public void testSmallValue() {
        String smallValue = "0.0";
        for (int i = 0; i < 330; i++) smallValue += "0";
        smallValue += "1";
        String rubbleResult = new MainPage(this.driver).getSearchFormInput().search(DOLLAR_CONVERTER_DATA).enterDollarValue(smallValue).getRubblesValue();
        Assert.assertEquals("0", rubbleResult);
    }

    @Test
    public void testCurrencyTitle() {
        ResultPage resultPage = new MainPage(this.driver).getSearchFormInput().search(DOLLAR_CONVERTER_DATA);
        String title = resultPage.enterDollarValue("1").getCurrencyTitle();
        Assert.assertEquals("доллар сша", title);
        title = resultPage.enterDollarValue("3").getCurrencyTitle();
        Assert.assertEquals("доллара сша", title);
        title = resultPage.enterDollarValue("7").getCurrencyTitle();
        Assert.assertEquals("долларов сша", title);
    }

    @Test
    public void testChangeButton() {
        ResultPage resultPage = new MainPage(this.driver).getSearchFormInput().search(DOLLAR_CONVERTER_DATA);
        resultPage.clickChangeButton();
        Assert.assertEquals("российский рубль", resultPage.getCurrencyTitle());
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

}
