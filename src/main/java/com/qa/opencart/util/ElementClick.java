package com.qa.opencart.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ElementClick {
	
	static WebDriver driver;

	public static void main(String[] args) {
		
		WebDriver driver= new ChromeDriver();
		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
	
		//Creat web Elemet +action)(click)
		//button,link,checkbox,radiobutton,image
		
		driver.findElement(By.linkText("Register")).click();
		driver.findElement(By.name("agree")).click();
	
	}
	

}
