package weblib;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class Verify {

	private static boolean bStatus;
	private static Logger logger=Logger.getLogger(Verify.class.getName());


	public static boolean verifyElementVisible(WebDriver wDriver,By objLocator)
	{
		try
		{
			bStatus = wDriver.findElement(objLocator).isDisplayed();
			logger.info("Element "+objLocator+" is visible");
			return bStatus;
		}
		catch(Exception e)
		{
			Messages.errorMsg =e.getMessage();
			logger.warning("Element "+objLocator+" is not visible.");
			return false;
		}
	}

	public static boolean verifyElementPresent(WebDriver wDriver,By objLocator)
	{
		try
		{
			wDriver.findElement(objLocator);
			logger.info("Element "+objLocator+" is present in DOM");
			return true;
		}
		catch(Exception e)
		{
			Messages.errorMsg =e.getMessage() ;
			logger.warning("Element "+objLocator+" is not present in DOM because "+Messages.errorMsg);
			return false;
		}
	}

	public static boolean verifyTextVisible(WebDriver wDriver,String sText)
	{

		bStatus = wDriver.getPageSource().contains(sText);
		if(bStatus)
		{
			logger.info("The Text "+sText+" is present in the current page ");
			return true;
		}
		else
		{
			Messages.errorMsg = sText+" not found in the current page";
			logger.warning(Messages.errorMsg);
			return false;
		}
	}

	public static boolean verifyTextVisible(WebDriver wDriver, By objLocator, String sText)
	{
		bStatus = verifyElementVisible(wDriver, objLocator);
		if(!bStatus)
		{
			logger.warning("The Text "+sText+" is not present in the element "+objLocator+" because "+Messages.errorMsg);
			return false;
		}
		bStatus = wDriver.findElement(objLocator).getText().contains(sText);
		if(bStatus)
		{
			logger.info("The Text "+sText+" is present in the element "+objLocator+" because "+Messages.errorMsg);
			return true;
		}
		Messages.errorMsg = sText+" not found in the locator "+objLocator;
		logger.warning(Messages.errorMsg);
		return false;
	}

	public static boolean verifyChecked(WebDriver wDriver,By objLocator)
	{
		bStatus = verifyElementVisible(wDriver, objLocator);
		if(!bStatus)
		{
			logger.warning("The check box has cannot be checked because "+Messages.errorMsg);
			return false;
		}
		bStatus = wDriver.findElement(objLocator).isSelected();
		if(bStatus)
		{
			logger.info("The check box has already been selected");
			return true;
		}
		Messages.errorMsg = objLocator+" is not selected";
		logger.warning(Messages.errorMsg);		
		return false;

	}

	public static boolean verifyFileExists(String sFileName)
	{		
		File objFile = new File(sFileName);
		if (objFile.exists())
		{
			logger.info(sFileName+ "exist in directory");
			return true;
		}
		Messages.errorMsg = sFileName+ " doesn't exist in directory";
		logger.warning(Messages.errorMsg);
		return false;
	}

	public static boolean verifyItemPresent(WebDriver wDriver,By objLocator,String sItem)
	{
		bStatus = verifyElementVisible(wDriver, objLocator);
		if(!bStatus)
		{
			logger.warning(sItem+" cannot be verified for the locator "+objLocator+" because "+Messages.errorMsg);
			return false;
		}

		try
		{
			Select select = new Select(Elements.getWebElement(wDriver,objLocator));
			List<WebElement> element = select.getOptions();
			for (int iCount = 0; iCount < element.size(); iCount++) 
			{
				if(element.get(iCount).getText().equalsIgnoreCase(sItem))
				{
					logger.info(sItem+" option is present in the element "+objLocator);
					return true;
				}
			}
			Messages.errorMsg = sItem+" option not found in the element "+objLocator;
			logger.warning(Messages.errorMsg);
			return false;
		}
		catch(Exception e)
		{
			Messages.errorMsg = e.getMessage();
			logger.warning(sItem+" item cannot be found because "+Messages.errorMsg);
			return false;
		}
	}

	public static boolean verifyEnable(WebDriver wDriver,By objLocator)
	{
		bStatus = verifyElementPresent(wDriver, objLocator);
		if(!bStatus)
		{
			logger.warning("Element "+objLocator+" is not visible "+Messages.errorMsg);
			return false;
		}
		bStatus = wDriver.findElement(objLocator).isEnabled();
		if(bStatus)
		{
			logger.info("The element is enabled");
			return true;
		}
		Messages.errorMsg = objLocator+" is not enabled";
		logger.warning(Messages.errorMsg);
		return false;
	}

	public static boolean verifyAlertPresent(WebDriver wDriver)
	{
		String alertMsg = Alerts.getAlertMessage(wDriver);
		if(alertMsg == null)
		{
			logger.warning("No alert found");
			return false;
		}
		logger.info("Alert present");
		return true;
	}

	public static Boolean verifyElementsPresent(WebDriver wDriver, By objLocator) 
	{
		try 
		{
			wDriver.findElements(objLocator);
			logger.info("The elements are prsent in the DOM");
			return true;
		} catch (NoSuchElementException e) 
		{
			Messages.errorMsg = e.getMessage();
			logger.warning("The elements are prsent in the DOM because "+Messages.errorMsg);
			return false;
		}
	}
}



