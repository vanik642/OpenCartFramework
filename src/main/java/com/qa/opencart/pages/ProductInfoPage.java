package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private final By productHeader=By.tagName("h1");
	private final By productImages=By.cssSelector("ul.thumbnails img");
	private final By productMetaData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private final By productPriceData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private Map<String,String> productMap;
	
	
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getProductHeader() {
		String header=eleUtil.waitForElementVisible(productHeader, AppConstants.LONG_DEFAUL_TIMEOUT).getText();
		System.out.println("Product Header :"+header);
		return header;
	}
	
	public int getProductImagesCount() {
		int imageCount=eleUtil.waitForAllElementsVisible(productImages, AppConstants.DEFAULT_TIMEOUT).size();
		System.out.println("Total Number of product images :"+imageCount);
		return imageCount;
	}
	
	public Map<String,String> getProductDetailsMap() {
		productMap=new TreeMap<String, String>();//to Sorted order for keys
		productMap.put("productheader", getProductHeader());
		productMap.put("productimages",String.valueOf(getProductImagesCount()));  //int to string
		getProductMetaData();
		getProductPriceData();
		System.out.println("Full product details :"+productMap);
		return productMap;
	}
	
	private void getProductMetaData() {
		
		List<WebElement> metaList=eleUtil.waitForAllElementsVisible(productMetaData, AppConstants.DEFAULT_TIMEOUT);
		
		
		for(WebElement e:metaList) {
			String metaData=e.getText();
			String meta[]=metaData.split(":");
			String metaKey=meta[0].trim();
			String metaValue=meta[1].trim();
			productMap.put(metaKey, metaValue);
		}
	
	}
	
	private void getProductPriceData() {
		List<WebElement> priceList=eleUtil.waitForAllElementsVisible(productPriceData, AppConstants.DEFAULT_TIMEOUT);
		String productPrice=priceList.get(0).getText();
		String exTaxPrice=priceList.get(1).getText().split(":")[1].trim();	
		productMap.put("productprice",productPrice);
		productMap.put("exTaxPrice",exTaxPrice);
	
	}
	
	
	
	
	
	
}
