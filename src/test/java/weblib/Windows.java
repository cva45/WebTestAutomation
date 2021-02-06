package weblib;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class Windows 
{
	private static boolean bStatus;
	private static Logger logger=Logger.getLogger(Windows.class.getName());

	public static String getWindowTitle(WebDriver wDriver)
	{
		return wDriver.getTitle();
	}

	public static String[] getWindowTitles(WebDriver wDriver)
	{
		Set<String> windows = wDriver.getWindowHandles();
		int iSize = windows.size();
		String[] arrWindows = new String[iSize];
		int iInc = 0;
		for (String handle : windows) 
		{
			wDriver.switchTo().window(handle);
			arrWindows[iInc] = wDriver.getTitle();
			iInc++;
		}
		return arrWindows;
	}

	public static boolean switchToWindowByIndex(WebDriver wDriver,int iWindowIndex)
	{
		Set<String> windows=wDriver.getWindowHandles();
		Iterator itr=windows.iterator();
		int iSize=windows.size();
		if((iSize > 1))
		{
			if(iWindowIndex < iSize)
			{
				String[] arrWin =new String[iSize];
				int inc=0;
				while(itr.hasNext())
				{
					arrWin[inc]=itr.next().toString();
					inc++;
				}
				wDriver.switchTo().window(arrWin[iWindowIndex]);
				return true;
			}
			Messages.errorMsg = iWindowIndex+" is greater than windows count "+iSize;
			return false;
		}
		Messages.errorMsg = "only one window is available";
		return false;
	}

	public static boolean switchToWindowByTitle(WebDriver wDriver,String sWindowName)
	{
		String sFocusedWindow = wDriver.getWindowHandle();
		Set<String> windows = wDriver.getWindowHandles();
		int iSize = windows.size();
		if(iSize >1)
		{
			for (String handle : windows) 
			{
				wDriver.switchTo().window(handle);
				if(wDriver.getTitle().equalsIgnoreCase(sWindowName))
				{
					return true;
				}
			}
			wDriver.switchTo().window(sFocusedWindow);
			Messages.errorMsg = sWindowName+" not found";
			return false;
		}
		Messages.errorMsg = "only one window is available";
		return false;
	}

	public static boolean switchToFrameByFrameElement(WebDriver wDriver,By objLocator)
	{
		bStatus = Verify.verifyElementVisible(wDriver,objLocator);
		if(bStatus)
		{	
			wDriver.switchTo().frame(wDriver.findElement(objLocator));
			return true;
		}
		return false;
	}

	public static boolean switchToFrameByName(WebDriver wDriver,String sName)
	{
		By objLocator = By.name(sName);
		bStatus = Verify.verifyElementVisible(wDriver,objLocator);
		if(bStatus)
		{	
			try
			{
				wDriver.switchTo().frame(sName);
				return true;
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
				return false;
			}
		}
		return false;
	}
	
	public static boolean switchToFrameById(WebDriver wDriver,String sId)
	{
		By objLocator = By.id(sId);
		bStatus = Verify.verifyElementVisible(wDriver,objLocator);
		if(bStatus)
		{	
			try
			{
				wDriver.switchTo().frame(sId);
				return true;
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
				return false;
			}
		}
		return false;
	}

}
