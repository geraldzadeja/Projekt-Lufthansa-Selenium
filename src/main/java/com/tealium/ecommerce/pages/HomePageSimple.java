package com.tealium.ecommerce.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageSimple {

    private WebDriver driver;
    private Actions actions;

    @FindBy(xpath = "//a[@title='Account']")
    private WebElement accountMenu;

    @FindBy(xpath = "//a[@title='Register']")
    private WebElement registerLink;

    @FindBy(xpath = "//a[@title='Sign In']")
    private WebElement signInLink;

    @FindBy(xpath = "//a[@title='Woman']")
    private WebElement womanMenu;

    @FindBy(xpath = "//a[@title='Man']")
    private WebElement manMenu;

    @FindBy(xpath = "//a[@title='Sale']")
    private WebElement saleMenu;

    public HomePageSimple(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickAccountMenu() {
        accountMenu.click();
    }

    public void clickRegister() {
        registerLink.click();
    }

    public void clickSignIn() {
        signInLink.click();
    }

    public void hoverOverWomanMenu() {
        actions.moveToElement(womanMenu).perform();
    }

    public void hoverOverManMenu() {
        actions.moveToElement(manMenu).perform();
    }

    public void hoverOverSaleMenu() {
        actions.moveToElement(saleMenu).perform();
    }
}
