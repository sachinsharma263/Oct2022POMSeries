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
		logger=LogManager.getLogger(LoginPageTest.class.getName());//do we need to create instance of logger class in each test and page class
		basePage = new BasePage();
		logger.info("base page laucnhed");
		prop = basePage.init_properties();
		logger.error("prop init");
		driver = basePage.init_driver(prop);
		logger.debug("driver laucnhed");

		loginPage = new LoginPage(driver);
		credentials=new Credentials(prop.getProperty("username"), prop.getProperty("password"));
	}

	@JiraPolicy(logTicketReady = true)
	@Test(priority = 1,enabled = true)
	public void verifyLoginPageTitleTest() {
		logger.info("****************************** starting test case *****************************************");
		logger.info("****************************** verifyLoginPageTitleTest *****************************************");
		String title = loginPage.getTitle();
		logger.info("title:"+title);
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
		logger.info("****************************** ending test case *****************************************");
		logger.info("****************************** verifyLoginPageTitleTest *****************************************");

	}

	@JiraPolicy(logTicketReady = true)
	@Test(priority = 2,enabled = true)
	public void verifySignUpLinkTest() {
		logger.info("****************************** starting test case *****************************************");
		logger.info("****************************** verifySignUpLinkTest *****************************************");
		try{Assert.assertTrue(loginPage.verifySignUpLink());}catch(Exception e) {}
		logger.info("****************************** ending test case *****************************************");
		logger.info("****************************** verifySignUpLinkTest *****************************************");
	}

	@Test(priority = 3,enabled = true)
	public void doLoginTest() {
		logger.info("****************************** starting test case *****************************************");
		logger.info("****************************** doLoginTest *****************************************");
		homePage = loginPage.doLogin(credentials);
		Assert.assertEquals(homePage.getHomePageHeader(), AppConstants.HOME_PAGE_HEADER);
		logger.info("****************************** ending test case *****************************************");
		logger.info("****************************** doLoginTest *****************************************");
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
