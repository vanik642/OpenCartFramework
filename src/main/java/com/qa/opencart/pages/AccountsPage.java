package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.qa.opencart.constants.AppConstants.*;

import java.util.ArrayList;
import java.util.List;

import com.qa.opencart.util.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private final By headers=By.cssSelector("div#content>h2");
	private final By search=By.name("search");
	private final By searchIcon=By.cssSelector("div#search button");
	
	public AccountsPage(WebDriver driver)  {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}

	public String getAccPageTitle() {
		String title=eleUtil.waitForTitleIs(HOME_PAGE_TITLE,DEFAULT_TIMEOUT);
		System.out.println("Home page title "+title);
		return title;
		
	}
	
	public String getAccPageURL() {
		String url=eleUtil.waitForURLContains(HOME_PAGE_FRACTIONAL_URL,DEFAULT_TIMEOUT);
		System.out.println("Home page url "+url);
		return url;
		
	}
	
	public List<String> getAccPageHeader() {
		List<WebElement> headerList=eleUtil.getElements(headers);
		;
		List<String> headerValList=new ArrayList<String>();
		for(WebElement e:headerList) {
			String text=e.getText();
			headerValList.add(text);
			
		}
		System.out.println("Account Page Header List :"+headerValList);
		return headerValList;
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("Search key :" +searchKey);
		eleUtil.doSendKeys(search, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}
	

}
