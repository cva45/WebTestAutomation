package weblib;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import applib.*;

public class Asserts {

	private static boolean bStatus;
	private static Logger logger = Logger.getLogger(Asserts.class.getName());

	public static void assertFail(String locatorKey) {
		String imageName = locatorKey;
		CoreLib.takeScreenShot(GlobalVars.ResultsDir, imageName);
		Assert.fail();
	}
}
