package com.mma.web.pages;

import org.openqa.selenium.By;

import com.mma.web.util.BasicWebPage;

public class SearchResultsPage extends BasicWebPage {
	
	
	public String searchedItem = "xpath://div[@class='Flex__Box-ych44r-1 Grid2__Col-mpt2p4-0 jhfizC'][";
	public String commonxpath = "]";
	public String itemTitle = "xpath://p[@class[contains(.,'ItemInfo__Title')]]";
	
	public String clickSearchedElement(String number)
	{
		String title;
		String cst_xpath = custom_xpath(searchedItem,number,commonxpath);
		click(cst_xpath);
		title = getTextValue(itemTitle);
		return title;
	}

}
