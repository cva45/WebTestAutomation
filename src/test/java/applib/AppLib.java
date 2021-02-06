package applib;

import java.net.HttpURLConnection;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import weblib.WebLib;

public class AppLib {
public void login(String userName, String pwd,WebDriver driver)
{
	WebLib wl =new WebLib();
	try
	{		
		driver.get("https://test.piotest.com/");
		driver.manage().window().maximize();
		wl.inputText("//input[@name='username']",userName,driver);
		wl.inputText("//input[@name='passwd']",pwd,driver);
		wl.click("//input[@name='passwd']",driver);
		boolean eleStatus = wl.elementPresent("//a[contains(.,'Logout')]",driver);
		if(eleStatus)
		{
			System.out.println("Login Successfull");
			 Reporter.log("Login Successfull");
		}
		else
		{
			System.out.println("Login Failure");
		}
		
	}
	catch(Exception e)
	{
		
	}
}
}
