package com.qa.opencart.test;

import org.testng.Assert;
import static com.qa.opencart.constants.AppConstants.*;  //Import static methods to avoid AppConstants.HOME_PAGE_TITLE
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;

import com.qa.opencart.pages.LoginPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Feature("F 50:Open Cart - Login Feature")
@Epic("Epic 100: design pages for open cart application")
@Story("US 101 : implement login page for open cart application")
public class LoginPageTest extends BaseTest {
	
	@Description("checking open carrt login page url")
	@Severity(SeverityLevel.MINOR)
	@Owner("Vanita")
	@Test(description="checking login page title")

	public void loginPageTitle() {
		System.out.println("----starting test case----");
		String actTitle=loginPage.getLoginPageTitle(); 
		ChainTestListener.log("Checking Login page title :"+actTitle);
		Assert.assertEquals(actTitle,LOGIN_PAGE_TITLE);
		System.out.println("----ending test case----");
	
	}
	
	@Description("checking open carrt login page url")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Vanita")
	@Test(description="checking login page url")
	public void loginPageURL() {
		String actURL=loginPage.getLoginURL(); 
		Assert.assertTrue(actURL.contains(LOGIN_PAGE_FRACTIONAL_URL));
	
	}

	@Description("checking open cart login page has forgot pwd link...")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Vanita")
	@Test(description="forgotPwdLinkExistsTest")
	
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
		
	}
	
	@Description("check user is able to login with valid user credntials...")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Vanita")
	@Test(priority = Short.MAX_VALUE,description = "Login with valid credentials")
	public void dologinTest() throws InterruptedException {
		
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(),HOME_PAGE_TITLE);
	}
	
	
	@Test(enabled=false,description = "WIP --forgot pass check")
	public void forgotPwd() {
		System.out.println("forgot pwd");
	}

}
