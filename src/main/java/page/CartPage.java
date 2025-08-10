package page;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.WaitUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By cartLink = By.id("cartur");
    private By placeOrderBtn = By.xpath("//button[text()='Place Order']");
    private By totalPrice = By.id("totalp");
    private By cartRows = By.cssSelector("#tbodyid > tr");
    private By productNamesInCart = By.cssSelector("#tbodyid > tr > td:nth-child(2)");
    private By deleteLinks = By.xpath("//a[starts-with(@onclick, 'deleteItem') and text()='Delete']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** Opens the cart page */
    public void openCart() {
        WaitUtil.waitForClickable(driver, cartLink, 10).click();
    }

    /** Clicks 'Place Order' button */
    public void clickPlaceOrder() {
        WaitUtil.waitForClickable(driver, placeOrderBtn, 10).click();
    }

    /** Gets all product names from the cart */
    public List<String> getCartProductNames() {
        return WaitUtil.waitForVisibilityAll(driver, productNamesInCart, 10)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /** Gets the current total price from cart */
    public int getTotal() {
        String totalText = WaitUtil.waitForVisibility(driver, totalPrice, 10).getText();
        try {
            return Integer.parseInt(totalText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** Deletes the first item in the cart */
    public void deleteFirstItem() {
        List<WebElement> deletes = WaitUtil.waitForVisibilityAll(driver, deleteLinks, 10);
        if (!deletes.isEmpty()) {
            int originalSize = deletes.size();
            deletes.get(0).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(deleteLinks, originalSize - 1));
        }
    }

    /** Deletes a product from the cart by exact name match */
    public void deleteProductByName(String productName) {
        List<WebElement> rows = WaitUtil.waitForVisibilityAll(driver, cartRows, 10);
        for (WebElement row : rows) {
            String name = row.findElement(By.xpath("./td[2]")).getText();
            if (name.equalsIgnoreCase(productName)) {
                WebElement deleteLink = row.findElement(By.xpath(".//a[starts-with(@onclick, 'deleteItem')]"));
                deleteLink.click();
                wait.until(ExpectedConditions.stalenessOf(row));
                break;
            }
        }
    }

    /** Deletes product by ID from onclick attribute */
    public void deleteByProductId(String productId) {
        By deleteById = By.xpath("//a[contains(@onclick, \"" + productId + "\") and text()='Delete']");
        WaitUtil.waitForClickable(driver, deleteById, 10).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(deleteById));
    }

    /** Adds a product to cart and verifies alert text */
    public void addProductAndVerifyAlert(By addButtonLocator) {
        WaitUtil.waitForClickable(driver, addButtonLocator, 10).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        if (!"Product added".equalsIgnoreCase(alertText.trim())) {
            throw new AssertionError("Unexpected alert text: " + alertText);
        }
        alert.accept();
    }

    /** Verifies cart contains given product with expected price */
    public void verifyProductInCart(String expectedName, int expectedPrice) {
        boolean found = false;
        for (WebElement row : WaitUtil.waitForVisibilityAll(driver, cartRows, 10)) {
            String name = row.findElement(By.xpath("./td[2]")).getText();
            String priceText = row.findElement(By.xpath("./td[3]")).getText();
            int price = Integer.parseInt(priceText);
            if (name.equalsIgnoreCase(expectedName) && price == expectedPrice) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Product " + expectedName + " with price " + expectedPrice + " not found in cart.");
        }
    }

    /** Verifies total price matches sum of all item prices */
    public void verifyTotalPriceMatchesSum() {
        List<WebElement> rows = WaitUtil.waitForVisibilityAll(driver, cartRows, 10);
        int sum = 0;
        for (WebElement row : rows) {
            String priceText = row.findElement(By.xpath("./td[3]")).getText();
            sum += Integer.parseInt(priceText);
        }
        int displayedTotal = getTotal();
        if (sum != displayedTotal) {
            throw new AssertionError("Total price mismatch: Expected " + sum + " but found " + displayedTotal);
        }
    }

    public List<Integer> getCartProductPrices() {
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".price")); 
        List<Integer> prices = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText().replaceAll("[^0-9]", ""); // remove non-digits
            if (!priceText.isEmpty()) {
                prices.add(Integer.parseInt(priceText));
            }
        }
        return prices;
    }
}