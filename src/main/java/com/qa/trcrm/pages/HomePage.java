package com.qa.trcrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.trcrm.base.BasePage;
import com.qa.trcrm.utils.ElementUtil;
import com.qa.trcrm.utils.JavaScriptUtil;

public class HomePage extends BasePage {

	WebDriver driver;
	ElementUtil util;
	JavaScriptUtil jsUtil;

	By homePageHeader = By.xpath("//span[text()='Homepage']");
	By loggedInUser = By.xpath("//span[text()=' sachin sharma']");

	By contactPageLink = By.xpath("//li[@id='contactMenuLi']/a");

	public HomePage(WebDriver driver) {
		this.driver = driver;
		util = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	public String getHomePageTitle() {
		return util.doGetTitle();
	}

	public String getHomePageHeader() {
		if (util.doIsDisplayed(homePageHeader)) {
			return util.doGetText(homePageHeader);
		}
		return null;
	}

	public String loggedInUser() {
		if (util.doIsDisplayed(loggedInUser)) {
			return util.doGetText(loggedInUser);
		}
		return null;
	}

	public ContactsPage clickOnContacts() {

		util.waitForElementVisible(contactPageLink);
		jsUtil.clickElementByJS(util.getElement(contactPageLink));

		return new ContactsPage(driver);

	}
}
