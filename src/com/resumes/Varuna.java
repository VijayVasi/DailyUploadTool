package com.resumes;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


public class Varuna{

		public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException
		
		{
			
			System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
			
			WebDriver driver = new ChromeDriver();
			//driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			String parent = driver.getWindowHandle();
			driver.get("https://www.naukri.com");
			
			Set<String> AllWH = driver.getWindowHandles();
			AllWH.remove(parent);
			
			for(String wh:AllWH){
				
				driver.switchTo().window(wh);
				driver.close();
			}
			driver.switchTo().window(parent);
			
			driver.findElement(By.xpath("//div[text() = 'Login']")).click();
			
			FileInputStream fis = new FileInputStream("./Data/DataSheet.xlsx");
			Workbook wb = WorkbookFactory.create(fis);
			org.apache.poi.ss.usermodel.Cell UsernameCell = wb.getSheet("Sheet1").getRow(2).getCell(0);
			String Username = UsernameCell.toString();
			org.apache.poi.ss.usermodel.Cell PasswordCell = wb.getSheet("Sheet1").getRow(2).getCell(1);
			String Password = PasswordCell.toString();
			
			driver.findElement(By.xpath("//input[@id = 'eLogin']")).sendKeys(Username);
			driver.findElement(By.xpath("//input[@id = 'pLogin']")).sendKeys(Password);
			driver.findElement(By.xpath("//button[text() = 'Login']")).click();
			driver.findElement(By.xpath("//b[text() = 'View and Update Profile']")).click();
			driver.findElement(By.xpath("//a[text() = 'Upload New Resume']")).click();
			driver.findElement(By.xpath("//input[@id = 'attachCV']")).sendKeys("D:\\MyWorkspace\\DailyUploadTool\\Data\\Varuna_Resume.docx");
			driver.findElement(By.xpath("//b[text() = 'Save']")).click();
			
			WebElement MyNaukri = driver.findElement(By.xpath("//div[contains(text(),'My Naukri')]"));
			
			Actions actions = new Actions(driver);
			actions.moveToElement(MyNaukri).perform();
			driver.findElement(By.xpath("//a[text() = 'Log Out']")).click();
			driver.quit();
		}
}
