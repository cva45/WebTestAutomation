package applib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {
	
	public void readExcel() throws IOException{

	    //Create an object of File class to open xlsx file
		
		String fileName = "Test.xlsx";
	    File file =    new File("C:\\Users\\saribina\\Downloads\\Test.xlsx" );
	    FileInputStream inputStream = new FileInputStream(file);

	    Workbook Workbook = null;
	    
	    //Find the file extension by splitting file name in substring  and getting only extension name

	    String fileExtensionName = fileName.substring(fileName.indexOf("."));

	    //Check condition if the file is xlsx file

	    if(fileExtensionName.equals(".xlsx")){

	    //If it is xlsx file then create object of XSSFWorkbook class

	    	Workbook = new XSSFWorkbook(inputStream);

	    }

	    //Check condition if the file is xls file

	    else if(fileExtensionName.equals(".xls")){

	        //If it is xls file then create object of HSSFWorkbook class

	    	Workbook = new HSSFWorkbook(inputStream);

	    }
	    
	    //Read sheet inside the workbook by its name

	    Sheet guru99Sheet = Workbook.getSheet("Sheet1");
	    
	    //Find number of rows in excel file

	    int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();
	    int rowCount1 = guru99Sheet.getPhysicalNumberOfRows();

	    //Create a loop over all the rows of excel file to read it

	    for (int i = 0; i < rowCount+1; i++) {

	        Row row = guru99Sheet.getRow(i);

	        //Create a loop to print cell values in a row

	        for (int j = 0; j < row.getLastCellNum(); j++) {

	            //Print Excel data in console
	        		
	        	try
	        	{
	           // System.out.print(row.getCell(j).getStringCellValue()+"|| ");
	            System.out.print(row.getCell(j)+"|| ");
	        	}
	        	catch(Exception e)
	        	{
	        		System.err.println("Exception+"+e.getMessage());
	        	}
	        }

	        System.out.println();
	        
	    }
	        
	    } 
	    
	    public static void main(String...strings) throws IOException{

	    	//Create an object of ReadGuru99ExcelFile class

	        ExcelRead objExcelFile = new ExcelRead();
	        
	        //Prepare the path of excel file

	        String filePath = System.getProperty("user.dir")+"\\src\\excelExportAndFileIO";
	        
	        //Call read file method of the class to read data

	      //  objExcelFile.readExcel(filePath,"ExportExcel.xlsx","ExcelGuru99Demo");
	        objExcelFile.readExcel();
	        
	    }





}
	
	