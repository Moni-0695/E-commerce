package page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.WaitUtil;

public class PlaceOrderPage {
    private WebDriver driver;

    private By name = By.id("name");
    private By country = By.id("country");
    private By city = By.id("city");
    private By card = By.id("card");
    private By month = By.id("month");
    private By year = By.id("year");
    private By purchaseBtn = By.xpath("//button[text()='Purchase']");
    private By okBtn = By.xpath("//button[text()='OK']");

    // Confirmation modal elements
    private By confirmationHeading = By.cssSelector(".sweet-alert > h2");
    private By confirmationDetails = By.cssSelector(".sweet-alert > div > p");

    public PlaceOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillOrder(String nm, String cnt, String cy, String cd, String m, String y) {
        WaitUtil.waitForVisibility(driver, name, 10).sendKeys(nm);
        WaitUtil.waitForVisibility(driver, country, 10).sendKeys(cnt);
        WaitUtil.waitForVisibility(driver, city, 10).sendKeys(cy);
        WaitUtil.waitForVisibility(driver, card, 10).sendKeys(cd);
        WaitUtil.waitForVisibility(driver, month, 10).sendKeys(m);
        WaitUtil.waitForVisibility(driver, year, 10).sendKeys(y);
    }

    public void clickPurchase() {
        WaitUtil.waitForClickable(driver, purchaseBtn, 10).click();
    }

    public String getConfirmationHeading() {
        return WaitUtil.waitForVisibility(driver, confirmationHeading, 10).getText();
    }

    public String getConfirmationDetails() {
        return WaitUtil.waitForVisibility(driver, confirmationDetails, 10).getText();
    }

    public void clickOkButton() {
        WaitUtil.waitForClickable(driver, okBtn, 10).click();
    }
}