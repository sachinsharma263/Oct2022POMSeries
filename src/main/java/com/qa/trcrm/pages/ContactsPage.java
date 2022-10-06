package com.qa.trcrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.trcrm.utils.ElementUtil;
import com.qa.trcrm.utils.JavaScriptUtil;

public class ContactsPage {

	WebDriver driver;
	ElementUtil util;
	JavaScriptUtil jsUtil;
	
	By contactPageHeader=By.xpath("(//h2[text()])[1]");

	By addPersonButton = By.xpath("//button[@class='hidden-xs hidden-sm btn btn-danger mr5 ng-scope ng-binding']");
	By name = By.name("name");
	By emailId = By.id("email0");
	By saveButton = By.xpath("//button[@class='btn btn-primary btn-large ng-binding']");

	By personAddedMsg = By.xpath("//span[text()='Person added.']");

	public ContactsPage(WebDriver driver) {
		this.driver = driver;
		util = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
	}
	public String verifyContactPageHeader() {
		util.waitForPresenceOfElementLocated(contactPageHeader);
		return util.doGetText(contactPageHeader);
	}

	public String addPerson(String firstName, String email) {
		util.waitForPresenceOfElementLocated(addPersonButton);
		util.doClick(addPersonButton);
		util.waitForPresenceOfElementLocated(name);
		util.doSendKeys(name, firstName);
		util.doSendKeys(emailId, email);
		util.doClick(saveButton);

		util.waitForPresenceOfElementLocated(personAddedMsg);

		return util.doGetText(personAddedMsg);
	}
}
