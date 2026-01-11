package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.HomePage;
import com.tealium.ecommerce.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignInTest extends BaseTest {

    @Test(dependsOnMethods = {"com.tealium.ecommerce.tests.CreateAccountTest.testCreateAccount"})
    public void testSignIn() {
        String email = CreateAccountTest.testEmail;
        String password = CreateAccountTest.testPassword;
        String firstName = CreateAccountTest.testFirstName;

        System.out.println("DEBUG: Signing in with email: " + email);

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        homePage.clickAccountLink();
        loginPage.clickSignInLink();

        loginPage.login(email, password);

        String currentUrl = driver.getCurrentUrl();
        String pageTitle = driver.getTitle();
        System.out.println("DEBUG: After login URL = " + currentUrl);
        System.out.println("DEBUG: After login title = " + pageTitle);

        Assert.assertTrue(loginPage.isUsernameDisplayed(),
            "Username should be displayed after login");

        String welcomeText = loginPage.getWelcomeMessageText();
        System.out.println("DEBUG: Welcome message = " + welcomeText);

        Assert.assertTrue(welcomeText.contains(firstName) || welcomeText.toLowerCase().contains("welcome") || welcomeText.toLowerCase().contains("account"),
            "Welcome message should contain user's name or 'welcome'. Found: " + welcomeText);

        loginPage.logout();

        System.out.println("DEBUG: Logged out successfully");
    }
}
