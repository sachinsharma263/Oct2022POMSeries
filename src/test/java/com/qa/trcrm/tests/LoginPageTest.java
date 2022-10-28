package com.qa.trcrm.tests;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.trcrm.base.BasePage;
import com.qa.trcrm.pages.HomePage;
import com.qa.trcrm.pages.LoginPage;
import com.qa.trcrm.pojo.Credentials;
import com.qa.trcrm.utils.AppConstants;
import com.qa.trcrm.utils.JiraPolicy;
import com.qa.trcrm.utils.Log;

public class LoginPageTest {

	BasePage basePage;
	Properties prop;

	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;

	Credentials credentials;
	Logger logger;

	@BeforeTest
	public void setUp() {
		// logger=LogManager.getLogger(LoginPageTest.class.getName());//do we need to
		// create instance of logger class in each test and page class
		basePage = new BasePage();

		Log.info("base page laucnhed");
		prop = basePage.init_properties();

		Log.error("prop init");
		driver = basePage.init_driver(prop);

		Log.debug("driver laucnhed");

		loginPage = new LoginPage(driver);
		if (System.getProperty("userName").isEmpty() && System.getProperty("password").isEmpty()) {
			credentials = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
		} else {
			credentials = new Credentials(System.getProperty("userName"), System.getProperty("password"));
		}

		// credentials = new Credentials(System.getProperty("userName"),
		// System.getProperty("password"));
	}

	@JiraPolicy(logTicketReady = true)
	@Test(priority = 1, enabled = true, description = "verify Login Page Title is correct or not")
	public void verifyLoginPageTitleTest() {
		Log.info("getting Login page title");
		String title = loginPage.getTitle();
		Log.info("Login page title is: " + title);
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);

	}

	@JiraPolicy(logTicketReady = true)
	@Test(priority = 2, enabled = true, description = "verify SignUp Link is correct or not")
	public void verifySignUpLinkTest() {

		Assert.assertTrue(loginPage.verifySignUpLink());

	}

	@Test(priority = 3, enabled = true, description = "verify login feature")
	public void doLoginTest() {

		homePage = loginPage.doLogin(credentials);
		Assert.assertEquals(homePage.getHomePageHeader(), AppConstants.HOME_PAGE_HEADER);

	}

	@DataProvider
	public Object[][] getLoginInvalidData() {
		Object[][] data = { { "test@gmail.com", "test@123" }, { "test2@gmail.com", "test@123" }

		};
		return data;
	}

	@Test(dataProvider = "getLoginInvalidData", enabled = false)
	public void login_InvalidTestCases(String username, String password) {
		loginPage.doLogin(username, password);
		Assert.assertTrue(loginPage.errorMessage());
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
