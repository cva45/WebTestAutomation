package weblib;


import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import applib.GlobalVars;




public class Elements {

	private static boolean bStatus;
	private static Logger logger=Logger.getLogger(Elements.class.getName());

	public static WebElement getWebElement(By objLocator)
	{
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			logger.info("The Element "+objLocator+" is visible and can be used");
			return GlobalVars.wdriver.findElement(objLocator);
		}
		logger.warning("The Element "+objLocator+" is not visible and cannot be used");
		return null;
	}

	public static int getXpathCount(By objLocator)
	{
		int iSize = 0;
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			iSize = GlobalVars.wdriver.findElements(objLocator).size();
			logger.info("The xpath count of the element "+objLocator+" is "+iSize);
			return iSize;
		}
		logger.warning("The xpath count of the element "+objLocator+" is "+iSize+" because "+Messages.errorMsg);
		return iSize;	
	}

	public static boolean enterText(By objLocator,String sValue)
	{
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			GlobalVars.wdriver.findElement(objLocator).clear();
			GlobalVars.wdriver.findElement(objLocator).sendKeys(sValue);
			logger.info("The text"+sValue+"has been inputted successfully.");
			return true;
		}
		logger.warning("The text "+sValue+"could not be entered successfully");
		return false;	
	}

	public static boolean selectCheckbox(By objLocator)
	{
			bStatus = Verify.verifyChecked(objLocator);
			if(!bStatus)
			{
				GlobalVars.wdriver.findElement(objLocator).click();
				
				logger.info("The check box has been selected");
				return true;
			}	
			logger.warning("Cannot check the CheckBox");
			return false;
	}

	public static boolean unSelectCheckbox(By objLocator)
	{

			bStatus = Verify.verifyChecked(objLocator);
			if(bStatus)
			{
				GlobalVars.wdriver.findElement(objLocator).click();
				logger.info("The check box has been deselected successfully");
				return true;
			}
			logger.warning("Cannot uncheck the CheckBox because "+Messages.errorMsg);
			return false;
	}

	public static boolean clickButton(By objLocator)
	{
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			GlobalVars.wdriver.findElement(objLocator).click();
			logger.info("The button "+objLocator+" has been clicked successfully");
			return true;
		}
		logger.warning("The button "+objLocator+" cannot be clicked because "+Messages.errorMsg);
		return false;
	}

	public static boolean clearText(By objLocator){
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			GlobalVars.wdriver.findElement(objLocator).clear();
			logger.info("The text has been cleared from the input box "+objLocator+" successfully");
			return true;
		}
		logger.warning("The text could not be cleared from the input box "+objLocator);
		return false;
	}


	public static boolean selectOptionByIndex(By objLocator, int iIndexVal)
	{
		WebElement wbElement = getWebElement(objLocator);
		if(wbElement == null)
		{
			try{
				Select sel = new Select(wbElement);
				sel.selectByIndex(iIndexVal);
				logger.info("The option from the dropdown has been selected by index "+iIndexVal+" successfully");
				return true;
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
				logger.warning("The option from the dropdown could not be selected by index "+iIndexVal+ " due to "+Messages.errorMsg);
				return false;
			}
		}
		return false;
	}

	public static boolean selectOptionByValue(By objLocator,String sValue)
	{
		WebElement wbElement = getWebElement(objLocator);
		if(wbElement == null)
		{
			try{
				Select sel = new Select(wbElement);
				sel.selectByValue(sValue);
				logger.info("The option from the dropdown has been selected by value "+sValue+" successfully");
				return true;
			}
			catch(Exception e)
			{
				Messages.errorMsg = e.getMessage();
				logger.warning("The option from the dropdown could not be selected by value "+sValue+ " due to "+Messages.errorMsg);
				return false;
			}
		}
		logger.warning("The option from the dropdown could not be selected by value "+sValue+ " due to "+Messages.errorMsg);
		return false;
	}

	public static String getElementAttribute(By objLocator, String sAttrVal)
	{
		String sValue = null;
		bStatus = Verify.verifyElementPresent(objLocator);
		if(bStatus)
		{
			sValue = GlobalVars.wdriver.findElement(objLocator).getAttribute(sAttrVal);
			if(sValue == null)
			{
				Messages.errorMsg = "The element "+objLocator+" has no attribute "+sAttrVal;
				logger.warning(Messages.errorMsg );
				return sValue;
			}
			logger.warning( "The element "+objLocator+" has value '"+sValue +"' for attribute "+sAttrVal);
			return sValue;
		}
		logger.warning( "The attribute "+sAttrVal+" of element "+objLocator+" value cannot be retrieved because "+Messages.errorMsg);
		return sValue ;
	}
	
	public static String getText(By objLocator)
	{
		String sValue = null;
		bStatus = Verify.verifyElementVisible(objLocator);
		if(bStatus)
		{
			sValue = GlobalVars.wdriver.findElement(objLocator).getText();
			if(sValue == null)
			{
				logger.info("The element "+objLocator+" has no text ");
				return sValue;
			}
			logger.warning("The text "+sValue+" from the element "+objLocator+" is retrieved");
			return sValue;	
		}
		logger.warning("The text from the element "+objLocator+" cannot be retrieved because "+Messages.errorMsg);
		return sValue;
	}

}

