package com.qa.trcrm.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.trcrm.base.BasePage;
import com.qa.trcrm.pages.HomePage;
import com.qa.trcrm.pages.LoginPage;
import com.qa.trcrm.utils.AppConstants;

public class LoginPageTest2 {

	BasePage basePage;
	Properties prop;

	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;

	@BeforeTest
	@Parameters({"browser"})
	public void setUp(String browserName) {
		basePage = new BasePage();
		prop = basePage.init_properties();
		driver = basePage.init_driver2(prop,browserName);

		loginPage = new LoginPage(driver);
	}

	@Test(priority = 1)
	public void verifyLoginPageTitleTest() {
		String title = loginPage.getTitle();
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test(priority = 2)
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.verifySignUpLink());
	}

	@Test(priority = 3)
	public void doLoginTest() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(homePage.getHomePageHeader(), AppConstants.HOME_PAGE_HEADER);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
