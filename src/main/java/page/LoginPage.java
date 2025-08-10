package page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.WaitUtil;

public class LoginPage {
    private WebDriver driver;

    private By loginLink = By.id("login2");
    private By usernameField = By.id("loginusername");
    private By passwordField = By.id("loginpassword");
    private By loginBtn = By.xpath("//button[text()='Log in']");
    private By userLabel = By.id("nameofuser");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLoginModal() {
        WaitUtil.waitForClickable(driver, loginLink, 10).click();
    }

    public void login(String username, String password) {
        WaitUtil.waitForVisibility(driver, usernameField, 10).sendKeys(username);
        WaitUtil.waitForVisibility(driver, passwordField, 10).sendKeys(password);
        WaitUtil.waitForClickable(driver, loginBtn, 10).click();
    }

    public String getLoggedInUser() {
        return WaitUtil.waitForVisibility(driver, userLabel, 10).getText();
    }

    public String getAlertText() {
        WaitUtil.waitForAlert(driver, 10);
        return driver.switchTo().alert().getText();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }
}