package ExcelFunctions;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	public static Object[][] readExcel(String Sheet) throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\InputData\\EcommerceInputSheet.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheet(Sheet);
		XSSFRow rw = sh.getRow(0);
		int LastRowNum = sh.getLastRowNum();
		int LastCellNum = rw.getLastCellNum();
		Object[][] ob = new Object[LastRowNum][LastCellNum];
		for(int i =0; i<LastRowNum; i++) {
			XSSFRow row = sh.getRow(i+1);
		for(int j=0; j<LastCellNum; j++) {	
			if(row==null) {
				ob[i][j]=""; 
			}
			else {
			XSSFCell cell = row.getCell(j);
			if(cell==null) {
				ob[i][j]="";
			}
			else {
				ob[i][j]=row.getCell(j).getStringCellValue();
			}
			}
		}
		}
		return ob;
		
		
	}
	}

