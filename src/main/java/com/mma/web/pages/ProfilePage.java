package com.mma.web.pages;

import com.mma.web.util.BasicWebPage;

public class ProfilePage extends BasicWebPage {
	
	public String navLink = "xpath://a[contains(.,'";
	public String commonxpath = "')]";
	
	/********************************** Address Details ***************************************/
	public String firstNameText = "xpath://input[@name='first_name']";
	public String lastNameText = "xpath://input[@name='family_name']";
	public String streetAddressText = "xpath://input[@name='address1']";
	public String zipCodeText = "xpath://input[@name='zip_code1']";
	public String cityText = "xpath://input[@name='city']";
	public String stateDrpdwn = "xpath://select[@name='prefecture']";
	public String updateBtn = "xpath=//button[contains(.,'Update')]";
	
	public void clickMenuLink(String linkText)
	{
		String cust_xpath = custom_xpath(navLink, linkText, commonxpath);
		click(cust_xpath);
	}
	
	public void addAddressDetails(String firstName, String lastName, String streetAddress, String zipCode,
			String city, String state)
	{
		type(firstNameText,firstName);
		type(lastNameText, lastName);
		type(streetAddressText,streetAddress);
		type(zipCodeText,zipCode);
		type(cityText,city);
		select(stateDrpdwn, state);
		//click(updateBtn);
	}

}
