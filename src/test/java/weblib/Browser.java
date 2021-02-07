package weblib;

import java.io.File;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.bcel.generic.RETURN;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import applib.GlobalVars;
import utillib.ReportLib;
import applib.CoreLib;

public class Browser 
{
	static WebDriverWait wait;
	static WebDriver driver = null;
	private static Logger logger=Logger.getLogger(Browser.class.getName());

	public static WebDriver launchBrowser(String browserType) throws Exception {
		// ReportLib.reportMsgInfo("Browser Type ::" + browserType + "'");
		try {
			if (GlobalVars.wdriver == null) {
				switch (browserType) {
				case "FF":
					openFirefoxBrowser();
					break;
				case "GC": {
					openChromeBrowser();
					break;
				}
				case "IE": {
					openIEBrowser();
					break;
				}
				case "Html Unit": {
					GlobalVars.wdriver = new HtmlUnitDriver();
					break;
				}
				case "SF": {
					GlobalVars.wdriver = new SafariDriver();
					break;
				}
				}
//				ReportLib.reportMsgInfo("'" + browserType + "' Browser Lanched Successfully ");
				GlobalVars.wdriver.manage().window().maximize();
				return GlobalVars.wdriver;
			} else
				return GlobalVars.wdriver;
		} catch (Exception e) {
//			ReportLib.LOGGER.error("Error occured while lanching the Browser ::'" + browserType + "'" + e.getMessage());
			return null;
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

	private static void openFirefoxBrowser()
	{	
		System.setProperty("webdriver.firefox.profile", "MySeleniumProfile");
		FirefoxProfile profile = new FirefoxProfile();
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		profile.setPreference("intl.accept_languages", "en-gb");
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir",GlobalVars.downloadPath);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/msword, application/csv, application/ris, text/csv, "
						+ "image/png, application/pdf, text/html, text/plain, application/zip, application/x-zip, "
						+ "application/x-zip-compressed, application/download, application/octet-stream");
		dc.setCapability(FirefoxDriver.PROFILE, profile);

		GlobalVars.wdriver = new FirefoxDriver(dc);
		// ReportLib.reportMsgInfo("Browser Launched Successfully - Browser Type "
		// + browserType);
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

	private static void openChromeBrowser()
	{


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

	private static void openIEBrowser()
	{

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
public static void closeBrowser() throws Exception {
	try {
		Thread.sleep(500);
		CoreLib.takeScreenShot(GlobalVars.ResultsDir, "BeforeBrowserClose");
		GlobalVars.wdriver.close();
		Thread.sleep(1000);
		ReportLib.reportMsgInfo("Closed Browser");
		GlobalVars.wdriver.quit();
		ReportLib.reportMsgInfo("Browser quit successful");
	} catch (Exception e) {
		try {
			ReportLib.reportMsgInfo("Exception when trying to close browser. So trying to quit browser. \n Exception is : "
					+ e.getMessage(), "");
			GlobalVars.wdriver.quit();
			ReportLib.reportMsgInfo("Browser quit successful");
		} catch (Exception e1) {
			ReportLib
					.reportMsgInfo("Exception when trying to quit browser. So trying to Kill browser process. \n Exception is : "
							+ e1.getMessage());
			CoreLib.killBrowserProcess(GlobalVars.hMapVariableData.get("Browsertype"));
			Asserts.assertFail("BeforeKillBrowser");
		}
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

