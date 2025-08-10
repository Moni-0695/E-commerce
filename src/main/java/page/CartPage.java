package page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.WaitUtil;

public class CartPage {
    private WebDriver driver;

    private By cartLink = By.id("cartur");
    private By placeOrderBtn = By.xpath("//button[text()='Place Order']");
    private By deleteLink = By.xpath("//a[text()='Delete']");
    private By totalPrice = By.id("totalp");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCart() {
        WaitUtil.waitForClickable(driver, cartLink, 10).click();
    }

    public void clickPlaceOrder() {
        WaitUtil.waitForClickable(driver, placeOrderBtn, 10).click();
    }

    public void deleteFirstItem() {
        WaitUtil.waitForClickable(driver, deleteLink, 10).click();
    }

    public String getTotal() {
        return WaitUtil.waitForVisibility(driver, totalPrice, 10).getText();
    }
}