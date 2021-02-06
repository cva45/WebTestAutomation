package weblib;


import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

public class Alerts {

	private static boolean bStatus;
	private static Logger logger=Logger.getLogger(Alerts.class.getName());
	
	
	public static void test567()
	{
		logger.info("Alerts");
	}

	public static String getAlertMessage(WebDriver wDriver)
	{
		String alertMsg=null;
		bStatus = Wait.waitForAlert(wDriver,5);
		if(bStatus)
		{
			alertMsg = wDriver.switchTo().alert().getText();
			
			
			if(alertMsg != null)
			{
				logger.info("The text '"+alertMsg+"' from the alert has been retrieved successfully");
				return alertMsg;	
			}
			else
			{
				logger.warning("There is no text present in the alert");
				return alertMsg;
			}
		}
		logger.warning("Cannot retrieve the message because "+Messages.errorMsg);
		return  alertMsg;
	}

	public static boolean acceptAlert(WebDriver wDriver)
	{
		bStatus = Verify.verifyAlertPresent(wDriver);
		if(bStatus)
		{
			wDriver.switchTo().alert().accept();
			return true;
		}
		logger.warning("The alert could not be consumed successfully because "+Messages.errorMsg);
		return false;

	}

	public static boolean closeAlert(WebDriver wDriver)
	{
		bStatus = Verify.verifyAlertPresent(wDriver);
		if(bStatus)
		{
			wDriver.switchTo().alert().dismiss();
			logger.info("The alert has been dismissed successfully");
			return true;
		}
		logger.warning("The alert dismissal has been unsuccessful because "+Messages.errorMsg);
		return false;
	}
	
}

