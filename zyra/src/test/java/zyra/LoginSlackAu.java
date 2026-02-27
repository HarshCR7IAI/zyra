package zyra;

import java.time.Duration;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginSlackAu extends BaseClass {


    @Test (priority = 1)
    public void Login() throws InterruptedException {

        // 1. Setup your date and login variables (These stay the same for all channels)
        String timeLine = "Last 24 Hours";
        String userName = "admin@example.com";
        String passWord = "admin";
        String startMonth = "December 2025";
        String endMonth = "January 2026";
        String startDate = "15";
        String endDate = "11";

        // 2. Read the channels.txt file
        List<String> channelsToAudit;
        try {
            // Make sure channels.txt is in your main project folder
            channelsToAudit = Files.readAllLines(Paths.get("/home/harshit/git/repository/zyra/channels.txt"));
        } catch (IOException e) {
            System.out.println("Could not find or read channels.txt! " + e.getMessage());
            return; // Stop the test if the file is missing
        }

        // 3. Log into the application JUST ONCE
//        driver.get("https://auditor.iaisolution.com/login");
//
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(userName);
//        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(passWord);
//        driver.findElement(By.xpath("//button[text()='Log in']")).click();
//
//        try {
//            String errorMsg = driver.findElement(By.xpath("//p[text()='Invalid email or password']")).getText();
//            System.out.println("Login Failed : " + errorMsg);
//            return; // stop test if login fails
//        } catch (NoSuchElementException e) {
//            System.out.println("User logged in successfully");
//        }
//        
//        driver.findElement(By.xpath("//button[@aria-label='Integration']")).click();
//        driver.findElement(By.xpath("(//button[normalize-space()='START AUDIT'])[3]")).click();

        // 4. Start the loop to go through each channel from the text file
        for (String rawChannel : channelsToAudit) {
            String channelName = rawChannel.trim(); // Clean up spaces

            // Skip empty lines or commented lines
            if (channelName.isEmpty() || channelName.startsWith("//")) {
                continue;
            }

            System.out.println("=========================================");
            System.out.println("Starting audit for channel: " + channelName);
            System.out.println("=========================================");

            String attemptsText = driver.findElement(By.xpath("//span[contains(., 'Page') and contains(., 'of')]")).getText();

            String[] trimattempts = attemptsText.split(" ");
            String maxPage = trimattempts[3];

            int totalPages = Integer.parseInt(trimattempts[3].trim());



            

            // 5. Reset pagination variables for EACH channel
            boolean found = false;
            int maxPages = totalPages;
            int pageCount = 0;

            while (!found && pageCount < maxPages) {

                List<WebElement> channelList = driver.findElements(
                        By.xpath("//tbody[contains(@class,'divide-y')]/tr/td/div/span[text()='"
                                + channelName + "']/ancestor::tr"));

                if (!channelList.isEmpty()) {

                    WebElement channel = channelList.get(0);
                    System.out.println("Channel found: " + channel.getText());

                    channel.findElement(By.xpath(".//button[text()='Run Audit']")).click();
                    found = true;
                    break;

                } else {
                    try {
                        WebElement nextBtn = driver.findElement(
                                By.xpath("(//button[contains(@class,'border-gray-200') and contains(@class,'p-2')])[2]"));
                        nextBtn.click();
                        Thread.sleep(1000);
                        pageCount++;
                    } catch (NoSuchElementException e) {
                        System.out.println("No next page available");
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("Channel " + channelName + " not found in any page. Skipping to next channel...");
                continue; // Skip the rest of the code and move to the next channel in the file
            }

            driver.findElement(By.xpath("//span[text()='" + timeLine + "']")).click();

            if (timeLine.equals("Custom Range")) {
                driver.findElement(By.xpath("//button[@aria-haspopup='dialog']")).click();

                WebElement calDate = driver.findElement(
                        By.xpath("//span[contains(@class,'select-none') and @aria-live='polite']"));
                String calMonth = calDate.getText();

                int maxAttempts = 12;
                int attempts = 0;

                while (!calMonth.equalsIgnoreCase(startMonth) && attempts < maxAttempts) {
                    driver.findElement(By.xpath("//button[@aria-label='Go to the Previous Month']")).click();
                    calMonth = driver.findElement(
                            By.xpath("//span[contains(@class,'select-none') and @aria-live='polite']")).getText();
                    attempts++;
                }

                driver.findElement(By.xpath("//button[text()='" + startDate + "']")).click();

                attempts = 0;
                while (!calMonth.equalsIgnoreCase(endMonth) && attempts < maxAttempts) {
                    driver.findElement(By.xpath("//button[@aria-label='Go to the Next Month']")).click();
                    calMonth = driver.findElement(
                            By.xpath("//span[contains(@class,'select-none') and @aria-live='polite']")).getText();
                    attempts++;
                }

                driver.findElement(By.xpath("//button[text()='" + endDate + "']")).click();
                driver.findElement(By.xpath("//button[contains(@class,'transition-all active:scale-95')]")).click();

            } else {
                driver.findElement(By.xpath("//button[contains(@class,'transition-all active:scale-95')]")).click();
            }

            try {
                wait.until(ExpectedConditions.textToBePresentInElementLocated(
                        By.xpath("//div[contains(@class,'Toastify__toast')]"), "Audit completed."));
                WebElement toast = driver.findElement(
                        By.xpath("//div[contains(@class,'Toastify__toast') and contains(.,'Audit completed')]"));
                String Success = toast.getText();
                System.out.println(channelName + " : " + Success);
            } catch (Exception e) {
                System.out.println(channelName + " : Audit not completed or toast not found.");
            }

            Thread.sleep(5000); // Brief pause before the loop restarts for the next channel
            
            // 🛑 IMPORTANT: UI STATE RESET
            // Depending on how your website works, after the toast disappears, you might need to 
            // close a popup or navigate back to the dashboard so the next channel can start cleanly.
            // If the script fails on the SECOND channel, try uncommenting the line below:
            // driver.navigate().refresh(); 

        } // End of the For Loop
        
        System.out.println("All channels processed successfully.");
    }
}