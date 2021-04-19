# MercariAutomationFramework

This is an automation framework created using Page Object Model Pattern with Java, Selenium and TestNG.
The scenario 1 and scenario 2 are defiend in the MercariWebTests.java file. The test can be run as testNg Tests.

Since Login is prtected by Captcha, I have used the concept of profile to launch the chrome browser.
For Scenario 1, I have written the code for updating all fields in the 'My address' tab and it does not click on Update button  since I dont have the valid shipping address.

For Scenario 2, I have searched for 'MacBook' and if the title contains text 'MacBook', the test case will pass.
Even if the character is present in the lowercase instead of Uppercase, the test case will fail.
