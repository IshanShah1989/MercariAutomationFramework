package com.mma.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.mma.web.reports.ExtentManager;

public class BasicWebPage {

	public static WebDriver driver;
	public ExtentReports rep;
	public ExtentTest scenario;
	public Properties prop;
	public String name;

	public BasicWebPage() 
	{
		if(prop==null) 
		{			
			try 
			{
				prop= new Properties();
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\project.properties");
				prop.load(fs);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public WebDriver getDriver() 
	{
		String browserName = prop.getProperty("browser");
		if(browserName.equalsIgnoreCase("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+
					"\\drivers\\firefox\\geckodriver.exe");
			driver= new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("Chrome"))
		{
			ChromeOptions ops = new ChromeOptions();
			 //Change the path as per the local machine
			ops.addArguments("user-data-dir=C:/Users/Ishan/AppData/Local/Google/Chrome/User Data");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+
					"\\drivers\\chromedriver_win32\\chromedriver.exe");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			driver= new ChromeDriver(ops);
			
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
	}
	
	
	public void navigateUrl()
	{
		driver.navigate().to(prop.getProperty("url"));
	}
	
	public void click(String objectKey) 
	{
		getObject(objectKey).click();
	}
	
	public void type(String objectKey,String data)
	{
		getObject(objectKey).sendKeys(data);
	}

	public String getTextValue(String objectKey)
	{
		String text = getObject(objectKey).getText();
		return text;
	}	
	
	public String custom_xpath(String xpath_part1, String customValue, String xpath_part2)
	{
		String xpath = xpath_part1+customValue+xpath_part2;
		return xpath;
	}
	
	public void select(String objectKey,String data)
	{
		Select s= new Select(getObject(objectKey));
		s.selectByVisibleText(data);
	}
	
	public void clear(String objectKey)
	{
		getObject(objectKey).clear();
	}
	
	// central function to extract objects
	public WebElement getObject(String objectKey)
	{
		WebElement e = null;
		WebDriverWait wait  =  new WebDriverWait(driver, 10);
		
		try
		{
			if(objectKey.contains("xpath")) 
			{
				e = driver.findElement(By.xpath(objectKey.split(":")[1]));// present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(objectKey.split(":")[1])));
			}
			else if(objectKey.contains("id"))
			{
					e = driver.findElement(By.id(objectKey.split(":")[1]));// present
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(objectKey.split(":")[1])));
			}
			else if(objectKey.contains("name"))
			{
				e = driver.findElement(By.name(objectKey.split(":")[1]));// present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(objectKey.split(":")[1])));
			}
			else if(objectKey.contains("css"))
			{
				e = driver.findElement(By.cssSelector(objectKey.split(":")[1]));// present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(objectKey.split(":")[1])));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			reportFailure("Unable to extract Object "+objectKey);
		}
		return e;
	}
	// true - present
	// false - not present
	public boolean isElementPresent(String objectKey) 
	{
		List<WebElement> e=null;
		
		if(objectKey.endsWith("_xpath")) {
			e = driver.findElements(By.xpath(prop.getProperty(objectKey)));// present
		}else if(objectKey.endsWith("_id")) {
				e = driver.findElements(By.id(prop.getProperty(objectKey)));// present
		}
		else if(objectKey.endsWith("_name")) {
			e = driver.findElements(By.name(prop.getProperty(objectKey)));// present
		}
		else if(objectKey.endsWith("_css")) {
			e = driver.findElements(By.cssSelector(prop.getProperty(objectKey)));// present
		}
		if(e.size()==0)
			return false;
		else
			return true;
	}
	
	public void waitForPageToLoad()
	{
		wait(1);
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String state = (String)js.executeScript("return document.readyState");
		
		while(!state.equals("complete"))
		{
			wait(2);
			state = (String)js.executeScript("return document.readyState");
		}
	}
	
	public void wait(int timeToWaitInSec)
	{
		try {
			Thread.sleep(timeToWaitInSec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**********logging**************/
	
	public void infoLog(String msg)
	{
		scenario.log(Status.INFO, msg);
	}
	
	public void reportFailure(String errMsg)
	{
		// fail in extent reports
		scenario.log(Status.FAIL, errMsg);
		takeSceenShot();
		// take screenshot and put in repots
		// fail in cucumber as well
		//assertThat(false);
	}
	
	public void takeSceenShot()
	{
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+screenshotFile));
			//put screenshot file in reports
			scenario.log(Status.FAIL,"Screenshot-> "+ scenario.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}	

	public String getReportConfigPath()
	{
	 String reportConfigPath = prop.getProperty("reportConfigPath");
	 if(reportConfigPath!= null)
		 return reportConfigPath;
	 else
		 throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath"); 
	}
	
	/**************Reporting******************/
	
	public void quit() 
	{
/*		if(rep!=null)
			rep.flush();*/
		if(driver !=null)
			driver.quit();
	}
	
	public void initReports(String scenarioName) 
	{
		rep = ExtentManager.getInstance(System.getProperty("user.dir")+"\\reports\\");
		scenario = rep.createTest(scenarioName);
		scenario.log(Status.INFO, "Starting " +scenarioName);
	}

}
