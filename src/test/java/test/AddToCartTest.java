package test;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.CartPage;
import page.ProductPage;
import util.WaitUtil;

import java.util.List;

public class AddToCartTest extends BaseTest {

    private void acceptAlert() {
        WaitUtil.waitForAlert(driver, 5);
        driver.switchTo().alert().accept();
    }

    @Test
    public void addMultipleProductsToCart_ShouldShowCorrectTotal() {
        ProductPage product = new ProductPage(driver);
        CartPage cart = new CartPage(driver);

        String product1 = "Samsung galaxy s6";
        String product2 = "Sony vaio i5";

        product.openProduct(product1);
        product.clickAddToCart();
        acceptAlert();

        product.openProduct(product2);
        product.clickAddToCart();
        acceptAlert();

        cart.openCart();
        List<String> productsInCart = cart.getCartProductNames();
        Assert.assertTrue(productsInCart.contains(product1), "Cart should contain: " + product1);
        Assert.assertTrue(productsInCart.contains(product2), "Cart should contain: " + product2);

        int total = cart.getTotal();
        int expectedTotal = cart.getCartProductPrices().stream().mapToInt(Integer::intValue).sum();
        Assert.assertEquals(total, expectedTotal, "Cart total should equal sum of product prices");
    }

    @Test
    public void deleteProductFromCart_ShouldUpdateCartAndTotal() {
        ProductPage product = new ProductPage(driver);
        CartPage cart = new CartPage(driver);

        String productName = "Samsung galaxy s6";

        product.openProduct(productName);
        product.clickAddToCart();
        acceptAlert();

        cart.openCart();
        Assert.assertTrue(cart.getCartProductNames().contains(productName), "Product should be in cart before delete");

        int beforeTotal = cart.getTotal();
        cart.deleteProductByName(productName);
        Assert.assertFalse(cart.getCartProductNames().contains(productName), "Product should be removed from cart");

        int afterTotal = cart.getTotal();
        Assert.assertTrue(afterTotal < beforeTotal, "Total should reduce after deletion");
    }

    @Test
    public void deleteFirstItemFromCart_ShouldUpdateCart() {
        ProductPage product = new ProductPage(driver);
        CartPage cart = new CartPage(driver);

        String product1 = "Samsung galaxy s6";
        String product2 = "Sony vaio i5";

        product.openProduct(product1);
        product.clickAddToCart();
        acceptAlert();

        product.openProduct(product2);
        product.clickAddToCart();
        acceptAlert();

        cart.openCart();
        List<String> beforeDelete = cart.getCartProductNames();
        Assert.assertTrue(beforeDelete.size() >= 2, "Should have at least two products in cart");

        int beforeTotal = cart.getTotal();
        cart.deleteFirstItem();
        Assert.assertEquals(cart.getCartProductNames().size(), beforeDelete.size() - 1, "One product should be deleted");

        int afterTotal = cart.getTotal();
        Assert.assertTrue(afterTotal < beforeTotal, "Total should decrease after deleting item");
    }
}