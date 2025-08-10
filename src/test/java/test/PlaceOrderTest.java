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

        String productName = "Sony vaio i5"; // choose an available product

        // Add product to cart
        product.openProduct(productName);
        product.clickAddToCart();

        // Wait for and accept alert confirming addition
        Thread.sleep(1500);
        driver.switchTo().alert().accept();

        // Go to cart and open place order modal
        cart.openCart();
        cart.clickPlaceOrder();

        // Fill order details
        order.fillOrder(TestData.ORDER_NAME, TestData.ORDER_COUNTRY, TestData.ORDER_CITY,
                TestData.ORDER_CARD, TestData.ORDER_MONTH, TestData.ORDER_YEAR);

        // Click Purchase
        order.clickPurchase();

        // Verify confirmation modal
        String heading = order.getConfirmationHeading();
        String details = order.getConfirmationDetails();

        Assert.assertTrue(heading.toLowerCase().contains("thank you"), "Confirmation heading incorrect: " + heading);
        Assert.assertTrue(details.toLowerCase().contains("id"), "Confirmation details missing order ID: " + details);
        Assert.assertTrue(details.toLowerCase().contains("amount"), "Confirmation details missing amount: " + details);

        // Click OK to close modal
        order.clickOkButton();
    }
}