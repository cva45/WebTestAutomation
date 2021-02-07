package applib;

import java.nio.file.Path;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

public class GlobalVars {
public static WebDriver wdriver = null;
public static String Currentdir = System.getProperty("user.dir");
public static String ResultsDir = System.getProperty("user.dir");
public static String downloadPath = "";
public static Path TakesScreenshotPath = null;
public static String ScreenshotPath = null;
public static String ScreeshotName = null;
public static String SPACE = " ";
public static HashMap<String, String> hMapVariableData = null;
}
