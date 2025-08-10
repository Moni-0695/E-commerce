package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.WaitUtil;

public class HomePage {
    private WebDriver driver;

    private By loginLink = By.id("login2");
    private By signUpLink = By.id("signin2");
    private By cartLink = By.id("cartur");
    private By logoutLink = By.id("logout2");
    private By userLabel = By.id("nameofuser");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLogin() {
        WaitUtil.waitForClickable(driver, loginLink, 10).click();
    }

    public void openSignUp() {
        WaitUtil.waitForClickable(driver, signUpLink, 10).click();
    }

    public void goToCart() {
        WaitUtil.waitForClickable(driver, cartLink, 10).click();
    }

    public String getLoggedInUser() {
        return WaitUtil.waitForVisibility(driver, userLabel, 5).getText();
    }

    public boolean isLogoutVisible() {
        try {
            return WaitUtil.waitForVisibility(driver, logoutLink, 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        WaitUtil.waitForClickable(driver, logoutLink, 5).click();
    }
}