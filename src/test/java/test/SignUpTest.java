package test;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.SignUpPage;
import util.TestData;

public class SignUpTest extends BaseTest {

    @Test
    public void signUp_NewUser_ShouldShowSuccessAlert() {
        SignUpPage signUp = new SignUpPage(driver);
        signUp.openSignUpModal();

        String unique = TestData.USERNAME + "_" + System.currentTimeMillis();
        signUp.signUp(unique, TestData.PASSWORD);

        String alert = signUp.getAlertText();
        // Demoblaze success text: "Sign up successful." (small variations: may be "Sign up successful.")
        Assert.assertTrue(alert.toLowerCase().contains("sign up"), "Unexpected alert: " + alert);
        signUp.acceptAlert();
    }

    @Test
    public void signUp_ExistingUser_ShouldShowExistsAlert() {
        SignUpPage signUp = new SignUpPage(driver);
        signUp.openSignUpModal();
        signUp.signUp(TestData.USERNAME, TestData.PASSWORD);

        String alert = signUp.getAlertText();
        Assert.assertTrue(alert.toLowerCase().contains("already"), "Unexpected alert: " + alert);
        signUp.acceptAlert();
    }
}
