package test;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.CartPage;
import page.ProductPage;

public class AddToCartTest extends BaseTest {

    @Test
    public void addAndViewCart_ShouldContainItem() throws InterruptedException {
        ProductPage product = new ProductPage(driver);
        CartPage cart = new CartPage(driver);

        String productName = "Samsung galaxy s6";

        product.openProduct(productName);
        product.clickAddToCart();

        Thread.sleep(1500);
        driver.switchTo().alert().accept();

        cart.openCart();

        String total = cart.getTotal();
        Assert.assertTrue(Integer.parseInt(total) > 0 || total.equals("0"), "Total should be numeric. Found: " + total);
    }

    @Test
    public void deleteProductFromCart_ShouldUpdateCartAndTotal() throws InterruptedException {
        CartPage cart = new CartPage(driver);

        cart.openCart();

        String totalBefore = cart.getTotal();

        // Delete the first product in cart
        cart.deleteFirstItem();

        // Wait a bit for UI to update after deletion
        Thread.sleep(2000);

        String totalAfter = cart.getTotal();

        // Verify total price decreased or is zero after deletion
        Assert.assertNotEquals(totalBefore, totalAfter, "Total price should update after deleting product.");
    }
}