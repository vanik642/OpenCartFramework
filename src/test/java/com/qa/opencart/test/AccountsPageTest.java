package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import static com.qa.opencart.constants.AppConstants.*;

import java.util.List;

public class AccountsPageTest extends BaseTest{
	
	//BT-->BC
	@BeforeClass
	public void accPAgeSetup() {
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		
	}
	
	@Test
	public void accPageTitleTest() {
		Assert.assertEquals(accPage.getAccPageTitle(),HOME_PAGE_TITLE );
		
	}
	

	@Test
	public void accPageURLTest() {
		Assert.assertTrue(accPage.getAccPageURL().contains(HOME_PAGE_FRACTIONAL_URL));
		
	}
	
	@Test
	public void accPageHeaderListTest() {
		List<String> actHeaderList=accPage.getAccPageHeader();
		Assert.assertEquals(actHeaderList,AppConstants.expectedAccPageHeadersList);
	}

}
