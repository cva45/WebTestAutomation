package weblib;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import applib.GloabalVars;

public class WebLib {
   
	
	public WebDriver lunchBrowser(GloabalVars gv) {
		try {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\saribina\\Desktop\\NGA Project\\PioAdmin\\drivers\\chromedriver.exe");
			gv.wdriver = new ChromeDriver();
		} catch (Exception e) {
			System.out.println("Exception in lunchBrowser:"+e.getMessage());
		}
		return gv.wdriver;
	}
	
	public void inputText(String loc,String text,WebDriver driver) {
		try {
			driver.findElement(By.xpath(loc)).sendKeys(text);
		} catch (Exception e) {
			System.out.println("Exception in inpuText "+e.getMessage());
		}
	}
	
	public void click(String loc,WebDriver driver) {
		try {
			driver.findElement(By.xpath(loc)).submit();
		} catch (Exception e) {
			System.out.println("Exception in click "+e.getMessage());
		}
	}
	
	public boolean elementPresent(String loc, WebDriver driver)
	{
		boolean status = false;
		try
		{
			int size = driver.findElements(By.xpath(loc)).size();
			if(size==0)
				status = false;
				else
				status = true;
		}
		catch(Exception e)
		{
			System.out.println("Exception in ElementPresent");
		}
		return status;
	}
}
