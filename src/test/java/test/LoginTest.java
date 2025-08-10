package test;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.LoginPage;
import util.TestData;

public class LoginTest extends BaseTest {

    @Test
    public void login_ValidCredentials_ShouldShowUsername() {
        LoginPage login = new LoginPage(driver);
        login.openLoginModal();
        login.login(TestData.USERNAME, TestData.PASSWORD);

        String shown = login.getLoggedInUser();
        Assert.assertTrue(shown.toLowerCase().contains(TestData.USERNAME.toLowerCase()),
                "Logged-in username not shown. Found: " + shown);
    }

    @Test
    public void login_InvalidPassword_ShouldShowAlert() {
        LoginPage login = new LoginPage(driver);
        login.openLoginModal();
        login.login(TestData.USERNAME, "WrongPass123");

        String alert = login.getAlertText();
        Assert.assertTrue(alert.length() > 0, "Expected an alert on wrong credentials");
        login.acceptAlert();
    }
}