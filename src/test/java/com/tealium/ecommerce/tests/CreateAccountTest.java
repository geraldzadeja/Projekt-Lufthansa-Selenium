package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import com.tealium.ecommerce.pages.HomePage;
import com.tealium.ecommerce.pages.RegistrationPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class CreateAccountTest extends BaseTest {

    protected static String testEmail;
    protected static String testPassword;
    protected static String testFirstName;
    protected static String testLastName;

    @Test
    public void testCreateAccount() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        homePage.clickAccountLink();
        registrationPage.clickRegisterLink();

        registrationPage.waitForUrlContains("account/create");

        String pageTitle = registrationPage.getPageTitle();
        String currentUrl = driver.getCurrentUrl();
        System.out.println("DEBUG: Page title = '" + pageTitle + "'");
        System.out.println("DEBUG: Current URL = " + currentUrl);

        boolean onRegistrationPage = pageTitle.contains("Create") ||
                                     currentUrl.contains("account/create") ||
                                     currentUrl.contains("register") ||
                                     currentUrl.contains("customer/account/create");

        Assert.assertTrue(onRegistrationPage,
            "Should be on registration page. Title: '" + pageTitle + "', URL: " + currentUrl);

        testEmail = "testuser" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        testFirstName = "Test";
        testLastName = "User";
        testPassword = "TestPassword123!";

        registrationPage.fillRegistrationForm(testFirstName, testLastName, testEmail, testPassword);
        registrationPage.submitForm();

        String afterSubmitUrl = driver.getCurrentUrl();
        String afterSubmitTitle = driver.getTitle();
        System.out.println("DEBUG: After submit URL = " + afterSubmitUrl);
        System.out.println("DEBUG: After submit title = " + afterSubmitTitle);

        registrationPage.debugPageMessages();

        Assert.assertTrue(registrationPage.isSuccessMessageDisplayed(),
            "Success message should be displayed after registration");

        registrationPage.logout();
        System.out.println("DEBUG: Logged out successfully");
    }
}
