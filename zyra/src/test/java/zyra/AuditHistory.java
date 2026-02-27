package zyra;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
public class AuditHistory extends BaseClass {
	
	  @Test (priority = 2)
	  public void auditHistory() throws IOException, InterruptedException
	  {
		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'flex items-center') and contains(.,'Audit History')]"))).click();
//		  wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
		  
		   WebElement next = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Next']")));
		   

		   while (true)
		   {
		ScreenShot ("AuditHistory") ;
		WebElement button = driver.findElement(By.xpath("//button[text() = 'Next']"));
		if(!button.isEnabled())
		{
			break;
		} 
		button.click();
		
	// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = 'Next']")));
		  Thread.sleep(1000);
		   }
	  }

	
		
	  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


