package com.mma.web.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mma.web.pages.HomePage;
import com.mma.web.pages.LoginPage;
import com.mma.web.pages.ProfilePage;
import com.mma.web.pages.SearchResultsPage;
import com.mma.web.util.BasicWebPage;

public class MercariWebTests {
	
	WebDriver wd;
	BasicWebPage bwp = new BasicWebPage();
	LoginPage lp;
	HomePage hp = new HomePage();
	SearchResultsPage sp = new SearchResultsPage();
	ProfilePage pr;
	
	@BeforeMethod
	public void beforeClass(Method method)
	{
		wd = bwp.getDriver();
		lp = new LoginPage();
		bwp.navigateUrl();
	}
	
	@Test
	public void testScenario1()
	{
		pr = hp.clickProfile();
		pr.clickMenuLink("My address");
		pr.addAddressDetails("Arania", "Jain", "124 Vijay Nagar", "61259", "Illinois", "Illinois");
	}
	
	@Test
	public void testScenario2()
	{
		String searchText = "MacBook";
		String title;
		hp.search(searchText);
		title = sp.clickSearchedElement("3");
		System.out.println("Title is " + title);
		Assert.assertTrue(title.contains(searchText),"Either Text doesnot Match or Text is not present in the title");
	}
	
	@AfterMethod
	public void afterMethod()
	{
		wd.close();
	}

}
