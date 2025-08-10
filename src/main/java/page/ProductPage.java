package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.WaitUtil;

public class ProductPage {
    private WebDriver driver;

    // product links are anchor with product name text
    private By productLinkByName(String name) {
        return By.xpath("//a[text()='" + name + "']");
    }
    private By addToCartBtn = By.xpath("//a[text()='Add to cart']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openProduct(String productName) {
        WaitUtil.waitForClickable(driver, productLinkByName(productName), 10).click();
    }

    public void clickAddToCart() {
        WaitUtil.waitForClickable(driver, addToCartBtn, 10).click();
    }
}