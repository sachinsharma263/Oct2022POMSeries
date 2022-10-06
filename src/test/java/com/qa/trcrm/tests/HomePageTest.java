package com.qa.trcrm.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.trcrm.base.BasePage;
import com.qa.trcrm.pages.HomePage;
import com.qa.trcrm.pages.LoginPage;
import com.qa.trcrm.utils.AppConstants;

public class HomePageTest {

	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	HomePage homePage;

	WebDriver driver;

	@BeforeTest
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_properties();
		driver = basePage.init_driver(prop);
		loginPage = new LoginPage(driver);
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}

	@Test
	public void verifyHomePageTitleTest() {
		String homePageTitle = homePage.getHomePageTitle();
		Assert.assertEquals(homePageTitle, AppConstants.HOME_PAGE_TITLE);
	}

	@Test
	public void verifyHomePageHeaderTest() {
		String homePageHeader = homePage.getHomePageHeader();
		Assert.assertEquals(homePageHeader, AppConstants.HOME_PAGE_HEADER);
	}

	@Test
	public void verifyLoggedInUserTest() {
		String loggedInUser = homePage.loggedInUser();
		Assert.assertEquals(loggedInUser,prop.getProperty("accountname"));
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
