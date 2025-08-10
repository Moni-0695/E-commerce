package test;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.CartPage;
import page.PlaceOrderPage;
import page.ProductPage;
import util.TestData;

public class PlaceOrderTest extends BaseTest {

    @Test
    public void placeOrder_WithValidDetails_ShouldConfirm() throws InterruptedException {
        ProductPage product = new ProductPage(driver);
        CartPage cart = new CartPage(driver);
        PlaceOrderPage order = new PlaceOrderPage(driver);

        String productName = "Sony vaio i5";

        // Add product to cart
        product.openProduct(productName);
        product.clickAddToCart();

        Thread.sleep(1500);
        driver.switchTo().alert().accept();

        // Open cart and click place order
        cart.openCart();
        cart.clickPlaceOrder();

        // Fill in order details
        order.fillOrder(TestData.ORDER_NAME, TestData.ORDER_COUNTRY, TestData.ORDER_CITY,
                TestData.ORDER_CARD, TestData.ORDER_MONTH, TestData.ORDER_YEAR);

        // Submit purchase
        order.clickPurchase();

        // Validate confirmation modal content
        String heading = order.getConfirmationHeading();
        String details = order.getConfirmationDetails();

        Assert.assertTrue(heading.toLowerCase().contains("thank you"), "Popup heading missing 'Thank you': " + heading);
        Assert.assertTrue(details.toLowerCase().contains("id:"), "Popup details missing 'Id:' field: " + details);
        Assert.assertTrue(details.toLowerCase().contains("amount:"), "Popup details missing 'Amount:' field: " + details);

        // Close confirmation
        order.clickOkButton();
    }

    @Test
    public void placeOrder_WithEmptyForm_ShouldShowValidationError() throws InterruptedException {
        CartPage cart = new CartPage(driver);
        PlaceOrderPage order = new PlaceOrderPage(driver);

        cart.openCart();
        cart.clickPlaceOrder();

        order.clickPurchase();

        // Check if validation error message appears
        Assert.assertTrue(order.isValidationErrorDisplayed(), "Expected validation error message on empty form");

        // Alternatively, check if confirmation modal does not show success
        String heading = order.getConfirmationHeading();
        Assert.assertFalse(heading.toLowerCase().contains("thank"), "Order should not be confirmed on empty form");
    }

    @Test
    public void verifyOrderConfirmationPopup_ShouldContainValidData() throws InterruptedException {
        ProductPage product = new ProductPage(driver);
        CartPage cart = new CartPage(driver);
        PlaceOrderPage order = new PlaceOrderPage(driver);

        String productName = "Sony vaio i5";

        product.openProduct(productName);
        product.clickAddToCart();

        Thread.sleep(1500);
        driver.switchTo().alert().accept();

        cart.openCart();
        cart.clickPlaceOrder();

        order.fillOrder(TestData.ORDER_NAME, TestData.ORDER_COUNTRY, TestData.ORDER_CITY,
                TestData.ORDER_CARD, TestData.ORDER_MONTH, TestData.ORDER_YEAR);

        order.clickPurchase();

        String heading = order.getConfirmationHeading();
        String details = order.getConfirmationDetails();

        Assert.assertTrue(heading.toLowerCase().contains("thank you"), "Popup heading missing 'Thank you': " + heading);
        Assert.assertTrue(details.toLowerCase().contains("id:"), "Popup details missing 'Id:' field: " + details);
        Assert.assertTrue(details.toLowerCase().contains("amount:"), "Popup details missing 'Amount:' field: " + details);

        order.clickOkButton();
    }
}