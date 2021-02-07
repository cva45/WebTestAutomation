package utillib;

import java.io.File;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import applib.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportLib {
	public static Logger LOGGER = Logger.getLogger(CoreLib.class.getName());
	public static ExtentReports extent;
	public static ExtentTest Report = null;
	public static ExtentTest ChildReport = null;
	public static ExtentTest ReportTemp = null;
	public static ExtentTest SheetReport = null;

	/*******************************************************************************************************************
	 *
	 * @Date Created : 17/12/2014
	 * @author : SAribina
	 * @param :
	 * @Purpose : Creating the Logger Object.
	 * @Date Updated :
	 * @Updated by :
	 ******************************************************************************************************************/
	public static String createLogger() {
		String logFilePathTxt = null;
		String logFilePathHtml = null;
		try {
			// Set Results Stored Location
			GlobalVars.ResultsDir = GlobalVars.Currentdir + File.separator + "Results";
			CoreLib.deleteFolder(GlobalVars.ResultsDir);
			CoreLib.createDir(GlobalVars.ResultsDir);
			logFilePathTxt = GlobalVars.ResultsDir + File.separator + "Log_Results_Report" + ".txt";
			logFilePathHtml = GlobalVars.ResultsDir + File.separator + "Log_Results_Report" + ".html";

			FileAppender appender = new FileAppender();
			appender.setName("MyFileAppender");
			appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m %n"));

			appender.setFile(logFilePathTxt);
			appender.setAppend(true);
			appender.setThreshold(Level.INFO);
			appender.activateOptions();
			LOGGER.addAppender(appender);

			extent = new ExtentReports(logFilePathHtml, true);
			extent.config().documentTitle("WebView Report").reportName("Results").reportHeadline("Sample Reports...")
					.insertCustomStyles(".test { border:1px solid #200; }").insertCustomStyles(".test{font-size:15px;}");
			
			Report = extent.startTest("testName");
			// extent = new ExtentReports(logFilePath1, true,
			// DisplayOrder.NEWEST_FIRST, NetworkMode.OFFLINE);

		} catch (Exception e) {
			reportMsgInfo("Error Occured while creating the Logger Object..::" + e.getMessage(), CoreLib.getMethodName());
		}
		return logFilePathTxt;
	}


	/*******************************************************************************************************************
	 * @Date Created :
	 * @author :
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void reportMsgPass(String Message) {
		LOGGER.info(Message);
		Report.log(LogStatus.PASS, Message);
	}

	// TO be used in future to print skip test on report
	/*******************************************************************************************************************
	 * @Date Created :
	 * @author :
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void reportMsgSkip(String Message, String ScriptName) {
		LOGGER.info(Message + GlobalVars.SPACE + ScriptName);
		Report.log(LogStatus.SKIP, Message + GlobalVars.SPACE + ScriptName);
	}

	/*******************************************************************************************************************
	 * @Date Created :
	 * @author :
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void reportMsgFail(String Message, String ScriptName) {
		LOGGER.error(Message + GlobalVars.SPACE + ScriptName);
		Report.log(LogStatus.FAIL, Message + GlobalVars.SPACE + ScriptName);

	}

	/*******************************************************************************************************************
	 * @Date Created :
	 * @author :
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void reportMsgFail(String Message) {
		LOGGER.error(Message);
		Report.log(LogStatus.FAIL, Message);

	}

	/*******************************************************************************************************************
	 * @Date Created :
	 * @author :
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void reportMsgError(String Message, String ScriptName) {
		LOGGER.error(Message + GlobalVars.SPACE + ScriptName);
		Report.log(LogStatus.ERROR, Message + GlobalVars.SPACE + ScriptName);

	}

	/*******************************************************************************************************************
	 * @Date Created :
	 * @author :
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void reportMsgError(String Message) {
		LOGGER.error(Message);
		Report.log(LogStatus.ERROR, Message);
	}

	/*******************************************************************************************************************
	 * @Date Created :
	 * @author :
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void reportMsgInfo(String Message, String ScriptName) {
		// System.out.println(Message+" : "+ScriptName );
		LOGGER.info(Message + " : " + ScriptName);
		Report.log(LogStatus.INFO, Message + GlobalVars.SPACE + ScriptName);
	}

	/*******************************************************************************************************************
	 * @Date Created :
	 * @author :
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void reportMsgInfo(String Message) {
		LOGGER.info(Message);
		Report.log(LogStatus.INFO, Message);
	}

	/*******************************************************************************************************************
	 * @Date Created :
	 * @author :
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void reportScreencast(String Message, String ScreenshotPath) {
		Report.log(LogStatus.INFO, Message + " ScreenShot Name " + ScreenshotPath + Report.addScreenCapture(ScreenshotPath));
	}


}
