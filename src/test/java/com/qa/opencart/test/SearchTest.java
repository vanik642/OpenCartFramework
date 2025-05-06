package com.qa.opencart.test;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchTest extends BaseTest{
	
	
	@BeforeClass
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void searchTest() {
		searchResultsPage = accPage.doSearch("macbook");
		int actResultsCount = searchResultsPage.getResultsProductCount();
		Assert.assertEquals(actResultsCount, 3);
	}
}
