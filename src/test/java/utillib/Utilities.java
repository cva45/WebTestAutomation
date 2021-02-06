package utillib;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import jxl.Sheet;
//import jxl.Workbook;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import weblib.Messages;

public class Utilities 

{
	private static Map<String, String> objMap = null;
	

/*	public static Map<String, String> readTestData(String sFilePath,String sSheetName,String sTestCaseName) throws IOException
	{
		String sKey = null;
		String sValue = null;
		try
		{

			objMap = new HashMap<String, String>();	
			Workbook objWorkbook = Workbook.getWorkbook(new File(sFilePath));
			Sheet objSheet = objWorkbook.getSheet(sSheetName);
			int iRowCount = objSheet.getRows();
			int iColCount = objSheet.getColumns();
			for(int iRowCounter = 1;iRowCounter<iRowCount;iRowCounter++)
			{
				String sCurTestCaseName = objSheet.getCell(0,iRowCounter).getContents();
				if ((sCurTestCaseName.equalsIgnoreCase(sTestCaseName)))
				{		
					for(int iColCounter = 1;iColCounter<iColCount;iColCounter++)
					{
						sKey = objSheet.getCell(iColCounter,0).getContents();
						System.out.println(sKey);
						sValue = objSheet.getCell(iColCounter,iRowCounter).getContents();
						System.out.println(sValue);
						sValue = getDate(sValue);
						sValue = getTestDataUniqueValue(sValue);

						if((!sValue.equalsIgnoreCase("Null")) && (sValue.trim().length()!=0))
						{
							objMap.put(sKey,sValue);
						}
					}
					break;
				} 
			}
		}
		catch(BiffException e)
		{
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}
		return objMap;
	}
*/	
	public static Map<String, String> gFunc_ReadTestData(String strFilePath,String strSheetName,String strTestCaseName) throws IOException
	{
		String strKey = "";
		String strValue = "";
		String strCurTestCaseName = "";
		Boolean blnTestCaseNameFound = false;
		Workbook objWorkBook = null;
		Sheet objSheet = null;
		Map<String, String> objMap = null;
		objMap = new HashMap<String, String>();
		
		try
		{	
			
			FileInputStream objFileInputStream = new FileInputStream(new File(strFilePath));
	        if(strFilePath.toLowerCase().endsWith(".xlsx"))
	        {
	        	//***xlsx work book object creation
	        	objWorkBook = new XSSFWorkbook(strFilePath);
	        }
	        else if(strFilePath.toLowerCase().endsWith(".xls"))
	        {
	              //***xls file workbook object creation  
	        	  objWorkBook = new HSSFWorkbook(objFileInputStream);
	        }
	        
	            //***sheet object creation
	        objSheet = objWorkBook.getSheet(strSheetName);
	            
	        //***Getting used range Row count from the current sheet 
			int intRowCount = objSheet.getLastRowNum();
			
			//*** Creating object for first row to get the column names and column count
			Row objFirstRow = objSheet.getRow(0);
				
			//***Get used range column count
			int intColCount = objFirstRow.getLastCellNum();
						
			for(int intRowCounter = 1;intRowCounter<=intRowCount;intRowCounter++)
			{
				//***Row object creations			
				Row objRow = objSheet.getRow(intRowCounter);
	         	 
	        	//***Cell object creation for testcase name
				Cell objCellTestCaseName = objRow.getCell(0);
	        	 
				//***Getting TestCaseName 
				//***readCellValue -- Get the cell object value 
	        	strCurTestCaseName = gFunc_ReadCellValue(objCellTestCaseName);
	        	
	        	//*** Validating whether testcase name is there in Excel file
				if ((strCurTestCaseName.equalsIgnoreCase(strTestCaseName)))
				{	
					blnTestCaseNameFound = true;
					for(int intColCounter = 1;intColCounter<intColCount;intColCounter++)
					{
						//***String sColName = datatable.getColumn(sSheetName, iColCounter);
						Cell objCellColName = objFirstRow.getCell(intColCounter);
						
		        		//*** key as colname for map object
						strKey = gFunc_ReadCellValue(objCellColName);
		        		
		        		//*** Cell value object
		        		Cell objCellColValue = objRow.getCell(intColCounter);

		        		strValue = gFunc_ReadCellValue(objCellColValue);
		        			        		
						//Get date and time 
		        		if(strValue.toUpperCase().startsWith("CDATE_"))
		        		{
		        			strValue = getDate(strValue);
		        		}
		        		
						//Get unique data with timestamp
		        		if(strValue.toUpperCase().startsWith("UNIQUE"))
		        		{
		        			strValue = getTestDataUniqueValue(strValue);
		        		}
						if((!strValue.equalsIgnoreCase("Null")) && (!strValue.equalsIgnoreCase(null)) &&(strValue.trim().length()!=0))
						{
							//Adding to map object
							objMap.put(strKey,strValue);
						}
					}
					break;
				} 
			}
			if(!blnTestCaseNameFound) 
			{
				Messages.errorMsg = "TestCaseName with:" + strTestCaseName + " is not found in file :" +strFilePath + " of sheet:" + strSheetName;
				
			}
		}
		catch(FileNotFoundException e)
		{
			objMap = null;
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}catch (NullPointerException e){
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}catch (ArrayIndexOutOfBoundsException e) {
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}catch (Exception e) {
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}
		objWorkBook = null;
		return objMap;
	}
	

/*	public static Map<String,Map<String,String>> readMultipleTestData(String sFilePath,String sSheetName,String sTestCaseName)throws Exception{


		Map<String,Map<String,String>> objTestData = new HashMap<String, Map<String,String>>();

		String sKey,sValue, sCurTestCaseName,sPreviousTestCaseName = "";
		int iRowNo = 1;
		Workbook objWorkbook = Workbook.getWorkbook(new File(sFilePath));
		Sheet objSheet = objWorkbook.getSheet(sSheetName);
		int iRowCount = objSheet.getRows();
		int iColCount = objSheet.getColumns();
		for(int iRowCounter=1;iRowCounter<iRowCount;iRowCounter++){

			Map<String,String> objRowData = new HashMap<String, String>();
			sCurTestCaseName = objSheet.getCell(0, iRowCounter).getContents();
			if ((sPreviousTestCaseName).length()!=0 && (sCurTestCaseName != sPreviousTestCaseName) && sTestCaseName.trim().length() > 0){
				break;
			}
			if ((sCurTestCaseName.equalsIgnoreCase(sTestCaseName)))
			{
				sPreviousTestCaseName = sCurTestCaseName;	

				for(int iColCounter = 1;iColCounter<iColCount;iColCounter++){
					sKey = ((Sheet) objSheet).getCell(iColCounter,0).getContents();
					sKey = sKey.trim();
					sValue = ((Sheet) objSheet).getCell(iColCounter,iRowCounter).getContents();
					sValue = sValue.trim();
					sValue = getDate(sValue);
					sValue = getTestDataUniqueValue(sValue);
					sValue = getTestDataMsgRef(sValue);
					System.out.println(sValue);
					if((!sValue.equalsIgnoreCase(null)) && (sValue.trim().length()!=0)){
						//System.out.println(sKey+sValue);
						objRowData.put(sKey,sValue);
					}
				}
				objTestData.put("Row" + iRowNo,objRowData);

				objRowData=null;

				iRowNo = iRowNo + 1;
			}  
		}
		return objTestData;
	}
*/
	
	/* ********************************************************************************************************** 	
	Copyright					: Oracle Micros Systems, Inc.	
	Method Name					: gFunc_ReadCellValue	
	Initially Developed By		: Murali Popuri	
	Module						: Data Utility
	Compatible Starting With	: NA
	Creation Date				: 01-09-2015
	*********************************************************************************************************
	Description 				: Cell as a input object and it returns Value   
	List of arguments 			: Input objOldArray -  oldArray  the old array, to be reallocated							
	Return value				: Output - ObjCell value is returned as string 
	Notes 						: 
   **********************************************************************************************************										             
    Revison History				:
   **********************************************************************************************************
    Modified Date               : MM-DD-YYYY                                    
    Modified By                 : <Name>                             
    Changed Description         : <Change comments>
   *********************************************************************************************************/

	public static String gFunc_ReadCellValue(Cell cell) 
	{
		String strResult = "";
		try {
	    switch (cell.getCellType())
	    {
	    case Cell.CELL_TYPE_BLANK:
	    	strResult = "";
	    	break;
	    case Cell.CELL_TYPE_BOOLEAN:
	    	strResult = String.valueOf(cell.getBooleanCellValue());
	    	break;
	    case Cell.CELL_TYPE_ERROR:
	    	strResult = String.valueOf(cell.getErrorCellValue());
	    	break;
	    case Cell.CELL_TYPE_NUMERIC:
	    	strResult = String.valueOf(cell.getNumericCellValue());
	    	if(strResult.endsWith(".0")) 
	    	{
	    		strResult = strResult.replace(".0", "");
	    	}
	    	break;
	    case Cell.CELL_TYPE_STRING:
	    	strResult = cell.getStringCellValue();
	    	break;
	    default:
	    	strResult = "";
	    }
		}catch(Exception e) 
		{
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}
		return strResult;
	}
	
	/* ********************************************************************************************************** 	
	Copyright					: Oracle Micros Systems, Inc.	
	Method Name					: gFunc_ReadMultipleTestData	
	Initially Developed By		: Murali Popuri	
	Module						: Data Utility
	Compatible Starting With	: It supports both xls and xlsx format of Excel spread sheets
	Creation Date				: 01-09-2015
	*********************************************************************************************************
	Description 				: It reads multiple rows of test data with the provided file,sheet name 
								  and testcase name 						  
	List of arguments 			
								: Input strFilePath - absolute path of the file where data to be read
								: Input strSheetName - sheet name
								: Input strTestCaseName - TestCase name
	Return value				: Output - hashmap object
	Notes 						: If user wants decimal data that has .0 in the decimal part to be treated
	 							  as is, then he needs to prefix apostrophe(') before that decimal number 
	 							  while keying in the data in the excel.
	 							  Example:  For 123.0, user should key in '123.0 in excel. 
	 							  Similarly for 1349.0,user should key in '1349.0 
   **********************************************************************************************************										             
    Revison History				:
   **********************************************************************************************************
    Modified Date               : MM-DD-YYYY                                    
    Modified By                 : <Name>                             
    Changed Description         : <Change comments>
   *********************************************************************************************************/
	
	public Map<String,Map<String,String>> gFunc_ReadMultipleTestData(String strFilePath,String strSheetName,String strTestCaseName)throws Exception
	{
		String strKey = "";
		String strValue = "";
		String strCurTestCaseName = "";
		String strPreviousTestCaseName = "";
		int iRowNo = 1;
		Boolean blnTestCaseNameFound = false;
		Workbook objWorkBook = null;
		Sheet objSheet = null;		
		Map<String,Map<String,String>> objTestData = null;
		objTestData = new HashMap<String, Map<String,String>>();
								
		try {
	
			FileInputStream objFileInputStream = new FileInputStream(new File(strFilePath));
	        if(strFilePath.toLowerCase().endsWith(".xlsx"))
	        {
	        	//***xlsx work book object creation
	        	objWorkBook = new XSSFWorkbook(strFilePath);
	        }
	        else if(strFilePath.toLowerCase().endsWith(".xls"))
	        {
	              //***xls file workbook object creation  
	        	  objWorkBook = new HSSFWorkbook(objFileInputStream);
	        }
	            //***sheet object creation
	        objSheet = objWorkBook.getSheet(strSheetName);
	            
	        //***Getting used range Row count from the current sheet 
			int intRowCount = objSheet.getLastRowNum();
			
			//***Creating object for row to get the column names and column count
			Row objFirstRow = objSheet.getRow(0);
				
			//***Get used range column count
			int intColCount = objFirstRow.getLastCellNum();
	
		for(int intRowCounter=1;intRowCounter<=intRowCount;intRowCounter++)
		{

			//***Creation of map object for row
			Map<String,String> objRowData = new HashMap<String, String>();	
			
			//***Row object creations			
			Row objRow = objSheet.getRow(intRowCounter);
         	 
        	//***Cell object creation for testcase name
			Cell objCellTestCaseName = objRow.getCell(1);
        	 
			//***Getting TestCaseName 
			//***readCellValue -- Get the cell object value 
        	strCurTestCaseName = gFunc_ReadCellValue(objCellTestCaseName);
        	
			//*** After reading all the multiple rows of single testcase and it comes from out of the loop instead of repeating for all the rows
        	if ((strPreviousTestCaseName).length()!=0 && (strCurTestCaseName != strPreviousTestCaseName) && strTestCaseName.trim().length() > 0)
        	{
        		blnTestCaseNameFound = true;
        		break;
			}
        	
        	//*** Validating whether testcase name is there in Excel file
			if ((strCurTestCaseName.equalsIgnoreCase(strTestCaseName)))
			{
				strPreviousTestCaseName = strCurTestCaseName;	

				for(int intColCounter = 1;intColCounter<intColCount;intColCounter++)
				{
					//***String sColName = datatable.getColumn(sSheetName, iColCounter);
					Cell objCellColName = objFirstRow.getCell(intColCounter);
					
	        		strKey = gFunc_ReadCellValue(objCellColName);
	        		
	        		 //*** Cell value object
	        		Cell objCellColValue = objRow.getCell(intColCounter);

	        		strValue = gFunc_ReadCellValue(objCellColValue);
	        			        		
	        		//Get date and time 
	        		if(strValue.toUpperCase().startsWith("CDATE_"))
	        		{
	        			strValue = getDate(strValue);
	        		}
	        		
					//Get unique data with timestamp
	        		if(strValue.toUpperCase().startsWith("UNIQUE"))
	        		{
	        			strValue = getTestDataUniqueValue(strValue);
	        		}
	        		
					if((!strValue.equalsIgnoreCase("Null")) && (!strValue.equalsIgnoreCase(null)) && (strValue.trim().length()!=0))
					{
						//***Adding to map object
						objRowData.put(strKey,strValue);
					}
				}
				//**** Adding each individual row with key as:""Row1" and value as MapObject 
				objTestData.put("Row" + iRowNo,objRowData);

				objRowData=null;

				iRowNo++;
			} 
		}
			if(!blnTestCaseNameFound) 
			{
				Messages.errorMsg = "TestCaseName with:" + strTestCaseName + " is not found in file :" +strFilePath + " of sheet:" + strSheetName;
			}
		}catch (FileNotFoundException e) {
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}catch(NullPointerException e) {
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}catch(Exception e) {
			Messages.errorMsg = "Exception occured.." + e.getMessage();
		}
		objWorkBook = null;
		return objTestData;
	}

	
	
	private static String getDate(String sValue) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dt = sValue;

		if (sValue.trim().equalsIgnoreCase("Today"))
		{
			dt = sdf.format(cal.getTime());
		}

		if (sValue.trim().contains("Today_"))
		{
			String [] arrValues = sValue.split("_");
			int iDays = Integer.parseInt(arrValues[1]);
			cal.add(Calendar.DATE, iDays);
			//cal.
			dt = sdf.format(cal.getTime());
		}
		if (sValue.trim().contains("Today#"))
		{
			String [] arrValues = sValue.split("#");
			int iDays = Integer.parseInt(arrValues[1]);
			cal.add(Calendar.DATE, -iDays);
			//cal.
			dt = sdf.format(cal.getTime());
		}
		return dt;
	}

	private static String getTestDataUniqueValue(String sValue){
		String sTemp;
		sTemp = sValue.toUpperCase();
		if (sTemp.contains("UNIQUE")){
			sTemp = getUniqueName(sTemp);
			sTemp  =  sTemp .replace("UNIQUE", "");
			System.out.println(sTemp);
			return sTemp ;			  
		}
		return sValue;
	}
	
	private static String getTestDataMsgRef(String sValue){
		String sTemp;
		sTemp = sValue.toUpperCase();
		if (sTemp.contains("MSGREF")){
			sTemp = getUniqueName(sTemp);
			sTemp = sTemp.substring(0, sTemp.length()-2);
			sTemp  =  sTemp .replace("MSGREF", "");
			System.out.println(sTemp);
			return sTemp ;			  
		}
		return sValue;
	}

	private static String getUniqueName(String sName) {
		Calendar rightNow = Calendar.getInstance();
		if (sName == "") {
			return sName;
		} else {
			String sNewName = sName + rightNow.get(Calendar.YEAR)
					+ rightNow.get(Calendar.MONTH)
					+ rightNow.get(Calendar.DAY_OF_MONTH)
					+ rightNow.get(Calendar.HOUR)
					+ rightNow.get(Calendar.MINUTE)
					+ rightNow.get(Calendar.SECOND);
			return sNewName;
		}
	}


	private static String now() 
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		return sdf.format(cal.getTime());
	}
	
	 public static boolean fileExists(String sFileName){		
			File objFile = new File(sFileName);
			if (objFile.exists()){
				return true;
			}		
			return false;
		}



}

