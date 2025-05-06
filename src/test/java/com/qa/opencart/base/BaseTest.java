 package com.qa.opencart.base;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;


import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;


//@Listeners(ChainTestListener.class)
public class BaseTest  {
	
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	private static final Logger log = LogManager.getLogger(BaseTest.class);

	@BeforeTest
	public void setUp() {
		df=new DriverFactory();
		prop=df.initProp();
		ChainTestListener.log("properties used  "+prop);
		driver=df.initDriver(prop);    //call by reference
		loginPage=new LoginPage(driver);
		
		
	}
	
	@BeforeMethod
	public void beforeMethod(ITestContext result) {
		
		log.info(result.getName()+"*******Starting test case******" +result.getName());
	}
	
	
	@AfterMethod //running after each @test method
	public void attachScreenshot(ITestResult result) {
		if(!result.isSuccess()) { //only for failure test case
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}
		
		log.info("******Ending test case*******"+result.getMethod().getMethodName());
		
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
		
	}
	

}
























