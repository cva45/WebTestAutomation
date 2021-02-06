package weblib;

import java.util.Set;
import java.util.logging.Logger;

import org.apache.bcel.generic.RETURN;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import applib.GloabalVars;



public class Browser 
{
	static WebDriverWait wait;
	static WebDriver driver = null;
	private static Logger logger=Logger.getLogger(Browser.class.getName());

	public static WebDriver openBrowser(String sURL)
	{
		logger.info("The application has been invoked successfully in Mozilla Firefox with URL:"+sURL);
		return driver = openFirefoxBrowser(driver,sURL);
	}

	public static WebDriver openBrowser(String sBrowserName,String sURL,String sPathOfDriver){
		driver = null;
		if(sBrowserName.equalsIgnoreCase("ie")){
			logger.info("The application has been invoked successfully in Internet Explorer with URL:"+sURL);
			return driver = openIEBrowser(driver, sURL, sPathOfDriver);
		}else if(sBrowserName.equalsIgnoreCase("chrome")){
			logger.info("The application has been invoked successfully in Google Chrome with URL:"+sURL);
			return driver = openChromeBrowser(driver, sURL, sPathOfDriver);
		}else if(sBrowserName.equalsIgnoreCase("firefox")){
			logger.info("The application has been invoked successfully in firefox with URL:"+sURL);
			return driver = openFirefoxBrowser(driver, sURL);
		}else {
			Messages.errorMsg="No browser drivers found";
			logger.warning("The application could not be invoked due to "+Messages.errorMsg);
			return driver;
		}	
	}

	/*****************************************************************************
	Function Name 					: openFirefoxBrowser
	Description						: Opens a new Firefox browser instance to given URL.  
	Parameters						: driver,url
	Usage							: WebDriver = openFirefoxBrowser(driver,url)
	Created By						: 
	Created On						: 
	 ******************************************************************************
	Revision History				:
	Modified By						:
	Modified On						:
	Remarks                         :
	 ******************************************************************************/

	private static WebDriver openFirefoxBrowser(WebDriver wDriver, String surl)
	{	
		if(wDriver==null)
		{
			try{
				wDriver = new FirefoxDriver();
				wDriver.get(surl);
				wDriver.manage().window().maximize();	
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
			}
		}
		return wDriver;
	}


	/*****************************************************************************
	Function Name 					: openChromeBrowser
	Description						: Opens a new chrome browser instance to given URL.  
	Parameters						: browser,url,PathOfDriver
	Usage							: WebDriver = openChromeBrowser(driver,url,pathofdriver)
	Created By						: 
	Created On						: 
	 ******************************************************************************
	Revision History				:
	Modified By						:
	Modified On						:
	Remarks                         :
	 ******************************************************************************/

	private static WebDriver openChromeBrowser(WebDriver wDriver,String sUrl,String sPathOfDriver )
	{

		if(wDriver==null)
		{
			try{
				System.setProperty("webdriver.chrome.driver",sPathOfDriver);
				wDriver = new ChromeDriver();	
				wDriver.get(sUrl);
				wDriver.manage().window().maximize();	
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
			}
		}
		return wDriver;

	}

	/*****************************************************************************
	Function Name 					: openIEBrowser
	Description						: Opens a new IE browser instance to given URL.  
	Parameters						: driver,url,PathOfDriver
	Usage							: WebDriver=openIEBrowser(driver,url,pathofdriver)
	Created By						: 
	Created On						: 
	 ******************************************************************************
	Revision History				:
	Modified By						:
	Modified On						:
	Remarks                         :
	 ******************************************************************************/

	private static WebDriver openIEBrowser(WebDriver wDriver, String sUrl,String sPathOfdriver)
	{
		try{
			System.setProperty("webdriver.ie.driver",sPathOfdriver);
			DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
			//dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			dc.setCapability("nativeEvents", false);
			wDriver = new InternetExplorerDriver(dc);
			wDriver.manage().deleteAllCookies();
			wDriver.get(sUrl);
			wDriver.manage().window().maximize();
			//wDriver.
		}
		catch(Exception e)
		{
			Messages.errorMsg = e.getMessage();
		}
		return wDriver;
	}


/*******************************************************************************
	Function Name 					: closeAllBrowsers
	Description						: close all the browsers 
	Parameters						: 
	Usage							: bStatus = closeAllBrowsers()
	Created By						: 
	Created On						: 
 ******************************************************************************
	Revision History				:
	Modified By						:
	Modified On						:
	Remarks                         :
 ******************************************************************************/
public static boolean closeAllBrowsers(WebDriver wDriver)
{

	try{
		if(wDriver != null)
		{
			GloabalVars.wdriver.quit();	
			GloabalVars.wdriver = null;
			//Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			//Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			logger.info("Closed all open browsers and killing the driver instances successfully");
			return true;
		}
		logger.warning("The browsers could not be closed");
		return false;

	}
	catch(Exception e){
		Messages.errorMsg = e.getMessage();
		logger.warning("The browsers could not be closed because "+Messages.errorMsg);
		return false;
	}
}

public static boolean closeCurrentBrowser(WebDriver wDriver)
{
	try{
		if(wDriver != null)
		{
			String sFocusedWindow = wDriver.getWindowHandle();
			Set<String> windows = wDriver.getWindowHandles();
			int iSize = windows.size();
			if(iSize > 1)
			{
				wDriver.close();
				for (String handle : windows) 
				{
					if(!sFocusedWindow.equalsIgnoreCase(handle))
					{
						wDriver.switchTo().window(handle);
						
					}
				}
			}	
			wDriver.close();
			logger.info("The current browser has been closed successfully");
			//Global.wDriver.quit();
			//Global.wDriver = null;
			//Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			//Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			return true;
		}
		logger.warning("The current browser could not be closed");
		return false;

	}
	catch(Exception e){
		Messages.errorMsg = e.getMessage();
		logger.warning("The current browser could not be closed due to "+Messages.errorMsg);
		return false;
	}
}

public static void navigateBack(WebDriver wDriver)
{
	wDriver.navigate().back();
	logger.info("Successfully navigated back to previous web page");
}

public static void navigateForward(WebDriver wDriver)
{
	wDriver.navigate().forward();
	logger.info("Successfully navigated forward to next web page");
}

public static void reloadPage(WebDriver wDriver)
{
	wDriver.navigate().refresh();
	logger.info("The web page has been refreshed");
}

public static void deleteAllCookies(WebDriver wDriver)
{
	wDriver.manage().deleteAllCookies();
	logger.info("Successfully deleted all the browser cookies");
}

public static void deleteCookie(WebDriver wDriver,String sCookieName)
{
	wDriver.manage().deleteCookieNamed(sCookieName);
	logger.info("Successfully deleted the browser cookie"+sCookieName);
}
}

