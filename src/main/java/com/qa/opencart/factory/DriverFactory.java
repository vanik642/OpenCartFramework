package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public static String highlight;
	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	// warn, info ,error ,fatal;
	/**
	 * This method is used to init the driver on the basis of given browser name
	 * 
	 * @param browserName
	 */

	public WebDriver initDriver(Properties prop) {
		log.info("-----properties-----: " + prop);
		String browserName = prop.getProperty("browser");
		log.info("browser name : " + browserName);
		ChainTestListener.log("browser name  " + browserName);
		optionsManager = new OptionsManager(prop);
		highlight = prop.getProperty("highlight");
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				
				//run on selenium server/aws/machine
				initRemoteDriver("chrome");
				
			}
			else {
				//run in local
				driver = new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(driver);
			}
		
			break;
		case "edge":

			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				
				//run on selenium server/aws/machine
				initRemoteDriver("edge");
				
			}
			else {
				//run in local
				driver = new EdgeDriver(optionsManager.getEdgeOptions());
				tlDriver.set(driver);
			}
			break;
		case "firefox":

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {

				// run on selenium server/aws/machine
				initRemoteDriver("firefox");

			} else {
				// run in local
				driver =new FirefoxDriver(optionsManager.getFirefoxOptions());
				tlDriver.set(driver);
			}
			
			break;
		case "safari":
			driver = new SafariDriver();
			break;

		default:
			log.error("----please pass  the valid browser name----..." + browserName);
			throw new BrowserException("===INVALID BROWSER===");
		}

		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		return getDriver();
	}

	
	//run in remote grid
	private void initRemoteDriver(String browserName) {
	    switch (browserName) {
		case "chrome":
			
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		
		case "firefox":

			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case "edge":

			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		default:
			System.out.println("this browser is not supported on selenium GRID server ..."+browserName);
			throw new BrowserException("=====INVALID BROWSER=====");
			
		}
		
	}

	/**
	 * getDriver: get the local thread copy of the driver
	 * 
	 */

	public static WebDriver getDriver() {
		return tlDriver.get();

	}

	/**
	 * this is used to init the config properties
	 * 
	 * @return
	 */

	// mvn clean install -Denv="stage"
	public Properties initProp() {

		String envName = System.getProperty("env"); // command line variable env
		System.out.println("Running tests on env :" + envName);
		FileInputStream ip = null;
		prop = new Properties();

		try {
			if (envName == null) {
				log.warn("env is null ,hence running the testes on QA env...");

				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				log.info("Running tests on env..." + envName);
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
					break;

				default:
					log.error("----invalid env name----" + envName);
					throw new FrameworkException("===INVALID ENV NAME===" + envName);

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * takescreenshot
	 * 
	 * 
	 */

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		return srcFile;
	}
	
	public  File getScreenshotFile1() {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);

	}

}
