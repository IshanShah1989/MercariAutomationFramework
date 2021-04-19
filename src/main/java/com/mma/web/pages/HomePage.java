package com.mma.web.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.mma.web.util.BasicWebPage;

public class HomePage extends BasicWebPage {
	
	public String searchTxt = "xpath://input[@type='search']";
	public String searchBtn = "xpath://button[@data-testid='SearchIcon']";
	public String profileIcon = "xpath://div[@data-testid='UserIcon']";
	public String viewProfile = "xpath://p[contains(.,'View profile')]";
	
	public void search(String text)
	{
		type(searchTxt,text);
		click(searchBtn);
	}
	
	public ProfilePage clickProfile()
	{
		click(profileIcon);
		click(viewProfile);
		return new ProfilePage();
	}
}
