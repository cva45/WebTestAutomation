package weblib;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import applib.GlobalVars;


public class Windows 
{
	private static boolean bStatus;
	private static Logger logger=Logger.getLogger(Windows.class.getName());

	public static String getWindowTitle()
	{
		return GlobalVars.wdriver.getTitle();
	}

	public static String[] getWindowTitles()
	{
		Set<String> windows = GlobalVars.wdriver.getWindowHandles();
		int iSize = windows.size();
		String[] arrWindows = new String[iSize];
		int iInc = 0;
		for (String handle : windows) 
		{
			GlobalVars.wdriver.switchTo().window(handle);
			arrWindows[iInc] = GlobalVars.wdriver.getTitle();
			iInc++;
		}
		return arrWindows;
	}

	public static boolean switchToWindowByIndex(int iWindowIndex)
	{
		Set<String> windows=GlobalVars.wdriver.getWindowHandles();
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
				GlobalVars.wdriver.switchTo().window(arrWin[iWindowIndex]);
				return true;
			}
			Messages.errorMsg = iWindowIndex+" is greater than windows count "+iSize;
			return false;
		}
		Messages.errorMsg = "only one window is available";
		return false;
	}

	public static boolean switchToWindowByTitle(String sWindowName)
	{
		String sFocusedWindow = GlobalVars.wdriver.getWindowHandle();
		Set<String> windows = GlobalVars.wdriver.getWindowHandles();
		int iSize = windows.size();
		if(iSize >1)
		{
			for (String handle : windows) 
			{
				GlobalVars.wdriver.switchTo().window(handle);
				if(GlobalVars.wdriver.getTitle().equalsIgnoreCase(sWindowName))
				{
					return true;
				}
			}
			GlobalVars.wdriver.switchTo().window(sFocusedWindow);
			Messages.errorMsg = sWindowName+" not found";
			return false;
		}
		Messages.errorMsg = "only one window is available";
		return false;
	}

	public static boolean switchToFrameByFrameElement(By objLocator)
	{
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{	
			GlobalVars.wdriver.switchTo().frame(GlobalVars.wdriver.findElement(objLocator));
			return true;
		}
		return false;
	}

	public static boolean switchToFrameByName(String sName)
	{
		By objLocator = By.name(sName);
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{	
			try
			{
				GlobalVars.wdriver.switchTo().frame(sName);
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
	
	public static boolean switchToFrameById(String sId)
	{
		By objLocator = By.id(sId);
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{	
			try
			{
				GlobalVars.wdriver.switchTo().frame(sId);
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
