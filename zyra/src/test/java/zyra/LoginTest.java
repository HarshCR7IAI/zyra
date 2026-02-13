package zyra;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class LoginTest {
	
	@Test
	public void  Login()
	{
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://auditor.iaisolution.com/login");
		driver.manage().window().maximize();
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("admin@example.com");
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("admin");
		driver.findElement(By.xpath("//button[text()='Log in']")).click();
		
		String channelName = "#ai-auditor";
		String timeLine = "Custom Range";
		
		try {
	    String errorMsg = 	driver.findElement(By.xpath("//p[text()='Invalid email or password']")).getText();
	    System.out.println("Login Failed : " + errorMsg);
		}
		catch (NoSuchElementException e){
			System.out.println("User logged in successfully");
		}
		
//		driver.findElement(By.xpath("//input[@type='password']")).clear();
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("admin");
//		driver.findElement(By.xpath("//button[text()='Log in']")).click();

		
		driver.findElement(By.xpath("//button[@aria-label='Integration']")).click();
//		driver.findElement(By.xpath("//div//div//div//div//div[3]//div[2]//button[1]")).click();
		
		// Print ALL "START AUDIT" buttons and their parent text
		List<WebElement> auditButtons = driver.findElements(By.xpath("//button[normalize-space()='START AUDIT']"));
		System.out.println("Found " + auditButtons.size() + " START AUDIT buttons:");

		for (int i = 0; i < auditButtons.size(); i++) {
		    WebElement button = auditButtons.get(i);
		    
		    // Get the parent card
		    WebElement parentCard = button.findElement(By.xpath("./ancestor::div[contains(@class,'bg-background') or contains(@class,'card')]"));
		    
		    // Print what's in this card
		    String cardText = parentCard.getText();
		    System.out.println("\n=== Button " + (i+1) + " ===");
		    System.out.println(cardText.substring(0, Math.min(100, cardText.length())) + "...");
		    System.out.println("================\n");
		}

		// Now try to find the specific element
		try {
		    WebElement slackButton = driver.findElement(By.xpath("//button[normalize-space()='START AUDIT' and ancestor::*[contains(.,'Integrating Slack')]]"));
		    WebElement parentCard = slackButton.findElement(By.xpath("./ancestor::div[contains(@class,'bg-background') or contains(@class,'card')]"));
		    System.out.println("SELECTED CARD TEXT: " + parentCard.getText());
		} catch (Exception e) {
		    System.out.println("Could not find element: " + e.getMessage());
		}
		
		
//		driver.findElement(By.xpath("//button[normalize-space()='START AUDIT' and ancestor::*[contains(.,'Integrating Slack for real-time team communication and collaboration.')]]")).click();

		driver.findElement(By.xpath("(//button[normalize-space()='START AUDIT'])[3]")).click();		
		
		try {
		WebElement channel = driver.findElement(By.xpath("//tbody[contains(@class,'divide-y')]/tr/td/div/span[text()='"+channelName+"']/ancestor::tr"));
		String chaname = channel.findElement(By.xpath("//td/div/span[text()='" + channelName + "']")).getText();
		System.out.println("Mani's choice is " + chaname + "!");
		driver.findElement(By.xpath("//button[text()='Run Audit']")).click();
		
		driver.findElement(By.xpath("//span[text()='"+ timeLine + "']")).click();
		
		if (timeLine== "Custom Range")
		{
//			driver.findElement(By.xpath("//[text()='Select date range']")).click();
			driver.findElement(By.xpath("//button[@aria-haspopup='dialog']")).click();
			
	   }
		
//		driver.findElement(By.xpath("//button[contains(@class,'transition-all active:scale-95')]")).click();
//		driver.findElement(By.xpath("//button[text()='Run Audit']")).click();
		
		
		
//		if (driver.findElement(By.xpath("//span[text()='"+ timeLine + "']")).getText()== timeLine)
//		{
//			driver.findElement(By.xpath("//span[contains(@class,'w-5 h-5 mr-3 rounded-full']")).click();
//		}
		}
		catch (Exception e) {
			System.out.println("No such Channel exists");
		}
		
		
		
		
		
		}
	}


