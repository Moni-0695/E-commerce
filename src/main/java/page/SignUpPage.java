package page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.WaitUtil;

public class SignUpPage {
    private WebDriver driver;

    private By signUpLink = By.id("signin2");
    private By usernameField = By.id("sign-username");
    private By passwordField = By.id("sign-password");
    private By signUpBtn = By.xpath("//button[text()='Sign up']");

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openSignUpModal() {
        WaitUtil.waitForClickable(driver, signUpLink, 10).click();
    }

    public void signUp(String username, String password) {
        WaitUtil.waitForVisibility(driver, usernameField, 10).sendKeys(username);
        WaitUtil.waitForVisibility(driver, passwordField, 10).sendKeys(password);
        WaitUtil.waitForClickable(driver, signUpBtn, 10).click();
    }

    public String getAlertText() {
        WaitUtil.waitForAlert(driver, 10);
        return driver.switchTo().alert().getText();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }
}