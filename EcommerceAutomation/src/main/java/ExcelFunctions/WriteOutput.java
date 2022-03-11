package ExcelFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteOutput {
	
	public static void writeExcel(String path,String SheetName, String TestCaseID,String TestCaseName, String Status,String ExpectedResult, String ActualResult) throws IOException {
		
		File file=new File(path);
		XSSFWorkbook wb ;
		XSSFSheet sh = null;
		if(!file.exists()) {
			wb = new XSSFWorkbook();
			sh = wb.createSheet(SheetName);
			XSSFRow rowHeader = sh.createRow(0);
			rowHeader.createCell(0).setCellValue("TestCaseID");
			rowHeader.createCell(1).setCellValue("TestCaseName");
			rowHeader.createCell(2).setCellValue("Status");
			rowHeader.createCell(3).setCellValue("Expected Result");
			rowHeader.createCell(4).setCellValue("Actual Result");
		}
		else {
			FileInputStream fin=new FileInputStream(file);
			wb = new XSSFWorkbook(fin);
			sh=wb.getSheet(SheetName);
		}
		XSSFRow row=sh.createRow(sh.getLastRowNum()+1);
			
		row.createCell(0).setCellValue(TestCaseID);
		row.createCell(1).setCellValue(TestCaseName);
		row.createCell(2).setCellValue(Status);
		row.createCell(3).setCellValue(ExpectedResult);
		row.createCell(4).setCellValue(ActualResult);
		
		FileOutputStream fio = new FileOutputStream(file);
		wb.write(fio);
		wb.close();
		
	}

}
