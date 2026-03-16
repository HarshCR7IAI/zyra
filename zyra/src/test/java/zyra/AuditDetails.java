package zyra;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import zyra.BaseClass;

public class AuditDetails extends BaseClass{

    @Test
    public void viewDetails() throws IOException, InterruptedException
    {
        driver.findElement(By.xpath("//button[contains(@class,'flex items-center') and contains(.,'Audit History')]")).click();
        String name = "#ai-auditor";
        boolean found = false ;
                      

        for (int i=0; i<5 ; i++)
            
        {          List<WebElement>  channelRow = driver.findElements(By.xpath("//span[contains(.,'" + name + "')]/parent::div"));

           if (channelRow.size() !=0 )
           {
            WebElement channelTC = channelRow.get(0);
            channelTC.findElement(By.xpath(".//button[contains(text(),'View Details')]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Detailed Audit Report')]")));
             ScreenShot("Audit Detail");
            
            found = true;
            break;
           }
           else
           {
            driver.findElement(By.xpath("//button[contains(text(),'Next')]")).click();
           }
        }

        if (found !=true)
        {
            System.out.println("Channel not found in first 5 pages");
        }
        
        
     // //span[contains(.,'project-insign-agent')]/parent::div



    }


        
}
