package com.qa.trcrm.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.trcrm.base.BasePage;
import com.qa.trcrm.pages.HomePage;
import com.qa.trcrm.pages.LoginPage;
import com.qa.trcrm.utils.AppConstants;

public class LoginPageTest3 {

	BasePage basePage;
	Properties prop;

	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;

	@BeforeTest
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_properties();
		driver = basePage.init_driver(prop);

		loginPage = new LoginPage(driver);
	}

	@Test(priority = 1,enabled = true)
	public void verifyLoginPageTitleTest() {
		String title = loginPage.getTitle();
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test(priority = 2,enabled = true)
	public void verifySignUpLinkTest() {
		Assert.assertTrue(loginPage.verifySignUpLink());
	}

	@Test(priority = 3,enabled = true)
	public void doLoginTest() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(homePage.getHomePageHeader(), AppConstants.HOME_PAGE_HEADER);
	}
	@DataProvider
	public Object[][] getLoginInvalidData() {
		Object[][] data= {
				{"test@gmail.com","test@123"},
				{"test2@gmail.com","test@123"}
				
		};
		return data;
	}
	@Test(dataProvider = "getLoginInvalidData",enabled = false)
	public void login_InvalidTestCases(String username,String password) {
		loginPage.doLogin(username, password);
		Assert.assertTrue(loginPage.errorMessage());
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
