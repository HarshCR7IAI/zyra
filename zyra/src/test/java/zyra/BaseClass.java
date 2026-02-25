package zyra;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass {
	
	public static WebDriver driver;
    public static WebDriverWait wait;
    static String userName = "admin@example.com" ;
    static String passWord = "admin" ;
    
    public static void ScreenShot (String fileName) throws IOException
    {
    TakesScreenshot ts = (TakesScreenshot) driver ;
    File source = ts.getScreenshotAs(OutputType.FILE);
    File destination = new File("/home/harshit/git/repository/zyra/ScreenShot/" + fileName + ".png");
    FileHandler.copy(source, destination);
    }
    
    
	@BeforeMethod
	public void setup() throws InterruptedException, IOException
	{
		
		// Set- Up
		
		    driver = new ChromeDriver();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	        driver.manage().window().maximize();
	        
	        
	      
	     // Log - In   
	        
	        driver.get("https://auditor.iaisolution.com/login");
	        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(userName);
	        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(passWord);
	        driver.findElement(By.xpath("//button[text()='Log in']")).click();

	        try {
	            String errorMsg = driver.findElement(By.xpath("//p[text()='Invalid email or password']")).getText();
	            System.out.println("Login Failed : " + errorMsg);
	        } catch (NoSuchElementException e) {
	            System.out.println("User logged in successfully");
	        }
	        
	        // Navigating - To - Slack
	        
	        driver.findElement(By.xpath("//button[@aria-label='Integration']")).click();
	        driver.findElement(By.xpath("(//button[normalize-space()='START AUDIT'])[3]")).click();
	    
	}
	
	
//            @AfterMethod 
//            public void closeBrowser() throws InterruptedException
//            {
//            	if( driver != null)
//            	{
//            		Thread.sleep(5000);
//            		driver.quit();
//            	}
//            }
	
	

}
