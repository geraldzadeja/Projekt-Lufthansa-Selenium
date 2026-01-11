package com.tealium.ecommerce.tests;

import com.tealium.ecommerce.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

public class DiagnosticTest extends BaseTest {

    @Test
    public void diagnoseRegistrationForm() {
        System.out.println("\n=== DIAGNOSTIC TEST START ===\n");

        driver.findElement(By.cssSelector("a[href*='account']")).click();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        driver.findElement(By.xpath("//a[@title='Register']")).click();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        System.out.println("1. Current URL: " + driver.getCurrentUrl());
        System.out.println("2. Page Title: " + driver.getTitle());

        String email = "testuser" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        driver.findElement(By.id("firstname")).sendKeys("Test");
        driver.findElement(By.id("lastname")).sendKeys("User");
        driver.findElement(By.id("email_address")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys("TestPassword123!");

        List<WebElement> passwordConfirmFields = driver.findElements(By.cssSelector(
            "input[name='password_confirmation'], input[id='password-confirmation'], input[id='confirmation']"));
        System.out.println("3. Found " + passwordConfirmFields.size() + " password confirmation fields");
        if (passwordConfirmFields.size() > 0) {
            passwordConfirmFields.get(0).sendKeys("TestPassword123!");
            System.out.println("4. Password confirmation field filled");
        }

        List<WebElement> allButtons = driver.findElements(By.tagName("button"));
        System.out.println("5. Found " + allButtons.size() + " buttons on page:");
        for (int i = 0; i < allButtons.size(); i++) {
            WebElement btn = allButtons.get(i);
            try {
                String type = btn.getAttribute("type");
                String title = btn.getAttribute("title");
                String className = btn.getAttribute("class");
                String text = btn.getText();
                boolean displayed = btn.isDisplayed();

                System.out.println("   Button " + i + ":");
                System.out.println("      type=" + type);
                System.out.println("      title=" + title);
                System.out.println("      class=" + className);
                System.out.println("      text=" + text);
                System.out.println("      displayed=" + displayed);
            } catch (Exception e) {
                System.out.println("   Button " + i + ": Error reading attributes");
            }
        }

        try {
            WebElement submitBtn = driver.findElement(By.cssSelector(
                "button[type='submit'], button[title*='Create'], button.action.submit.primary"));
            System.out.println("6. Found submit button: " + submitBtn.getAttribute("outerHTML"));
            System.out.println("7. Button is enabled: " + submitBtn.isEnabled());
            System.out.println("8. Button is displayed: " + submitBtn.isDisplayed());

            submitBtn.click();
            System.out.println("9. Clicked submit button");

            try { Thread.sleep(5000); } catch (InterruptedException e) {}
            System.out.println("10. URL after submit: " + driver.getCurrentUrl());
            System.out.println("11. Title after submit: " + driver.getTitle());

            List<WebElement> errors = driver.findElements(By.cssSelector(
                ".field-error, .mage-error, div[class*='error'], div[class*='message']"));
            System.out.println("12. Found " + errors.size() + " potential error/message elements:");
            for (int i = 0; i < errors.size(); i++) {
                WebElement err = errors.get(i);
                if (err.isDisplayed()) {
                    System.out.println("   Message " + i + ": " + err.getText());
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== DIAGNOSTIC TEST END ===\n");

        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }
}
