package com.qa.trcrm.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {

	WebDriver driver;
	Actions action;
	WebDriverWait wait;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
		wait=new WebDriverWait(driver, AppConstants.DEFAULT_TIME_OUT);
	}

	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			return driver.findElement(locator);
		} catch (Exception e) {
			System.out.println("some exception occured while creating the element: " + locator);
			Log.error("some exception occured while creating the element: " + locator);
			Log.error(e.getMessage());
		}
		return element;
	}

	public void doSendKeys(By locator, String value) {
		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetTitle() {
		return driver.getTitle();
	}

	public void doActionClick(By locator) {
		WebElement element = getElement(locator);
		action.click(element).build().perform();
	}

	public void doActionSendkeys(By locator, String value) {
		action.sendKeys(getElement(locator), value).build().perform();
	}

	public void doMoveToElement(By locator) {
		action.moveToElement(getElement(locator)).build().perform();
	}
	public void waitForPresenceOfElementLocated(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	public void waitForElementVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOf(getElement(locator)));
	}

}
