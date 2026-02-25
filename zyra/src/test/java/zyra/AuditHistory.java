package zyra;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
public class AuditHistory extends BaseClass {
	
	  @Test (priority = 2)
	  public void auditHistory() throws IOException
	  {
		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'flex items-center') and contains(.,'Audit History')]"))).click();
		  wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
		  
		  ScreenShot ("AuditHistory") ;
	  }

	
		
	  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


