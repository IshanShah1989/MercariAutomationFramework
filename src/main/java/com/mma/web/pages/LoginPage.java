package com.mma.web.pages;

import org.openqa.selenium.WebDriver;

import com.mma.web.util.BasicWebPage;

public class LoginPage extends BasicWebPage {

	public WebDriver driver;
	
	public String login_xpath = "xpath://button[contains(.,'Log in')]";
	public String emailTxt_xpath = "xpath://input[@type='email']";
	public String passwordTxt_xpath = "xpath://input[@type='password']";
	public String loginBtn_xpath = "xpath://button[@type='submit']"; 
	
	public void login(String userName, String password)
	{
		System.out.println("Login Successful");
		/*
		 * click(login_xpath); type(emailTxt_xpath,userName);
		 * type(passwordTxt_xpath,password); click(loginBtn_xpath);
		 */
	}
}
