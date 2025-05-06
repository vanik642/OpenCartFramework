package com.qa.opencart.test;

import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.util.CSVUtil;

public class ProductInfoPageTest extends BaseTest{
	
	//BT-->BC
		@BeforeClass
		public void productInfoSetup() {
			accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
			
		}
		
		//AAA Arrange Act Assert
		
		@DataProvider
		public Object[][] getProductTestData() {
			return new Object[][] {
				{"macbook","MacBook Pro"},
				{"macbook","MacBook Air"},
				{"imac","iMac"},
				{"Samsung","Samsung SyncMaster 941BW"},
				{"Samsung","Samsung Galaxy Tab 10.1"}
			};
			
		}
		
		@DataProvider
		public Object[][] getProductCSVData() {
			
			return CSVUtil.csvData("product");
			
		}
		
		@Test(dataProvider = "getProductTestData")
		public void productHeaderTest(String searchKey,String productName) {
			searchResultsPage =accPage.doSearch(searchKey);
			productInfoPage=searchResultsPage.selectProduct(productName);
			String actHeader=productInfoPage.getProductHeader();
			Assert.assertEquals(actHeader,productName);
			
		}
		
		@DataProvider
		public Object[][] getProductImagesTestData() {
			return new Object[][] {
				{"macbook","MacBook Pro",4},
				{"macbook","MacBook Air",4},
				{"imac","iMac",3},
				{"Samsung","Samsung SyncMaster 941BW",1},
				{"Samsung","Samsung Galaxy Tab 10.1",7}
			};
			
		}
		
		@Test(dataProvider = "getProductCSVData")
		public void productImageCountTest(String searchKey, String productName, String expectedImageCount) {
			searchResultsPage =  accPage.doSearch(searchKey);
			productInfoPage = searchResultsPage.selectProduct(productName);
			int actImageCount = productInfoPage.getProductImagesCount();
			Assert.assertEquals(String.valueOf(actImageCount), expectedImageCount);
		}
		
		
		
//		
//		public void productImageCountTest() {
//			searchResulsPage =accPage.doSearch("macbook");
//			productInfoPage=searchResulsPage.selectProduct("MacBook Pro");
//			int actImageCount=productInfoPage.getProductImagesCount();
//			Assert.assertEquals(actImageCount,4);
//			
//		}
		

		@Test 
		public void productInfoTest() {
			searchResultsPage =accPage.doSearch("macbook");
			productInfoPage=searchResultsPage.selectProduct("MacBook Pro");
			Map<String,String> actProductDetailsMap=productInfoPage.getProductDetailsMap();
//			Assert.assertEquals(actProductDetailsMap.get("productheader"),"MacBook Pro");
//			Assert.assertEquals(actProductDetailsMap.get("productimages"),"4");
//			Assert.assertEquals(actProductDetailsMap.get("Brand"),"Apple");
//			Assert.assertEquals(actProductDetailsMap.get("Product Code"),"Product 18");
//			Assert.assertEquals(actProductDetailsMap.get("Reward Points"),"800");
//			Assert.assertEquals(actProductDetailsMap.get("Availability"),"Out Of Stock");
//			Assert.assertEquals(actProductDetailsMap.get("productprice"),"$2,000.00");
//			Assert.assertEquals(actProductDetailsMap.get("exTaxPrice"),"$2,000.00");
			
			SoftAssert softAssert=new SoftAssert();
			softAssert.assertEquals(actProductDetailsMap.get("productheader"),"MacBook Pro");
			softAssert.assertEquals(actProductDetailsMap.get("productimages"),"4");
			softAssert.assertEquals(actProductDetailsMap.get("Brand"),"Apple");
			softAssert.assertEquals(actProductDetailsMap.get("Product Code"),"Product 18");
			softAssert.assertEquals(actProductDetailsMap.get("Reward Points"),"800");
			softAssert.assertEquals(actProductDetailsMap.get("Availability"),"Out Of Stock");
			softAssert.assertEquals(actProductDetailsMap.get("productprice"),"$2,000.00");
			softAssert.assertEquals(actProductDetailsMap.get("exTaxPrice"),"$2,000.00");
			softAssert.assertAll();
		}
		

}
