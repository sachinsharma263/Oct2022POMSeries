package com.qa.trcrm.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.trcrm.base.BasePage;
import com.qa.trcrm.pages.ContactsPage;
import com.qa.trcrm.pages.HomePage;
import com.qa.trcrm.pages.LoginPage;
import com.qa.trcrm.utils.AppConstants;
import com.qa.trcrm.utils.ExcelUtil;

public class ContactsPageTest {

	BasePage basePage;
	Properties prop;
	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;
	ContactsPage contactPage;

	@BeforeMethod
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_properties();
		driver = basePage.init_driver(prop);
		loginPage = new LoginPage(driver);
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		contactPage = homePage.clickOnContacts();

	}

	@DataProvider
	public Object[][] getData() {
		Object data[][] = ExcelUtil.getTestData(AppConstants.CONTACTS_SHEET_NAME);
		return data;
	}

	@Test(priority = 2,dataProvider = "getData")
	public void verifyAddPersonTest(String firstName, String email) {
		String addPersonMsg = contactPage.addPerson(firstName, email);
		Assert.assertEquals(addPersonMsg, AppConstants.CONTACTS_PERSON_ADDED);

	}
	@Test(priority = 1)
	public void verifyContactPageHeaderTest() {
		Assert.assertEquals(contactPage.verifyContactPageHeader(),AppConstants.CONTACTS_PAGE_HEADER);
	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
