package weblib;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait
{

	private static WebDriverWait wait;
	private static boolean bStatus;
	private static Logger logger=Logger.getLogger(Wait.class.getName());

	public static boolean waitForElementPresence(WebDriver wDriver,By objLocator,long iTimeout)
	{
		try{
			wait = new WebDriverWait(wDriver,iTimeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(objLocator));
			logger.info("element "+objLocator+" is present after waiting.");
			return true;
		}
		catch(Exception e)
		{
			Messages.errorMsg = e.getMessage();
			logger.warning("element "+objLocator+" is not present after waiting "+iTimeout+".");
			return false;
		}
	}

	public static boolean waitForElementVisibility(WebDriver wDriver,By objLocator,long iTimeout)
	{
		try{
			wait = new WebDriverWait(wDriver,iTimeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
			logger.info("element "+objLocator+" is visible after waiting.");
			return true;
		}
		catch(Exception e)
		{	
			Messages.errorMsg = e.getMessage();
			logger.warning("element "+objLocator+" is not present after waiting "+iTimeout+" secs.");
			return false;
		}
	}

	public static boolean waitForTextVisible(WebDriver wDriver,String sText,long iTimeout)
	{
		long iTimeoutinMillis = (iTimeout*10);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			bStatus = Verify.verifyTextVisible(wDriver, sText);
			if(bStatus)
			{
				logger.info("Text '"+sText+"' is present after waiting .");
				return true;
			}
		}	
		Messages.errorMsg="Text '"+sText+"' not found in the current page after waiting "+iTimeout+"secs";
		logger.warning(Messages.errorMsg);
		return false; 
	}
	
	public static boolean waitForTextVisible(WebDriver wDriver,By objLocator,String sText,long iTimeout)
	{
		long iTimeoutinMillis = (iTimeout*1000);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			bStatus = Verify.verifyTextVisible(wDriver,objLocator ,sText);
			if(bStatus)
			{
				logger.info("Text '"+sText+"' is present after waiting .");
				return true;
			}
		}
		Messages.errorMsg="Text '"+sText+"' not found in the current page after waiting "+iTimeout+"secs";
		logger.warning(Messages.errorMsg);
		return false; 
	}

	public static boolean waitForAlert(WebDriver wDriver,long iTimeout) 
	{
		long iTimeoutinMillis = (iTimeout*1000);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			try {
				wDriver.switchTo().alert();
				logger.info("Alert present");
				return true;
			} catch (NoAlertPresentException e) {
				Messages.errorMsg = e.getMessage();
				continue;
			}
		}
		logger.warning(Messages.errorMsg+" after waiting "+iTimeoutinMillis+" MilliSecs");
		return false;
	}

	/*	public static boolean waitForAjax(WebDriver wDriver,long iTimeout)
	{
		long lFinalTime = System.currentTimeMillis() + iTimeout;
		JavascriptExecutor jsDriver = (JavascriptExecutor)wDriver;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
			Long n = (Long)numberOfAjaxConnections;
			if (n.longValue() == 0L)
				return true;
		}
		Messages.errorMsg = "couldn't wait for Ajax to load completely";
		return false;

	}*/

	public static boolean waitForJQueryProcessing(WebDriver wDriver, long iTimeout)
	{
		boolean jQcondition = false;
		try{	
			wait = new WebDriverWait(wDriver,iTimeout);
			wait.until(new ExpectedCondition<Boolean>() 
					{
				@Override
				public Boolean apply(WebDriver wDriver) 
				{
					return (Boolean) ((JavascriptExecutor) wDriver).executeScript("return jQuery.active == 0");
				}

					});
			jQcondition = (Boolean) ((JavascriptExecutor) wDriver).executeScript("return window.jQuery != undefined && jQuery.active === 0");
			if(!jQcondition)
			{
				Messages.errorMsg = "couldn't wait for Ajax to load completely after waiting "+iTimeout+"Secs";
				return false;
			}
			return true;
		} 
		catch (Exception e) 
		{
			Messages.errorMsg = e.getMessage();
			return false;
		}
	}
	
	public static boolean waitForEnable(WebDriver wDriver,By objLocator,long iTimeout)
	{
		long iTimeoutinMillis = (iTimeout*1000);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			bStatus = Verify.verifyEnable(wDriver, objLocator);
			if(bStatus)
			{
				return true;
			}
		}
		Messages.errorMsg = "TimedOut due to element is not enabled after "+iTimeout +"secs";
		return false;
	}

	public static boolean waitForWindow(WebDriver wDriver,long iTimeout)
	{
		String sMainHandler = wDriver.getWindowHandle();
		System.out.println(sMainHandler);
		long iTimeoutinMillis = (iTimeout*1000);
		long lFinalTime = System.currentTimeMillis() + iTimeoutinMillis;
		while(System.currentTimeMillis() < lFinalTime) 
		{
			Set<String> handlers = wDriver.getWindowHandles();
			System.out.println("size of windows "+handlers);
			Iterator<String> winIterator = handlers.iterator();
			while (winIterator.hasNext())
			{
				String BancsChildWin = winIterator.next();
				System.out.println(BancsChildWin);
				if(!sMainHandler.equalsIgnoreCase(BancsChildWin))
				{
					return true;
				}
			}
		}
		Messages.errorMsg = "TimedOut due to new window is not availble after "+iTimeout +"secs";
		return false;
	}
}

