package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

//import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

import io.qameta.allure.Step;

import static com.qa.opencart.constants.AppConstants.*;  //Import static methods to avoid AppConstants.HOME_PAGE_TITLE

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	//1.private By locators
	private final By email=By.id("input-email");
	private final By password=By.id("input-password");
	private final By loginBtn=By.xpath("//input[@value='Login']");
	private final By forgotPwdLink=By.linkText("Forgotten Password");
	private final By registerLink=By.linkText("Register");
	
	private static final Logger log = LogManager.getLogger(LoginPage.class);
	//2.public page constr..
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		this.eleUtil=new ElementUtil(driver);
	}
	
	//3.Public page actions/method
	@Step("getting the login page title")
	public String getLoginPageTitle() {
		String title=eleUtil.waitForTitleIs(LOGIN_PAGE_TITLE,DEFAULT_TIMEOUT);
		
		//String title=driver.getTitle();
		System.out.println("login page title:"+ title);
		return title;
	}
	
	@Step("getting login page url")
	public String getLoginURL() {
		String url=eleUtil.waitForURLContains(LOGIN_PAGE_FRACTIONAL_URL,DEFAULT_TIMEOUT);
		log.info("*************login page url***************"+ url);
		return url;
	}
	
	@Step("checking forgot pwd link exist")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
		
	}
	
	@Step("login with valid username: {0} and password :{1}")
	public AccountsPage doLogin(String username,String pwd){
		System.out.print("user credentials: " + username + ":" +pwd);
		eleUtil.waitForElementVisible(email, MEDIUM_DEFAULT_TIMEOUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("navigationg to the registeration page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.clickWhenReady(registerLink, DEFAULT_TIMEOUT);
		return new RegisterPage(driver);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
