package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
//INterview Question
import java.util.List;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {
	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver=driver;
		act=new Actions(driver);
		jsUtil=new JavaScriptUtil(driver);
	}
	
	private void nullCheck(CharSequence... value) {
		if(value==null) {
			throw new RuntimeException("===Value can not be null===");
			
		}
		
	}
	
	
	@Step("finding the element using:{0}")
	public  WebElement getElement(By locator) {
		ChainTestListener.log("locator : "+locator.toString());
		WebElement element=driver.findElement(locator);
		highlightElement(element);
		return element;
	}
	
	private void highlightElement(WebElement element) {
		
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		
	}
	
	public  WebElement getElementWithWait(By locator,int timeOut) {
		return waitForElementVisible(locator, timeOut);
	}
	
	@Step("enterning value : {1} into element :{0}")
	public  void doSendKeys(By locator,String Value) {
		nullCheck(Value);
		getElement(locator).clear();
		getElement(locator).sendKeys(Value);
	}
	
	public  void doSendKeys(By locator,CharSequence... Value) throws InterruptedException {
		nullCheck(Value);
		getElement(locator).sendKeys(Value);
	}
	
	@Step("clicking on element using : {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	@Step("fetching the element text using : {0}")
	public  String doElementGetText(By locator) {
		String eleText=getElement(locator).getText();
		System.out.println("element text==>"+eleText);
		return eleText;
	}
	
	public  String getElementDomAttributeValue(By locator,String attrName) {
		nullCheck(attrName);
		return getElement(locator).getDomAttribute(attrName);
	}
	
	public  String getElementDomPropertyValue(By locator,String propName) {
		nullCheck(propName);
		return getElement(locator).getDomProperty(propName);
	}
	
     public boolean isElementDisplayed(By locator) {
		
		try {
			return getElement(locator).isDisplayed();
		}
		catch (NoSuchElementException e) {
			
			//e.printStackTrace();
			System.out.println("elemet is not present on the page  :"+locator);
			return false;
		}
		
				
	}
     
     //********************* findElements Utils ****************//
     

     public  List<String> getElementTextList(By locator) {
 		List<WebElement> eleList=getElements(locator);
 		List<String> eleTExtList=new ArrayList<String>(); //pc=0 []
 		
 		
 		for(WebElement e:eleList) {
 			String text=e.getText();
 			if(text.length()!=0) {
 				eleTExtList.add(text);
 			}
 			}
 		return eleTExtList;
 				
 	}
 	
 	
 	public int getElementsCount(By locator) {
 		int eleCount=getElements(locator).size();
 		System.out.println("element count==>"+eleCount);
 		return eleCount;
 		
 		
 	}
 	
 	//Example of method Overloading
 	public boolean checkSingleElementDisplayed(By locator) {
		if(getElements(locator).size()==1) {
			System.out.println("element :" +locator +" is displayed on the page one time");
			
			return true;
		}
		return false;
	}
	
	public boolean checkSingleElementDisplayed(By locator,int expElementCount) {
		if(getElements(locator).size()==expElementCount) {
			System.out.println("element :" +locator +" is displayed on the page " +expElementCount +"time");
			
			return true;
		}
		return false;
	}
	
	 
	
	public void clickElement(By locator,String value) {
		List<WebElement> languageLinksList=getElements(locator);
		System.out.println(languageLinksList.size());
			for(WebElement  ele:languageLinksList) {
				String text=ele.getText();
				if(text.contains(value)) {
					ele.click();
					break;
				}
			}
			
		}
		
 	
 	public  List<WebElement> getElements(By locator) {
 		return driver.findElements(locator);
 		
 		
 	}
 	
 	//***********************Drop Down Utils -Select bases Dropdown*************//
 	
 	
 	public  boolean doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		try {
			select.selectByIndex(index);
			return true;
		} catch (NoSuchElementException e) {
			System.out.println(index + " is not present in the dropdown");
			return false;
		}
	}

	public  boolean doSelectDropDownByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		try {
			select.selectByVisibleText(visibleText);
			return true;
		} catch (NoSuchElementException e) {
			System.out.println(visibleText + " is not present in the dropdown");
			return false;
		}
	}

	public  boolean doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		try {
			select.selectByValue(value);
			return true;
		} catch (NoSuchElementException e) {
			System.out.println(value + " is not present in the dropdown");
			return false;
		}
	}
	
       
	public  boolean selectDropDownValue(By locator,String value) {
		
		
		Select select=new Select(getElement(locator));
		List<WebElement> selectOptions=select.getOptions();
		boolean flag=false;
		for(WebElement e:selectOptions) {
			String text=e.getText();
			System.out.println(text);
			
			if(text.equals(value)) {
				e.click();
				flag=true;
				break;
			}
			
		}
		if(flag) {
			System.out.println(value + "is selected");
			return true;
		}
		else {
			System.out.println(value + "is not selected");
			return false;
		}
		
	}
	
	public List<String> getDropDownValueList(By locator) {
		Select select=new Select(getElement(locator));
		List<WebElement> optionList=select.getOptions();
		System.out.println(optionList.size());
		List<String> optionValList=new ArrayList<String>();
		for(WebElement ele:optionList) {
			String text=ele.getText();
			System.out.println(text);
			optionValList.add(text.trim()); 
		}
		
		return optionValList;
	}
	
	public boolean getDropDownValueList(By locator,List<String> expOptionLisyt) {
		Select select=new Select(getElement(locator));
		List<WebElement> optionList=select.getOptions();
		System.out.println(optionList.size());
		List<String> optionValList=new ArrayList<String>();
		for(WebElement ele:optionList) {
			String text=ele.getText();
			System.out.println(text);
			optionValList.add(text.trim()); 
		}
		
		if(optionValList.containsAll(expOptionLisyt)) {
			return true;
		} 
		else {
			return false;
		}
	}
	
	//***************drop down utils**********************//
	
	/**
	 * this method is used to select the choice with three different use case:
	 * 1.Single selection:selectChoice(choice,choiceList,"choice 2 1","choice 1");
	 * 2.multi selection:selectChoice(choice,choiceList,"choice 2 1","choice 1","choice 
	 * 3.all selection: use "all/ALL" to select all the choices selectChoice(choice,choiceList,"all");
	 * @param locator
	 * @param choiceList
	 * @param choiceValue
	 * @throws InterruptedException
	 */
	public void selectChoice(By choice,By choiceList,String... choiceValue) throws InterruptedException {
		
		doClick(choice);
		Thread.sleep(2000);
		List<WebElement> choices=getElements(choiceList);
		System.out.println(choices.size());
		if(choiceValue[0].equalsIgnoreCase("all")) {
			//logic to select all the choices:
			for(WebElement e:choices) {
				e.click();
			}
		}
		else {
		
		for(WebElement ele:choices) {
			String text=ele.getText();
			for(String value:choiceValue) {
				if(text.trim().equals(value)) {
					ele.click();
					break;
				}
			}
//			if(ele.getText().equals(choiceValue)) {
//				ele.click();
//				break;
//			}
		}
		}
		
	}
	
	//************Action utils *************//
	
	public void doMoveToElement(By locator) throws InterruptedException {
		//Actions act=new Actions(driver);
		act.moveToElement(getElement(locator)).build().perform();
		Thread.sleep(3000);
	}
	
	public void handleParentSubMenu(By parentMenu,By subMenu ) throws InterruptedException {	
		doMoveToElement(parentMenu);
		doClick(subMenu);
	}
	
	public void handle4LevelMenuHandle(By level1Menu,By level2Menu,By level3Menu,By level4Menu) throws InterruptedException {
		doClick(level1Menu);
		Thread.sleep(3000);
		doMoveToElement(level2Menu);
		Thread.sleep(3000);
		doMoveToElement(level3Menu);
		Thread.sleep(3000);
		doClick(level4Menu);
		
	}
	
	
	public  void doActionsClick(By locator) {
		//Actions act=new Actions(driver);
		act.click(getElement(locator)).perform();
		
		
	}
	
	public  void doActionsSendKeys(By locator,String Value) {
		//Actions act=new Actions(driver);
		act.sendKeys(getElement(locator),Value).perform();
	}
	
	
    public void  doSendKEysWithPause(By locator,String Value,long pauseTime) {
		
        //Actions act=new Actions(driver);
		char val[]=Value.toCharArray();
		
		for(char ch:val) {
			act.sendKeys(getElement(locator),String.valueOf(ch))
				.pause(pauseTime)
					.perform();
		}
		
	}
    
    //******************Wait Utils*********************//
    
    /**
     * An expectation for checking that there is at least one element present on a web page.
     * @param locator
     * @param timeOut
     * @return
     */
    
    public List<WebElement> waitForAllElementPresence(By locator,int timeOut) {
    	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    	return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
    
    
    /**
     * An expectation for checking that all elements present on the web page that match the locator are visible. 
     * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
     * @param locator
     * @param timeOut
     * @return
     */
    
    public List<WebElement> waitForAllElementsVisible(By locator,int timeOut) {
    	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    	try {
    		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (TimeoutException e) {
			return Collections.EMPTY_LIST; //[]
		}
    	
    }
	
	
	
    /**
     * An expectation for checking that an element is present on the DOM of a page.
     *  This does not necessarily mean that the element is visible
     * @param locator
     * @param timeOut
     * @return
     */
    
    @Step("waiting for element using : {0} and timeout :{1}")
    public WebElement waitForElementPresence(By locator,int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
	    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
	}
    
    /**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     *  Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
     * @param locator
     * @param timeOut
     * @return
     */
       public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
    
    /**
     * An expectation for checking an element is visible and enabled such that you can click it.
     * @param locator
     * @param timeOut
     */
     @Step("waiting for element and clicking on it using : {0} and timeout :{1}")
    public void clickWhenReady(By locator,int timeOut) {
    	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    	wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    	
    }
    
    public void clickWithWait(By locator,int timeout) {
    	waitForElementVisible(locator, timeout).click();
    }
    
    public void sendKeysWithWait(By locator,int timeout,CharSequence... value) {
    	waitForElementVisible(locator, timeout).sendKeys(value);
    }
    
    
    //********wait for alert(JS POP)************//
    
    public Alert waitForAlert(int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(3000));
        return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void acceptAlert(int timeOut) {
		waitForAlert(timeOut).accept();	
	}
	
	
	public void dismissAlert(int timeOut) {
		waitForAlert(timeOut).dismiss();	
	}
	

	public  String getTextAlert(int timeOut) {
		return waitForAlert(timeOut).getText()	;
	}
	

	public void sendKeysAlert(int timeOut,String value) {
		waitForAlert(timeOut).sendKeys(value);
	}
	
	
	//**w******Wait For Title**********//
	public String waitFotTitleContains(String fractionTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.titleContains(fractionTitle));
			return driver.getTitle();

		} catch (TimeoutException e) {
			return null;
		}

	}
	
	public String waitForTitleIs(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.titleIs(title));
			return driver.getTitle();

		} catch (TimeoutException e) {
			return null;
		}

	}
	
	//**w******Wait For URL**********//
	public  String waitForURLContains(String fractionTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.urlContains(fractionTitle));
			return driver.getCurrentUrl();

		} catch (TimeoutException e) {
			return null;
		}

	}
	
	public  String waitForURLIs(String url, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.urlToBe(url));
			return driver.getCurrentUrl();

		} catch (TimeoutException e) {
			return null;
		}

	}
	
	//************Wait for Frame***********//
	
	public void waitForFrameAndSwitchToIt(By framelocator,int timeOut) {
		   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		   wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(framelocator));
	
	}
	
	public void waitForFrameAndSwitchToItUsingNameID(String frameNameOrId,int timeOut) {
		   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		   wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));	
	}
	
	public void waitForFrameAndSwitchToItUsingInde(int frameindex,int timeOut) {
		   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		   wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameindex));
	}
	
	public void waitForFrameAndSwitchToItUsingWebElement(WebElement frameElement,int timeOut) {
		   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		   wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

		
	}
	
	//**********Wait for window***********//
	public  boolean waitForWindow(int timeOut,int exptectedNumberOfWindows) {
		WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(exptectedNumberOfWindows));
		} catch (Exception e) {
			System.out.println("Expected Number of Wiindows are not correct");
			return false;
		}
	    
	}
	
	public  WebElement waitForElementVisibleWithFluentWait(By locator,int timeOut,int pollingTIme) {
		
		Wait<WebDriver> wait =new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(10))
									.pollingEvery(Duration.ofSeconds(2))
										.ignoring(NoSuchElementException.class)
										.ignoring(StaleElementReferenceException.class)
										.withMessage("====Element is not found=====");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}
		
		public  WebElement waitForElementPresenceWithFluentWait(By locator,int timeOut,int pollingTIme) {
			
			Wait<WebDriver> wait =new FluentWait<WebDriver>(driver)
									.withTimeout(Duration.ofSeconds(10))
										.pollingEvery(Duration.ofSeconds(2))
											.ignoring(NoSuchElementException.class)
											.ignoring(StaleElementReferenceException.class)
											.withMessage("====Element is not found=====");
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			
		}

	


}
