package com.qa.trcrm.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class BasePage {

	public WebDriver driver;
	public Properties prop;

	OptionsManager options;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	public WebDriver init_driver(Properties prop) {
		String browserName = prop.getProperty("browser");
		// String headless = prop.getProperty("headless");
		// Boolean isHeadLess = Boolean.parseBoolean(headless);

		options = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver(options.getChromeOptions()));

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver(options.getFirefoxOptions()));

		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();

			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("safari")) {
			WebDriverManager.getInstance(SafariDriver.class).setup();
			driver = new SafariDriver();
		} else {
			System.out.println(browserName + " not found");
			try {
				throw new Exception("NoSuchBrowserFoundException");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();

		return getDriver();
	}

	public WebDriver init_driver2(Properties prop, String browserName) {

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			System.out.println(browserName + " not found");
		}
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();

		return driver;
	}

	public Properties init_properties() {
		FileInputStream fis = null;
		String env = null;
		String path = null;

		try {
			env = System.getProperty("env");

			if (env == null) {
				env = "qa";
			}
			switch (env) {
			case "qa":
				path = "./src/main/java/com/qa/trcrm/config/config_qa.properties";
				break;
			case "prod":
				path = "./src/main/java/com/qa/trcrm/config/config_prod.properties";
				break;

			default:
				path = "./src/main/java/com/qa/trcrm/config/config_qa.properties";
				break;
			}

			fis = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prop = new Properties();
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * take screenshot util
	 * 
	 * @return path of the file
	 */
	public String getScreenshot() {

		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		//String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		String path = System.getProperty("user.dir") + "/screenshots/" + getDateTime() + ".png";

		File destination = new File(path);

		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;

	}

	public String getDateTime() {
		Date date = new Date();
		SimpleDateFormat dateFormt = new SimpleDateFormat("ddMMyyhhmma");

		return dateFormt.format(date);
	}
}
