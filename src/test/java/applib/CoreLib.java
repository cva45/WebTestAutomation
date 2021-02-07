package applib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import applib.*;
import utillib.ReportLib;
import weblib.*;

public class CoreLib {
	public static Row row;
	public static HashMap<String, String> Vars = new HashMap<String, String>();

	/*******************************************************************************************************************
	 * @Date Created : 17/12/2014
	 * @author : SAribina
	 * @param dirName
	 *            : Name of the Directory
	 * @throws :
	 * @purpose : This function is used creating the directory(folder)
	 * ****************************************************************************************************************/
	public static void createDir(String dirName) {
		File f = new File(dirName);
		try {
			if (!f.exists()) {
				f.mkdir();

				// ReportLib.reportMsgInfo("Directory Created :: " + dirName);
				System.out.println("Directory Created :: " + dirName);
			}
		} catch (Throwable e) {
			// ReportLib.reportMsgError("Unable to create directory  '" +
			// dirName + "'", CoreLib.getMethodName());
			System.out.println("Unable to create directory  '" + dirName + "'");
		}
	}

	/*******************************************************************************************************************
	 * @Date Created : 17/12/2014
	 * @author : SAribina
	 * @param FolderName
	 *            : Name of the Folder
	 * @param ElementName
	 *            : Name of the locator or screen
	 * @throws : Exception error
	 * @purpose : This is function is used to take the screenshot (ie
	 *          FolderName_Element_DDMMYY_HHMMSS)
	 * ****************************************************************************************************************/
	public static String takeScreenShot(String FolderName, String Element) {
		String TakesScreenshot = FolderName;
		GlobalVars.TakesScreenshotPath = Paths.get("Results").relativize(Paths.get("RL_WebView-Framework"));
		TakesScreenshot = (GlobalVars.TakesScreenshotPath).toString();
		GlobalVars.ScreeshotName = Element + ".png";
		GlobalVars.ScreenshotPath = TakesScreenshot + File.separator + "Results" + File.separator + GlobalVars.ScreeshotName;
		try {
			File f = ((TakesScreenshot) GlobalVars.wdriver).getScreenshotAs(OutputType.FILE);
			File f1 = new File(GlobalVars.ScreenshotPath);
			FileUtils.copyFile(f, f1);
			// ReportMsgInfo("Screenshot taken and its location is :" + GlobalVars.ScreenshotPath, CoreLib.getMethodName());
		} catch (IOException ioe) {
			ReportLib.reportMsgInfo("Exception in ' " + CoreLib.getMethodName() + " '. Exception is : " + ioe.getMessage(),
					CoreLib.getMethodName());
			ReportLib.reportScreencast("Failed at : " + CoreLib.getMethodName() + "\n" + " Screenshot is : ",
					GlobalVars.ScreeshotName);
		}
		return GlobalVars.ScreenshotPath;
	}

	/*******************************************************************************************************************
	 * @Date Created : 02/02/2015
	 * @author : Aravind
	 * @param locator
	 *            : Name of the locator
	 * @throws :
	 * @purpose : This function is used print error message, take screen shot
	 *          and fails the application
	 * ****************************************************************************************************************/
	/*
	 * public static void exitTest(String locator) {
	 * CoreLib.fTakeScreenShot("TestFailure", locator); ExcelLib.CellRef = new
	 * CellReference(ExcelLib.RowNo, ExcelLib.ColNo).formatAsString();
	 * Assert.fail("Failed at locator: " + locator + " and at Cell :" +
	 * ExcelLib.CellRef + " in sheet: " + ExcelLib.ExcelSheet + " in workbook :"
	 * + ExcelLib.ExcelWorkbook + ". Exiting Test...");
	 * 
	 * }
	 */

	/*******************************************************************************************************************
	 * @Date Created :
	 * @author : SAribina
	 * @param FolderName
	 *            : Name of the Folder
	 * @param ElementName
	 *            : Name of the locator or screen
	 * @throws IOException
	 * @throws : Exception error
	 * @purpose :
	 * ****************************************************************************************************************/
	public static void moveFiles(String SourcePath, String DestPath, String FileExtn) {
		String files;
		File file = new File(SourcePath);
		File[] listOfFiles = file.listFiles();
		try {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					files = listOfFiles[i].getName();
					if (files.contains(FileExtn) && (!files.contains("_TP"))) {
						listOfFiles[i].renameTo(new File(DestPath + listOfFiles[i].getName()));
						boolean issuccess = new File(listOfFiles[i].toString()).delete();
					}
				}
			}
		} catch (Exception e) {
			ReportLib.reportMsgInfo("Exception in fMoveFiles :" + e.getMessage(), CoreLib.getMethodName());
		}
	}

	/*******************************************************************************************************************
	 * @Date Created :
	 * @author : Aravind
	 * @param : Source Path , Destination Path
	 * @param :
	 * @throws :
	 * @throws :
	 * @purpose : Copy file from source location to destination
	 * ****************************************************************************************************************/
	public static void copyFile(String SourcePath, String DestPath) {
		try {
			FileUtils.copyFile(new File(SourcePath), new File(DestPath));
		} catch (Exception e) {
			ReportLib.reportMsgInfo("Exception in fCopyFile :" + e.getMessage(), CoreLib.getMethodName());
		}
	}

	/*******************************************************************************************************************

	 * ****************************************************************************************************************/
	public static String getMethodName() {
		String methodName = null;
		try {
			return Thread.currentThread().getStackTrace()[2].getMethodName();
		} catch (Exception ioe) {
			ReportLib.reportMsgInfo("Exception at getMethodName():: " + ioe.getMessage());
			Asserts.assertFail(CoreLib.getMethodName());

		}
		return methodName;
	}

	/*******************************************************************************************************************

	 * ****************************************************************************************************************/
	public static int compareTwoLists(List<String> actualList, List<String> expectedList) throws Exception {
		if (actualList.size() == expectedList.size()) {
			for (int counter = 0; counter < expectedList.size(); counter++) {
				if (!actualList.get(counter).equalsIgnoreCase(expectedList.get(counter)))
					return -1;
			}
		} else
			return -1;
		return 0;
	}

	/*******************************************************************************************************************

	 * ****************************************************************************************************************/
	public static void deleteFolder(String DestPath) {
		try {
			File f = new File(DestPath);
			if (f.exists()) {
				FileUtils.forceDelete(new File(DestPath));
				// ReportLib.reportMsgInfo("Folder ( " + DestPath +
				// " ) has been successfully deleted.");
				// System.out.println("Folder ( " + DestPath +
				// " ) has been successfully deleted.");
			}
		} catch (Exception e) {
			// ReportLib.reportMsgInfo("Exception in " + getMethodName() + " :"
			// + e.getMessage(), CoreLib.getMethodName());
			System.out.println("Exception in " + getMethodName() + " :" + e.getMessage());
			Asserts.assertFail(CoreLib.getMethodName());
		}
	}

	/*******************************************************************************************************************

	 * ****************************************************************************************************************/
	public static double getFolderSizeInMB(String DestPath) {
		long size = 0;
		double sizeInMB = 0;
		try {
			size = FileUtils.sizeOfDirectory(new File(DestPath)); 
			// System.out.println("Folder Size in bytes: " + size);
			ReportLib.reportMsgInfo("Folder Size in bytes: " + size);

			double sizeInKB = (size); // convert long to double

			sizeInKB = size / 1024; // convert bytes to KB
			sizeInMB = sizeInKB / 1024; // convert KB to MB
			sizeInMB = Math.round(sizeInMB * 100.00) / 100.00; // round off to
																// two decimals.

			// System.out.println("Folder Size in MB: " + sizeInMB);
			ReportLib.reportMsgInfo("Folder Size in MB: " + sizeInMB);

		} catch (Exception e) {
			// System.out.println("\nException in " + getMethodName() + " : " +
			// e.getMessage());
			ReportLib.reportMsgInfo("\nException in " + getMethodName() + " : " + e.getMessage());
			sizeInMB = 0;
		}
		return sizeInMB;
	}

	/*******************************************************************************************************************

	 * ****************************************************************************************************************/
	public static void deleteFolderOfSize(String DestPath, long SizeInMB) {
		try {
			double size = getFolderSizeInMB(DestPath); // get folder size in MB
			double ifSize = SizeInMB; // convert user specified folder
										// size from long to double

			if (size >= ifSize) // if folder size is more than or equal to user
								// specified size then, delete the folder.
			{
				// System.out.println("Folder (" + DestPath +
				// ") is being deleted because its size (" + size +
				// " MB) is more than or equal to " + SizeInMB + " MB.");
				ReportLib.reportMsgInfo("Folder (" + DestPath + ") is being deleted because its size (" + size
						+ " MB) is more than or equal to " + SizeInMB + " MB.");
				deleteFolder(DestPath);
			} else
				// System.out.println("Folder (" + DestPath +
				// ") has not been deleted because its size (" + size +
				// " MB) is less than " + SizeInMB + " MB.");
				ReportLib.reportMsgInfo("Folder (" + DestPath + ") has not been deleted because its size (" + size
						+ " MB) is less than " + SizeInMB + " MB.");

		} catch (Exception e) {
			// System.out.println("Exception in " + getMethodName() + " : " +
			// e.getMessage());
			ReportLib.reportMsgInfo("Exception in " + getMethodName() + " : " + e.getMessage());
		}
	}

	/*******************************************************************************************************************

	 * ****************************************************************************************************************/
	public static void killBrowserProcess(String BrowserType) {

		String processName = null;

		try {

			switch (BrowserType.trim().toUpperCase()) {
			case "FF":
				processName = "firefox.exe";
				break;
			case "GC":
				processName = "chrome.exe";
				break;
			case "IE":
				processName = "iexplore.exe";
				break;
			case "SF":
				processName = "safari.exe";
				break;
			default:
				processName = "Invalid Browser type.";
				break;
			}
			Runtime.getRuntime().exec("taskkill /F /IM " + processName);
			// System.out.println("Successfully killed " + processName +
			// " process.");
			ReportLib.reportMsgInfo("Successfully killed " + processName + " process.");

		} catch (Exception e) {
			// System.out.println("Exception in " + getMethodName() + " : " +
			// e.getMessage());
			ReportLib.reportMsgInfo("Exception in " + getMethodName() + " : " + e.getMessage());
		}
	}

	/*******************************************************************************************************************

	 ******************************************************************************************************************/
	public static String createRandomNumber(long len) {
		if (len > 18)
			throw new IllegalStateException("To many digits");
		long fixedLen = (long) Math.pow(10, len - 1) * 9;

		long number = (long) (Math.random() * fixedLen) + (long) Math.pow(10, len - 1) * 1;

		String randomNum = number + "";
		if (randomNum.length() != len) {
			throw new IllegalStateException("The random number '" + randomNum + "' is not '" + len + "' digits");
		}
		return randomNum;
	}
}
